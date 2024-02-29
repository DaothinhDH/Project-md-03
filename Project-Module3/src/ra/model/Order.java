package ra.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private List<Orderdetail> orderDetails;
    private Date orderAt;
    private Date deliverAt;


    public Order(int orderId, int userId, String userName, String numberphone, String address, double total, OrderStatus orderStatus, List<Orderdetail> orderdetails, Date orderdate) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = userName;
        this.phoneNumber = numberphone;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderDetails = orderdetails;
        this.orderAt = orderdate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Orderdetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<Orderdetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Date orderAt) {
        this.orderAt = orderAt;
    }

    public Date getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(Date deliverAt) {
        this.deliverAt = deliverAt;
    }

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public String toString() {
        return
                "Mã đơn hàng: " + orderId +
                        " - Mã người dùng: " + userId +
                        " - Tên người đặt: " + name +
                        " - Số điện thoại: " + phoneNumber +
                        " - Địa chỉ: " + address +
                        " - Tổng giá tiền: " + total +
                        " - Trạng thái đơn hàng: " + orderStatus +
                        " - Chi tiết đơn hàng: " + orderDetails +
                        " - Thời gian đặt hàng: " + orderAt;
    }

    public void display() {
        System.out.printf("║   %-4d║   %-13s║    %-14s║   %-18s║    %-18s║  %-13s║   %-11s║\n",
                orderId, name, phoneNumber, address, dateFormat.format(orderAt), (formatter.format(total) + " VND"),
                (orderStatus == OrderStatus.WAITING ? "Đang Đợi" : orderStatus == OrderStatus.CONFIRM ? "Xác Nhận" : "Hủy Đơn"));
    }
}