package practice_section_13.custom_problem_2;

public class TradeResult {

    private final String orderId;
    private final TradeResultStatus status;
    private final String message;
    private final String errorType;

    public TradeResult(String orderId, TradeResultStatus status, String message, String errorType) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.errorType = errorType;
    }

    public TradeResult(String orderId, TradeResultStatus status, String message) {
        this(orderId, status, message, null);
    }

    public String getOrderId() {
        return orderId;
    }

    public TradeResultStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorType() {
        return errorType;
    }

    @Override
    public String toString() {
        return "TradeResult{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", errorType='" + errorType + '\'' +
                '}';
    }

}
