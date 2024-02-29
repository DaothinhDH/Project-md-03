package sevice.user;

import ra.config.Config;
import ra.model.Products;
import ra.model.RoleName;
import ra.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService {
    static Config<List<Users>> config = new Config<>();
    public static List<Users> usersList;
    static {
        usersList = config.readFile(Config.URL_USERS);
        if (usersList == null){
            usersList = new ArrayList<>();
            usersList.add(new Users(0,"ADMIN","admin","admin","admin@gmail.com",true, RoleName.ADMIN));
            new UserServiceIMPL().updateData();
        }
    }
    @Override
    public List<Users> findAll() {
        return usersList;
    }
    @Override
    public void save(Users users) {

        //kiem tra users co ton taij trong usersList khong
        if (findById(users.getId()) == null){ // neu chua ton tai trong danh sach
            usersList.add(users); // thi them moi
            updateData();
        }else {
            usersList.set(usersList.indexOf(users),users); // neu da ton tai thi set lai users (update)
            updateData();
        }
    }


    @Override
    public void delete(int id) {
        usersList.remove(findById(id));
        updateData();
    }
    @Override
    public Users findById(int id) {
        for (Users users : usersList){
            if (users.getId() == id){
                return users;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Users users : usersList) {
            if (users.getId() > idMax){
                idMax = users.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_USERS, usersList);
    }

    @Override
    public void update(Users users) {
        updateData();
    }

    @Override
    public List<Products> deleteProducts(int id) {
        return null;
    }

    @Override
    public boolean existUsername(String username) {
        for (Users users : usersList) {
            if (users.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        for (Users users : usersList) {
            if (users.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Users checkLogin(String username, String password) {
        for (Users users : usersList) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)){
                return users;
            }
        }
        return null;
    }

    @Override
    public List<Users> findName(String name) {
        List <Users> foundUser = new ArrayList<>();
        for (Users users : usersList){
            if (users.getName().toLowerCase().contains(name)){
                foundUser.add(users);
            }
        }
        return foundUser;
    }
}