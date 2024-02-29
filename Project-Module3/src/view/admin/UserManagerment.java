package view.admin;

import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import sevice.user.IUserService;
import sevice.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

import static ra.config.Color.*;
import static ra.config.Color.RESET;

public class UserManagerment {
    IUserService userService = new UserServiceIMPL();

    public void menuUser() {
        int choice;
        do {
            System.out.println(LIGHT_CYAN + "╔═════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                               "+ORANGE_2+"MENU User"+LIGHT_CYAN+"                                             ║");
            System.out.println("╠════╦════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1.║                       "+WHITE_BOLD_BRIGHT+"Hiển thị danh sách người dùng"+LIGHT_CYAN+"                            ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  2.║                       "+WHITE_BOLD_BRIGHT+"Tìm kiếm người dùng theo tên"+LIGHT_CYAN+"                             ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  3.║                       "+WHITE_BOLD_BRIGHT+"Block/Unblock tài khoản người dùng"+LIGHT_CYAN+"                       ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  4.║                       "+WHITE_BOLD_BRIGHT+"Thay đổi quyền truy cập tài khoản"+LIGHT_CYAN+"                        ║");
            System.out.println("╠════╬════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  0.║                       "+WHITE_BOLD_BRIGHT+"Quay lại"+LIGHT_CYAN+"                                                 ║");
            System.out.println("╚════╩════════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "Lựa chọn (0/1/2): " + RESET);
            choice = Validate.validateInt();
            switch (choice) {
                case 1:
                    showUser();
                    break;
                case 2:
                    seachUser();
                    break;
                case 3:
                    hideUser();
                    break;
                case 4:
                    roleUser();
                case 0:
                    return;
                default:
                    System.out.println(RED + "lựa chọn không hợp lệ vui lòng nhập lại" + RESET);
                    break;
            }

        } while (true);
    }

    private void roleUser() {
        System.out.println("Nhập ID tài để thay đổi quyền truy cập");
        int idRole = Validate.validateInt();
        Users users = userService.findById(idRole);
        if (users.getId()!=0) {
            if (users != null) {
                System.out.println("1. admin");
                System.out.println("2. User");
                int choice = Validate.validateInt();
                switch (choice) {
                    case 1:
                        users.setRole(RoleName.ADMIN);
                        break;
                    case 2:
                        users.setRole(RoleName.USER);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
                userService.save(users);

            }
        }else {
            System.out.println(YELLOW+"Tài khoản admin không được thay dổi"+RESET);
        }
    }

    private void showUser() {
        System.out.println("Tất cá người dùng");
        for (Users users : userService.findAll()) {
            System.out.println(users);
        }
    }

    private void hideUser() {
        System.out.println("Nhập ID người dùng muốn khóa/mở");
        int idUser = Validate.validateInt();
        if (idUser != 0) {
            Users users = userService.findById(idUser);
            if (users != null) {
                System.out.println("1.Khóa tài khoản");
                System.out.println("2.Mở Tài khoản");
                int choice = Validate.validateInt();
                switch (choice) {
                    case 1:

                        users.setStatus(false);
                        System.out.println("Tài khoản đã được khóa thành công");

                        userService.save(users);
                        break;
                    case 2:

                        users.setStatus(true);
                        System.out.println("Tài khoản đã được mở khóa thành công");

                        userService.save(users);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                        break;
                }
            } else {
                System.out.println(YELLOW + "Không tìm thấy" + RESET);
            }
        }else {
            System.out.println("Không được thay đổi admin");
        }
    }

    private void seachUser() {
        System.out.println(YELLOW+"Nhập từ khóa tìm kiếm"+RESET);
        String seachUser = Validate.validateString();
        List<Users> foundUsers = userService.findName(seachUser);
        if (foundUsers.isEmpty()){
            System.out.println(YELLOW+"Không tìm thấy người dùng"+RESET);
        }else {
            for (Users users : foundUsers) {
                System.out.println(users);
            }
        }

    }
}

































