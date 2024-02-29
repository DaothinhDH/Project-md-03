package sevice.order;

import ra.model.Cart;
import ra.model.Order;
import sevice.IGenericService;

import java.util.List;

public interface IOderService extends IGenericService<Order> {
    List<Order> findByName(String name);
}
