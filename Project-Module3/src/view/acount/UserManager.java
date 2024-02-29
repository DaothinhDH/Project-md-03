package view.acount;

import static ra.config.Color.*;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Products;
import ra.model.Users;
import sevice.cart.CartServiceIMPL;
import sevice.cart.ICartService;
import sevice.catalog.CatalogServiceIMPL;
import sevice.catalog.ICatalogService;
import sevice.product.IProductService;
import sevice.product.ProductServiceIMPL;
import view.admin.MenuInfor;
import view.home.Login;

public class UserManager {
    static Config<Products> config = new Config<>();
    static Config<Users> usersConfig = new Config<>();
    IProductService productService = new ProductServiceIMPL();
    ICatalogService catalogService = new CatalogServiceIMPL();
    ICartService cartService = new CartServiceIMPL();

    public void menuUser() {
        do {
//            System.out.println("\nXin chao: "+ Home.userLogin.getName());
            System.out.println(LIGHT_CYAN + "╔══════════════════════════════════╗");
            System.out.println("║             "+ORANGE_2+"HOME"+LIGHT_CYAN+"                 ║");
            System.out.println("╠════╦═════════════════════════════╣");
            System.out.println("║ 1. ║ "+WHITE_BOLD_BRIGHT+"Hiển thị danh sách sản phẩm"+LIGHT_CYAN+" ║");
            System.out.println("╠════╬═════════════════════════════╣");
            System.out.println("║ 2. ║ "+WHITE_BOLD_BRIGHT+"Tìm kiếm sản phẩm theo tên"+LIGHT_CYAN+"  ║");
            System.out.println("╠════╬═════════════════════════════╣");
            System.out.println("║ 3. ║ "+WHITE_BOLD_BRIGHT+"Thông tin cá nhân"+LIGHT_CYAN+"           ║");
            System.out.println("╠════╬═════════════════════════════╣");
            System.out.println("║ 4. ║ "+WHITE_BOLD_BRIGHT+"Giỏ hàng"+LIGHT_CYAN+"                    ║");
            System.out.println("╠════╬═════════════════════════════╣");
            System.out.println("║ 0. ║ "+WHITE_BOLD_BRIGHT+"Log out"+LIGHT_CYAN+"                     ║");
            System.out.println("╚════╩═════════════════════════════╝");
            System.out.print(YELLOW + "Lựa chọn (0/1/2): " + RESET);


            switch (Validate.validateInt()) {

                case 0:
//                    Home.userLogin = null;
                    new Login().menuHome();
                    break;
                case 1:
                    showProduct();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    new MenuInfor().menuInfor();
                    break;
                case 4:
                    new CartManager().menuCart();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void searchProduct() {
        System.out.println("Nhập từ khóa tìm kiếm");
        String seach = Validate.validateString();
        for (Products products : productService.findAll()) {
            if (products.getProductName().toLowerCase().contains(seach)){
                products.display();
            }
        }
    }

    private void showProduct() {
        System.out.println(LIGHT_CYAN+"╔═════════════════════════════════════"+ORANGE_2+"DANH SÁCH SẢN PHẨM ĐANG BÁN"+LIGHT_CYAN+"════════════════════════════════════════╗");
        System.out.println("║  *  ║      "+ORANGE_2+"TÊN SẢN PHẨM"+LIGHT_CYAN+"        ║     "+ORANGE_2+"GIÁ BÁN"+LIGHT_CYAN+"      ║  "+ORANGE_2+"SỐ LƯỢNG"+LIGHT_CYAN+" ║              "+ORANGE_2+"MÔ TẢ SẢN PHẨM"+LIGHT_CYAN+"             ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        if (productService.findAll().isEmpty()) {
            System.out.println("Danh sách đang rỗng");
        } else {
            boolean continueAdding = true;
            while (continueAdding) {
                for (Products products : productService.findAll()) {
                    products.display();
                }
                System.out.println(LIGHT_CYAN+"╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝"+RESET);
                addCart();
                System.out.print("Bạn có muốn tiếp tục thêm sản phẩm vào giỏ hàng? (Y/N): ");
                String choice = Validate.validateString();
                continueAdding = choice.equalsIgnoreCase("Y");
                if (choice.equalsIgnoreCase("Y")) {
                    System.out.println();
                    System.out.println(LIGHT_CYAN+"╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║  *  ║      "+WHITE_BOLD_BRIGHT+"TÊN SẢN PHẨM"+LIGHT_CYAN+"        ║     "+WHITE_BOLD_BRIGHT+"GIÁ BÁN"+LIGHT_CYAN+"      ║  "+WHITE_BOLD_BRIGHT+"SỐ LƯỢNG"+LIGHT_CYAN+" ║              "+WHITE_BOLD_BRIGHT+"MÔ TẢ SẢN PHẨM"+LIGHT_CYAN+"              ║");
                    System.out.println("║══════════════════════════════════════════════════════════════════════════════════════════════════════════║"+RESET);
                } else System.out.println();
            }
        }
    }

    public void addCart() {
        System.out.println(YELLOW+"Nhập id sản phẩm để thêm vào giỏ hàng"+RESET);
        int productId = Validate.validateInt();
        System.out.println(YELLOW+"Nhập số lượng muốn thêm vào giỏ hàng"+RESET);
        int quantity = Validate.validateInt();
        Products productBuy = productService.findById(productId);
        if (productBuy != null) {
//            int newStock = productBuy.getStock() - 1;
//            productBuy.setStock(newStock);
            Cart cart = cartService.findCartByUserLogin(usersConfig.readFile(Config.URL_USER_LOGIN).getId());

            if (cart == null) {
                cart = new Cart(cartService.getNewId(), usersConfig.readFile(Config.URL_USER_LOGIN).getId());
            }
            cart.addProduct(productId, quantity);
            cartService.save(cart);
            System.out.println(YELLOW+"Thêm vào giỏ hàng thành công"+RESET);
        } else {
            System.out.println(YELLOW+"sản phẩm không có trong cửa hàng"+ RESET);
        }
    }
}