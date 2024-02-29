package ra.config;
import java.io.*;
import java.util.Scanner;

public class Config<T> {
    public static Scanner scanner(){
        return new Scanner(System.in);
    }
    public static final String URL_USERS = "src/ra/config/data/users.txt";
    public static final String URL_USER_LOGIN = "src/ra/config/data/usersLogin.txt";
    public static final String URL_PRODUCT = "src/ra/config/data/product.txt";
    public static final String URL_CATALOG = "src/ra/config/data/catalog.txt";
    public static final String URL_CART = "src/ra/config/data/cart.txt";
    public static final String URL_ODER = "src/ra/config/data/oder.txt";

    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.out.println("Find not found!!!");
        } catch (IOException e) {
            System.out.println("Write file Error!!!");
        }
    }
    public T readFile(String PATH_FILE){
        File file = new File(PATH_FILE);
        T t = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null){
                fis.close();
            }
            if (ois != null){
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Find not found!!!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Read file Error!!!");
        }
        return t;
    }

}