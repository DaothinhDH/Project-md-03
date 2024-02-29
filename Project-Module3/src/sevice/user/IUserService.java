package sevice.user;

import ra.model.Products;
import ra.model.Users;
import sevice.IGenericService;

import java.util.List;

public interface IUserService extends IGenericService<Users> {
    boolean existUsername(String username);
    boolean existEmail(String email);
    Users checkLogin(String username, String password);
    List<Users> findName(String name);


}