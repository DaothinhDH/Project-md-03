package view.home;

import ra.config.Config;
import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import sevice.user.UserServiceIMPL;
import sevice.user.IUserService;
import view.admin.AdminManager;
import view.acount.UserManager;
import static ra.config.Color.*;


public class Login {
    IUserService userService = new UserServiceIMPL();

    Config <Users> config = new Config<>();
    public void menuHome() {
        do {
            System.out.println(LIGHT_CYAN +"╔═══════════════════════════════════╗");
            System.out.println("║           "+WHITE_BOLD_BRIGHT+"MENU LOGIN"+LIGHT_CYAN+"              ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║            "+WHITE_BOLD_BRIGHT+"1. Login"+LIGHT_CYAN+"               ║");
            System.out.println("║            "+WHITE_BOLD_BRIGHT+"2. Register"+LIGHT_CYAN+"            ║");
            System.out.println("║            "+WHITE_BOLD_BRIGHT+"0. Thoat"+LIGHT_CYAN+"               ║");
            System.out.println("╚═══════════════════════════════════╝");
            System.out.print(YELLOW +"Lựa chọn (0/1/2): " +RESET);

            switch (Validate.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println(LIGHT_CYAN +"╔═══════════════════════════════╗");
        System.out.println("║          "+WHITE_BOLD_BRIGHT+"FORM LOGIN"+LIGHT_CYAN+"           ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.print("║"         +WHITE_BOLD_BRIGHT+" Nhập tên tài khoản"+LIGHT_CYAN+":           ║");
        System.out.println("                      ");
        System.out.println("╠═══════════════════════════════╣");
        System.out.print("║"        +WHITE_BOLD_BRIGHT+" Nhập mật khẩu"+LIGHT_CYAN+":                ║");
        System.out.println("                      ");
        System.out.println("╚═══════════════════════════════╝" +RESET);
        String username = Validate.validateString();
        String pass = Validate.validateString();
        Users users = userService.checkLogin(username,pass);
        //kiem tra
        if (users == null){
            System.out.println(RED +"Sai tên tài khoản hoặc mật khẩu" +RESET);
        }else {
            //dung ten tk voi mk
            if (users.getRole().equals(RoleName.ADMIN)){
                config.writeFile(Config.URL_USER_LOGIN,users); // ghi đối tượng Users đang đăng nhập vào file
                // chuyen den trang quanr ly ADMIN
                System.out.println(YELLOW+"Đăng nhập thành công"+RESET);
                new AdminManager().menuAmin();
            }else {
                if (users.isStatus()){
                    config.writeFile(Config.URL_USER_LOGIN,users); // ghi đối tượng Users đang đăng nhập vào file
                    // chuyen den trang user
                    System.out.println(YELLOW+"Đăng nhập thành công"+RESET);
                    new UserManager().menuUser();
                }else {
                    System.out.println(RED+"Tài khoản của bạn đã bị khóa"+RESET);
                }
            }
        }
    }

    private void register() {
        System.out.println(LIGHT_CYAN +"╔═════════════════════════════╗");
        Users users = new Users();
        users.setId(userService.getNewId());
//        System.out.println("Id: "+users.getId());
        System.out.println("║  Nhập họ tên:               ║ ");
        users.setName(Validate.validateString());
        System.out.println("║  Nhập tên tài khoản:        ║"+RESET);
        while (true){
            String username = Validate.validateString();
            if (userService.existUsername(username)){
                System.out.println(YELLOW+"Tên đăng nhập đã tồn tại mời nhập lại"+RESET);
            }else {
                users.setUsername(username);
                break;
            }
        }
        System.out.println(LIGHT_CYAN+"║  Nhập mật khẩu:             ║");
        users.setPassword(Validate.validateString());
        System.out.println("║  Nhập lại mật khẩu:         ║"+ RESET);
        while (true){
            String repeatPass = Validate.validateString();
            if (users.getPassword().equals(repeatPass)){
                break;
            }else {
                System.out.println(YELLOW+"Mật khẩu không đúng mời nhập lại"+RESET);
            }
        }
        System.out.println(LIGHT_CYAN+"║  Nhập Email:                 ║ "+RESET);
        while (true){
            String email = Validate.validateEmail();
            if (userService.existEmail(email)){
                System.out.println(YELLOW+"Email đã tồn tại mời nhập lại!"+RESET);
            }else {
                users.setEmail(email);
                break;
            }
        }
        userService.save(users);
        System.out.println("Tạo tài khoản thành công");

    }

    public void checkRolelogin(Users user) {
        if (user.getRole() == RoleName.ADMIN) {
            new Config<Users>().writeFile(Config.URL_USER_LOGIN, user); // ghi vào file
            System.out.println(WHITE_BOLD_BRIGHT+"XIN CHÀO, "+user.getUsername()+LIGHT_CYAN);
            new AdminManager().menuAmin();
        } else {
            if (user.isStatus()) {
                new Config<Users>().writeFile(Config.URL_USER_LOGIN, user); // ghi vào file
                System.out.println(WHITE_BOLD_BRIGHT+"XIN CHÀO, "+user.getUsername()+RESET);
                new UserManager().menuUser();
            } else {
                System.out.println(YELLOW+"Tài khoản của bạn đang bị khóa."+RESET);
            }
        }
    }
}