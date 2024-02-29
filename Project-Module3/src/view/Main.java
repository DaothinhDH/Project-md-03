package view;

import ra.config.Config;
import ra.model.Users;
import view.home.Login;

public class Main {
    public static void main(String[] args) {
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        if (userLogin != null) {
            new Login().checkRolelogin(userLogin);
        }else {
            new Login().menuHome();
        }

        new Login().menuHome();
    }
}