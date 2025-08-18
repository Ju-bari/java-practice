package practice_section_13.custom_problem_2;

public class TradeOrder {

    private final String orderId;
    private final String symbol;
    private final int quantity;
    private final TradeOrderType orderType;

    public TradeOrder(String orderId, String symbol, int quantity, TradeOrderType orderType) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public TradeOrderType getOrderType() {
        return orderType;
    }

    @Override
    public String toString() {
        return "TradeOrder{" +
                "orderId='" + orderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", quantity=" + quantity +
                ", orderType='" + orderType + '\'' +
                '}';
    }

}
