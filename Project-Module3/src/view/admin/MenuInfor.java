package view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Users;
import sevice.user.UserServiceIMPL;
import static ra.config.Color.*;


public class MenuInfor {
    static Config<Users> usersConfig = new Config();
    UserServiceIMPL userServiceIMPL = new UserServiceIMPL();
    Users usersLogin = usersConfig.readFile(Config.URL_USER_LOGIN);

    public void menuInfor() {
        int menuWidth = 30;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersLogin.getName());
        String menu =
                LIGHT_CYAN+"╔══════════════════════"+ORANGE_2+"TRANG THÔNG TIN NGƯỜI DÙNG"+LIGHT_CYAN+"═════════════════════════╗\n" +
                        "║    "+ORANGE_2+"0. Quay lại"+LIGHT_CYAN+"                             " + greeting + "   ║\n" +
                        "║═════════════════════════════════════════════════════════════════════════║\n" +
                        "║                      "+ORANGE_2+"1. Thông tin cá nhân"+LIGHT_CYAN+"                               ║\n" +
                        "║                      "+ORANGE_2+"2. Sửa thông tin cá nhân"+LIGHT_CYAN+"                           ║\n" +
                        "║                     "+ORANGE_2+" 3. Đổi mật khẩu"+LIGHT_CYAN+"                                    ║\n" +
                        "║                     "+ORANGE_2+" 4. Lịch sử mua hàng "+LIGHT_CYAN+"                               ║\n" +
                        "╚═════════════════════════════════════════════════════════════════════════╝"+RESET;
        int choice;
        do {
            System.out.println(menu);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    showUser();
                    break;
                case 2:
                    editUsers();
                    break;
                case 3:
                    editPassword();
                    break;
                case 4:
                    historyBuy();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    private void historyBuy() {
        System.out.println("Lịch sử mua hàng");

    }

    private void editPassword() {
        System.out.println("------ĐỔI MẬT KHẨU------");

        boolean success = false;
        do {

            System.out.println("Mật khẩu cũ: ");
            String oldPassword = Validate.validateString();
            if (!oldPassword.equals(usersLogin.getPassword())) {
                System.err.println("Mật khẩu cũ không chính xác. Vui lòng thử lại.");
                continue;
            }
            System.out.println("Mật khẩu mới: ");
            String newPassword = Validate.validateString();

            if (newPassword.equals(oldPassword)) {
                System.err.println("Mật khẩu mới phải khác mật khẩu cũ. Vui lòng thử lại.");
                continue;
            }

            System.out.println("Xác nhận mật khẩu mới: ");
            String confirmPassword = Validate.validateString();
            if (!newPassword.equals(confirmPassword)) {
                System.err.println("Xác nhận mật khẩu mới không khớp. Vui lòng thử lại.");
                continue;
            }
            usersLogin.setPassword(newPassword);
            Users users = userServiceIMPL.findById(usersLogin.getId());
            userServiceIMPL.save(users);
            usersConfig.writeFile(Config.URL_USER_LOGIN, usersLogin);
            System.out.println("Đổi mật khẩu thành công.");
            success = true;
        } while (!success);
    }

    private void editUsers() {
        System.out.println("------SỬA THÔNG TIN CÁ NHÂN------");
        Users users = userServiceIMPL.findById(usersLogin.getId());
        System.out.println("Họ và tên: ");
        String fullName = Validate.validateString();
        users.setName(fullName);
        System.out.println("Email: ");
        String email = Validate.validateEmail();
        if (!email.isEmpty()) {
            users.setEmail(email);
        }
        userServiceIMPL.save(users);
        usersLogin = users;
        new Config<Users>().writeFile(Config.URL_USER_LOGIN, usersLogin);

        System.out.println("Thông tin cá nhân đã được cập nhật thành công.");
    }

    private void showUser() {
        int nameWidth = 50;
        String name = String.format("Họ và tên: %-" + (nameWidth - 14) + "s", usersLogin.getName());
        int emailWith = 46;
        String email = String.format("Email: %-" + (emailWith - 14) + "s", usersLogin.getEmail());
        int userloginWith = 43;
        String userlogin = String.format("Tên tài khoản: %-" + (userloginWith - 10) + "s", usersLogin.getUsername());
        String userInfor =
               LIGHT_CYAN+ "╔══════════════════════"+ORANGE_2+"THÔNG TIN CÁ NHÂN"+LIGHT_CYAN+"═════════════════════════╗\n" +
                        "║                                                                ║\n" +
                       "║                " + ORANGE_2 + name + LIGHT_CYAN + " ║\n" +
                       "║                " + ORANGE_2 + email + LIGHT_CYAN + "         ║\n" +
                       "║                " + ORANGE_2 + userlogin + LIGHT_CYAN + "║\n" +
                       "╚════════════════════════════════════════════════════════════════╝"+RESET;

        System.out.println(userInfor);
    }

}

