package view.admin;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Users;
import view.home.Login;

import static ra.config.Color.*;

public class AdminManager {
    public void menuAmin() {
        do {
//            System.out.println("\nXin chao: "+ Home.userLogin.getName());
            System.out.println(LIGHT_CYAN + "╔═══════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                       " + ORANGE_2 + "MENU ADMIN" + LIGHT_CYAN + "                                      ║");
            System.out.println("╠═══╦═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 1 ║                                   " + WHITE_BOLD_BRIGHT + "QUẢN LÝ SẢN PHẨM" + LIGHT_CYAN + "                                ║");
            System.out.println("╠═══╬═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 2 ║                                   " + WHITE_BOLD_BRIGHT + "QUẢN LÝ DANH MỤC" + LIGHT_CYAN + "                                ║");
            System.out.println("╠═══╬═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 3 ║                                   " + WHITE_BOLD_BRIGHT + "QUẢN LÝ NGƯỜI DÙNG" + LIGHT_CYAN + "                              ║");
            System.out.println("╠═══╬═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 4 ║                                   " + WHITE_BOLD_BRIGHT + "QUẢN LÝ ĐƠN HÀNG" + LIGHT_CYAN + "                                ║");
            System.out.println("╠═══╬═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 0 ║                                   " + WHITE_BOLD_BRIGHT + "Log out" + LIGHT_CYAN + "                                         ║");
            System.out.println("╚═══╩═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "Lựa chọn (0/1/2): " + RESET);

            switch (Validate.validateInt()) {

                case 0:
                    new Config<Users>().writeFile(Config.URL_USER_LOGIN, null);
                    new Login().menuHome();
                    break;
                case 1:
                    new ProductManagerment().menuProduct();
                    break;
                case 2:
                    new CatalogManagerment().menuCatalog();
                    break;
                case 3:
                    new UserManagerment().menuUser();
                    break;
                case 4:
                    new OderMannager().menuOder();
                    break;
                default:
                    System.out.println(RED+"Lựa chọn không hợp lệ. Vui lòng chọn lại."+RESET);
                    break;
            }
        } while (true);
    }
}