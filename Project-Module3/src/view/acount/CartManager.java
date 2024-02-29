package view.acount;

import ra.config.Config;
import ra.config.Validate;
import ra.model.*;
import sevice.cart.CartServiceIMPL;
import sevice.cart.ICartService;
import sevice.order.IOderService;
import sevice.order.OderIMPL;
import sevice.product.IProductService;
import sevice.product.ProductServiceIMPL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ra.config.Color.*;


public class CartManager {
    static Config<Products> config = new Config<>();
    static Config<Users> usersConfig = new Config<>();
    IProductService productService = new ProductServiceIMPL();
    ICartService cartService = new CartServiceIMPL();
    static OderIMPL oderIMPL = new OderIMPL();

    int totalCart = 0;

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VND");
        return formatter.format(amount);
    }
    public void menuCart() {

        int menuWidth = 30;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getName());
        if (cartService != null && productService != null) {
            System.out.println(LIGHT_CYAN+ "╔══════════════════════════════"+WHITE_BOLD_BRIGHT+"GIỎ HÀNG"+LIGHT_CYAN+"════════════════════════════════╗");
            System.out.println("║    "+WHITE_BOLD_BRIGHT+"0. QUAY LẠI"+LIGHT_CYAN+"                             " + greeting + "║");
            System.out.println("║══════════════════════════════════════════════════════════════════════║");

            List<Cart> cartList = cartService.findAll();
            if (cartList.isEmpty()) {
                System.out.println("║                          "+WHITE_BOLD_BRIGHT+"Giỏ hàng trống!"+LIGHT_CYAN+"                             ║");
            } else {
                System.out.println("║  *  ║       "+WHITE_BOLD_BRIGHT+"TÊN SẢN PHẨM"+LIGHT_CYAN+"         ║   "+WHITE_BOLD_BRIGHT+"SỐ LƯỢNG"+LIGHT_CYAN+"   ║        "+WHITE_BOLD_BRIGHT+"GIÁ"+LIGHT_CYAN+"         ║");
                System.out.println("║══════════════════════════════════════════════════════════════════════║");



                for (Cart cart : cartList) {
                    Map<Integer, Integer> products = cart.getProductCart();
                    for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                        int productId = entry.getKey();
                        int quantity = entry.getValue();

                        Products product = productService.findById(productId);
                        if (product != null) {
                            double totalPrice = product.getUnitPrice() * quantity;
                            totalCart+=totalPrice;
                            String formattedPrice = formatCurrency(totalPrice);
                            System.out.printf("║%4d ║   %-24s ║%8d      ║%18s  ║\n", product.getProductId(), product.getProductName(), quantity, formattedPrice);
                        }
                    }
                }
            }
            String formattedPriceCart = formatCurrency(totalCart);
            int menuTotal = 31;
            String totalAll = String.format(WHITE_BOLD_BRIGHT+"TỔNG GIÁ TRỊ ĐƠN HÀNG: %s"+LIGHT_CYAN, formattedPriceCart);
            System.out.println("║══════════════════════════════════════════════════════════════════════║ ");
            System.out.println("║                              "+WHITE_BOLD_BRIGHT+" "+totalAll+" "+LIGHT_CYAN+"  ║");
            System.out.println("║══════════════════════════════════════════════════════════════════════║");
            System.out.println("║     "+WHITE_BOLD_BRIGHT+"1. Xóa sản phẩm"+LIGHT_CYAN+"                ║    "+WHITE_BOLD_BRIGHT+"2. Thanh toán"+LIGHT_CYAN+"                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════╝" +LIGHT_CYAN);

            System.out.print(YELLOW+"Vui lòng chọn: "+RESET);
            int choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    deleteProduct();
                    break;
                case 2:
                    checkOut();
                    break;
                case 0:
                    break;
                default:
                    System.err.println(YELLOW+"Lựa chọn không hợp lệ"+RESET);
            }
        }
    }

    private void checkOut() {
        Cart cart = cartService.findCartByUserLogin(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
        if (cart != null){
            // kiểm tra tồn kho
            Map<Integer,Integer> products = cart.getProductCart();
            boolean stock = true;
            for (Map.Entry<Integer,Integer> entry : products.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                Products product = productService.findById(productId);
                if (product != null){
                    if (quantity > product.getStock()){
                        stock = false;
                        System.out.printf("Sản phẩm '%s' không đủ số lượng trong kho.\n", product.getProductName());
                    }
                }
            }

            if (stock){
                // cập nhật số lượng tồn kho
                for (Map.Entry<Integer,Integer> entry : products.entrySet()  ) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue();

                    Products product = productService.findById(productId);
                    if (product != null){
                        int newStock = product.getStock() - quantity;
                        product.setStock(newStock);
                        productService.save(product);
                    }
                }

                List<Orderdetail> orderdetails = new ArrayList<>();
                int orderId = oderIMPL.getNewId();
                for (Map.Entry<Integer,Integer> entry : products.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue();

                    Products product = productService.findById(productId);
                    if (product != null){
                        double unitPrice = product.getUnitPrice();
                        String name = product.getProductName();
                        Orderdetail orderdetail = new Orderdetail(productId,orderId,name,unitPrice,quantity);
                        orderdetails.add(orderdetail);
                    }
                }
                // tạo đối tướngj oder
                int userId = usersConfig.readFile(Config.URL_USER_LOGIN).getId();
                String userName = usersConfig.readFile(Config.URL_USER_LOGIN).getName();
                System.out.println("Nhập số điện thoại");
                String numberphone = Validate.validateString();
                System.out.println("Nhập địa chỉ");
                String address = Validate.validateString();
                double total = totalCart;
                Date orderdate = new Date();
                OrderStatus orderStatus = OrderStatus.WAITING;
                Order newOrder = new Order(orderId,userId,userName,numberphone,address,total,orderStatus,orderdetails,orderdate);
                // Lưu file
                oderIMPL.save(newOrder);

                // Xóa giỏ hàng sau khi thanh toán
                config.writeFile(Config.URL_CART,null);
                System.out.println("Thanh toán thành công. Cảm ơn bạn đã mua hàng!");
            }
        } else {
            System.out.println("Giỏ hàng không tồn tại.");
        }
    }


    private void deleteProduct() {
        System.out.println("Nhập vào mã sản phẩm bạn muốn xóa");
        int deleteId = Validate.validateInt();
        Cart cart = cartService.findCartByUserLogin(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
        if(cart != null){
            cart.removeProduct(deleteId);
            cartService.save(cart);
            System.out.println("Đã xóa sản phẩm trong giỏ hàng");
        }
    }
}




