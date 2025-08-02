package practice_section_13.problem_1;

import java.util.concurrent.ExecutionException;

public class OrderServiceTestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String orderNo = "Order#1234";
//        OrderService orderService = new OrderService();
        OrderServiceWithInvolve orderService = new OrderServiceWithInvolve();
        orderService.order(orderNo);

    }
}
