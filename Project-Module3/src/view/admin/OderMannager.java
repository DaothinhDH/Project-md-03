package view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.*;
import sevice.order.IOderService;
import sevice.order.OderIMPL;
import sevice.product.IProductService;
import sevice.product.ProductServiceIMPL;

import java.util.*;

import static ra.config.Color.*;
import static ra.config.Color.RESET;

public class OderMannager {
    static Config<Users> usersConfig = new Config();
    IOderService oderService = new OderIMPL();

    public void menuOder() {
        int menuWidth = 22;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getName());
        String menu = LIGHT_CYAN +
                "╔══════════════════════" + WHITE_BOLD_BRIGHT + "TRANG ĐƠN HÀNG" + LIGHT_CYAN + "═════════════════════════╗\n" +
                "║    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                          " + greeting + LIGHT_CYAN + "║\n" +
                "║═════════════════════════════════════════════════════════════║\n" +
                "║                  " + WHITE_BOLD_BRIGHT + "1. Hiển thị danh sách đơn hàng" + LIGHT_CYAN + "             ║\n" +
                "║                  " + WHITE_BOLD_BRIGHT + "2. Tìm kiếm đơn hàng" + LIGHT_CYAN + "                       ║\n" +
                "║                  " + WHITE_BOLD_BRIGHT + "3. Sắp xếp theo giời gian đặt hàng" + LIGHT_CYAN + "         ║\n" +
                "╚═════════════════════════════════════════════════════════════╝" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.print("Mời nhập lựa chọn: ");
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    showOrder();
                    break;
                case 2:
                    seachOder();
                    break;
                case 3:
                    sortOderTime();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showOrder() {
        if (oderService != null) {
            System.out.println(LIGHT_CYAN + "╔══════════════════════════════════════════════════" + ORANGE_2 + "DANH SÁCH ĐƠN HÀNG" + LIGHT_CYAN + "═══════════════════════════════════════════════════╗");
            System.out.println("║-" + ORANGE_2 + "MÃ ĐƠN" + LIGHT_CYAN + "║══" + WHITE_BOLD_BRIGHT + "TÊN NGƯỜI ĐẶT" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ GIAO HÀNG" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "GIỜI GIAN ĐẶT HÀNG" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "GIÁ TRỊ ĐƠN" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + LIGHT_CYAN + "══║");
            if (oderService.findAll().isEmpty()) {
                System.out.println("║                                                                                                                                                           ║");
                System.out.println("║                                                                Danh mục đang trống!                                                                       ║" + RESET);
            } else {
                for (Order orders : oderService.findAll()) {
                    orders.display();
                }
            }
        } else {
            System.err.println("Không tìm thấy danh sách đơn hàng!");
        }


        System.out.println(LIGHT_CYAN + "║═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║");
        System.out.println("║         " + WHITE_BOLD_BRIGHT + "1. XEM CHI TIẾT ĐƠN HÀNG" + LIGHT_CYAN + "          ║         " + WHITE_BOLD_BRIGHT + "2. THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG" + LIGHT_CYAN + "        ║        " + WHITE_BOLD_BRIGHT + "0. QUAY LẠI" + LIGHT_CYAN + "       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝'" + RESET);

        System.out.print("Mời nhập lựa chọn: ");
        int choice = Validate.validateInt();
        switch (choice) {
            case 1:
                orderDetailShow();
                break;
            case 2:
                updateOderStatus();
                break;
            case 0:
                break;
            default:
                System.err.println("Lựa chọn không hợp lệ");
        }

    }

    private void updateOderStatus() {
        System.out.print("Nhập ID đơn hàng: ");
        int orderId = Validate.validateInt();
        Order order = oderService.findById(orderId);
        if (order == null) {
            System.out.println("Không tìm thấy đơn hàng với mã đơn " + orderId);
            return;
        }

        if (order.getOrderStatus() != OrderStatus.WAITING) {
            System.out.println("Không thể thay đổi trạng thái cho đơn hàng này.");
            return;
        }

        System.out.println("Chọn trạng thái mới:");
        System.out.println("1. XÁC NHÂN ĐƠN HÀNG");
        System.out.println("2. HỦY ĐƠN HÀNG");
        System.out.print("Nhập số tương ứng với trạng thái mới: ");
        int choice = Validate.validateInt();

        OrderStatus newStatus;
        switch (choice) {
            case 1:
                newStatus = OrderStatus.CONFIRM;
                break;
            case 2:
                newStatus = OrderStatus.CANCEL;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        order.setOrderStatus(newStatus);
        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            List<Orderdetail> orderDetails = order.getOrderDetails();
            for (Orderdetail orderDetail : orderDetails) {
                int productId = orderDetail.getProductId();
                int quantity = orderDetail.getQuantity();

                // Lấy thông tin sản phẩm từ service hoặc repository tương ứng
                IProductService productService = new ProductServiceIMPL();
                Products product = productService.findById(productId);
                if (product != null) {
                    int currentStock = product.getStock();
                    product.setStock(currentStock + quantity);
                    productService.save(product);
                    System.out.println("Đã trả lại " + quantity + " sản phẩm vào kho cho sản phẩm có ID: " + productId);
                } else {
                    System.out.println("Không tìm thấy thông tin sản phẩm có ID: " + productId);
                }
            }
            System.out.println("Đơn hàng đã được hủy và số lượng sản phẩm đã được trả lại!");
        } else {
            oderService.save(order);
            System.out.println("Đã cập nhật trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());

            // Lập lịch chuyển trạng thái sau một khoảng thời gian
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (order.getOrderStatus() == OrderStatus.CONFIRM) {
                        order.setOrderStatus(OrderStatus.DELIVERY);
                        oderService.save(order);
                        System.out.println("Đã chuyển trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());
                        // Lập lịch chuyển trạng thái thành công sau một khoảng thời gian
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (order.getOrderStatus() == OrderStatus.DELIVERY) {
                                    order.setOrderStatus(OrderStatus.SUCCESS);
                                    oderService.save(order);
                                    System.out.println("Đã chuyển trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());
                                }
                            }
                        }, 6000); // Chuyển thành trạng thái SUCCESS sau 6s
                    }
                }
            }, 6000); // Chuyển thành trạng thái DELIVERY sau 6s
        }
    }

    public void seachOder() {
        System.out.print("Nhập tên người đặt hàng: ");
        String customerName = Validate.validateString();
        List<Order> foundOrders = oderService.findByName(customerName);
        if (foundOrders.isEmpty()) {
            System.out.println("Không tìm thấy đơn hàng cho khách hàng: " + customerName);
        } else {
            System.out.println(LIGHT_CYAN + "╔═══════════════════════════════════" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + LIGHT_CYAN + "═════════════════════════╗");
            for (Order order : foundOrders) {
                order.display();
                orderDetailProduct(order);
            }
        }
    }

    public void orderDetailProduct(Order order) {

        System.out.println(LIGHT_CYAN + "╔══════════════════════" + WHITE_BOLD_BRIGHT + "CHI TIẾT SẢN PHẨM" + LIGHT_CYAN + "═══════════════════╗");
        System.out.println("║══════════" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + LIGHT_CYAN + "══════════|-" + WHITE_BOLD_BRIGHT +      "SỐ LƯỢNG" + LIGHT_CYAN + "-║═══" + WHITE_BOLD_BRIGHT +     "GIÁ TIỀN" + LIGHT_CYAN + "═══║");
        order.getOrderDetails().forEach(Orderdetail::display);
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
//        System.out.println(".---------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + LIGHT_CYAN + "--------------------------.");
//        System.out.println("|                                                                       |" + RESET);
    }

    public void sortOderTime() {
        List<Order> allOrders = oderService.findAll();
        if (allOrders.isEmpty()) {
            System.out.println("Danh sách đơn hàng rỗng.");
            return;
        }

        Collections.sort(allOrders, Comparator.comparing(Order::getOrderAt).reversed());

        System.out.println(LIGHT_CYAN + "╔══════════════════════" + WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG (SẮP XẾP THEO THỜI GIAN ĐẶT HÀNG)" + LIGHT_CYAN + "═════════════════════════╗");
        System.out.println("|                                                                                                                                                                |");
        System.out.println("|-" + WHITE_BOLD_BRIGHT + "MÃ ĐƠN" + LIGHT_CYAN + "-|--" + WHITE_BOLD_BRIGHT + "TÊN NGƯỜI ĐẶT" + LIGHT_CYAN + "--|--" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + LIGHT_CYAN + "--|--" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ GIAO HÀNG" + LIGHT_CYAN + "--|--" + WHITE_BOLD_BRIGHT + "GIỜI GIAN ĐẶT HÀNG" + LIGHT_CYAN + "--|--" + WHITE_BOLD_BRIGHT + "GIÁ TRỊ ĐƠN" + LIGHT_CYAN + "--|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + LIGHT_CYAN + "--|");

        for (Order order : allOrders) {
            order.display();
        }

        System.out.println("`---------------------------------------------------------------------------------------------------------------'" + RESET);
    }
    private void orderDetailShow() {
        System.out.print("Nhập ID đơn hàng: ");
        int orderIdEdit = Validate.validateInt();
        Order foundOrders = oderService.findById(orderIdEdit);
        if (foundOrders == null) {
            System.out.println("Không tìm thấy đơn hàng có ID: " + orderIdEdit);
        } else {
            System.out.println(LIGHT_CYAN + "╔════════════════════════════════════════════════" + ORANGE_2 + "THÔNG TIN ĐƠN HÀNG" + LIGHT_CYAN + "══════════════════════════════════════════════════╗");
            System.out.println("║-" + ORANGE_2 + "MÃ ĐƠN" + LIGHT_CYAN + "║══" + WHITE_BOLD_BRIGHT + "TÊN NGƯỜI ĐẶT" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ GIAO HÀNG" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "GIỜI GIAN ĐẶT HÀNG" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "GIÁ TRỊ ĐƠN" + LIGHT_CYAN + "══║══" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + LIGHT_CYAN + "══║");
            foundOrders.display();
            orderDetailProduct(foundOrders);

        }
    }

}
