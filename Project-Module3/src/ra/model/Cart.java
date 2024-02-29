package ra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idCart;
    private int idUser;
    private Map<Integer,Integer> productCart;
    private boolean status = false;

    public Cart() {
    }

    public Cart(int idCart, int idUser) {
        this.idCart = idCart;
        this.idUser = idUser;
        this.productCart = new HashMap<Integer,Integer>();
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Map<Integer, Integer> getProductCart() {
        return productCart;
    }

    public void setProductCart(Map<Integer, Integer> productCart) {
        this.productCart = productCart;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addProduct(int product,int quantity){
        productCart.put(product, quantity);
    }
    public void removeProduct(int productId) {
        productCart.remove(productId);
    }

    public int getProductQuantity(int productId) {
        return productCart.getOrDefault(productId,0);
    }
    @Override
    public String toString() {
        return "Cart{" +
                " Mã giỏ hàng: " + idCart +
                ", Mã người dùng: " + idUser +
                ", Giỏ hàng: " + productCart +
                ", Trạng thái: " + (status ? "Đã thanh toán" : "Chưa thanh toán") +
                '}';
    }

}
