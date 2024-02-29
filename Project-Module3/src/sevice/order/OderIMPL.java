package sevice.order;

import ra.config.Config;
import ra.model.Cart;
import ra.model.Order;
import ra.model.Products;
import sevice.IGenericService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OderIMPL implements IOderService{
    static Config<List<Order>> config = new Config<>();
    static List<Order> ordersList;
    private Map<Integer,Integer> products;
    static {
        ordersList = config.readFile(Config.URL_ODER);
        ordersList = (ordersList == null) ? new ArrayList<>() : ordersList;
    }

    @Override
    public List<Order> findAll() {
        return ordersList;
    }

    @Override
    public void save(Order order) {
        if (findById(order.getOrderId()) == null){
            ordersList.add(order);
            updateData();
        }else {
            ordersList.set(ordersList.indexOf(order),order);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        Order orderDelete = findById(id);
        ordersList.remove(orderDelete);
        updateData();
    }

    @Override
    public Order findById(int id) {
        for (Order order : ordersList) {
            if (order.getOrderId() == id){
                return order;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Order order : ordersList) {
            if (order.getOrderId() > idMax){
                idMax = order.getOrderId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_ODER,ordersList);
    }

    @Override
    public void update(Order order) {
        Order oderEdit = findById(order.getOrderId());
        oderEdit.setName(order.getName());
        oderEdit.setPhoneNumber(order.getPhoneNumber());
        oderEdit.setAddress(order.getAddress());
        oderEdit.setTotal(order.getTotal());
        oderEdit.setOrderStatus(order.getOrderStatus());
        oderEdit.setOrderDetails(order.getOrderDetails());
        oderEdit.setOrderAt(order.getOrderAt());
        updateData();
    }
    @Override
    public List<Order> findByName(String name) {
        List<Order> foundOrder = new ArrayList<>();
        for (Order orders : ordersList) {
            if (orders.getName().trim().contains(name)){
                foundOrder.add(orders);
            }
        }
        return foundOrder;
    }
    @Override
    public List<Products> deleteProducts(int id) {
        return null;
    }

}
