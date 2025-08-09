package practice_section_10.custom_problem_1;

import java.time.LocalDateTime;

public class Order {

    private final String orderId;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int price;
    private final LocalDateTime orderTime;

    public Order(String orderId, String customerName, String productName, int quantity, int price, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderTime = orderTime;
    }

    public int getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
