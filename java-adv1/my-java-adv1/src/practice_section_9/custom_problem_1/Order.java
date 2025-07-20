package practice_section_9.custom_problem_1;

public class Order {

    private final int orderId;
    private final String menu;
    private final String address;

    public Order(int orderId, String menu, String address) {
        this.orderId = orderId;
        this.menu = menu;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", menu='" + menu + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
