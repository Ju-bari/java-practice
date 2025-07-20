package practice_section_9.custom_problem_1;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static utill.MyLogger.log;

public class OrderTask implements Runnable {

//    private static int orderIdGenerator = 1;
    private static final AtomicInteger orderIdGenerator = new AtomicInteger(1);

    private final BoundedQueue kitchenQueue;
    private final int orderCount;

    private final String[] menus = {"치킨버거", "피자", "파스타", "돈까스", "김치찌개"};
    private final String[] addresses = {"강남구", "서초구", "중구", "마포구", "용산구"};
    private final Random random = new Random();

    OrderTask(BoundedQueue kitchenQueue, int orderCount) {
        this.kitchenQueue = kitchenQueue;
        this.orderCount = orderCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < orderCount; i++) {
            try {
                int orderId = orderIdGenerator.getAndIncrement();
                Order order = new Order(orderId, menus[random.nextInt(addresses.length)], addresses[random.nextInt(addresses.length)]);
                log("주문 생성: " + order.toString());
                kitchenQueue.put(order);
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
