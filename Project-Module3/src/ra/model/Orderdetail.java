package ra.model;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Orderdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private int orderId;
    private String name;
    private double unitPrice;
    private int quantity;

    public Orderdetail(int productId, int orderId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFormattedUnitPrice() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(unitPrice);
    }

    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    public String toString() {
        return
                "Mã sản phẩm: " + productId +
                        " - Mã đơn hàng: " + orderId +
                        " - Tên sản phâm: " + name +
                        " - Giá tiền: " + unitPrice +
                        " - Số lượng: " + quantity;
    }

    public void display() {
        System.out.printf("|  %-3S|  %-24s|   %-6s|  %-15s|\n",
                productId, name, quantity, (formatter.format(unitPrice * quantity) + " VND"));
    }
}

