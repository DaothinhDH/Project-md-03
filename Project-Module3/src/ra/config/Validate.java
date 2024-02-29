package ra.config;

public class Validate {
    public static int validateInt(){
        int n;
        while (true){
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Sai định dạng mời nhập lại");
            }
        }
        return n;
    }
    public static String validateString(){
        String s;
        while (true){
            s = Config.scanner().nextLine();
            if (s.isEmpty()){
                System.out.println("Không được để trống mời nhập lại");
            }else{
                break;
            }
        }
        return s;
    }

    public static String validateEmail(){
        String email;
        while (true) {
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println("Email không đúng định dạng mời nhập lại: ");
            }
        }
        return email;
    }
}