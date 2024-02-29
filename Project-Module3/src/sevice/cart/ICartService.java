package sevice.cart;

import ra.model.Cart;
import sevice.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findCartByUserLogin( int userId );

}
