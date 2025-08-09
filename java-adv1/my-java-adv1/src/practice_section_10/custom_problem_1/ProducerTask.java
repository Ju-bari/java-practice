package practice_section_10.custom_problem_1;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static utill.MyLogger.log;

public class ProducerTask implements Runnable {

    private static final AtomicInteger orderIdGenerator = new AtomicInteger(1);

    private final BoundedQueue queue;
    private final int orderCount;

    private final String[] customerNames = {"세종대왕", "이이", "김정희", "정약용", "정도전", "송시열", "이황"};
    private final String[] productNames = {"클렌징오일", "마라탕", "전자레인지", "무선이어폰", "그램", "선글라스", "선풍기"};
    private final Random random = new Random();

    public ProducerTask(BoundedQueue queue, int orderCount) {
        this.queue = queue;
        this.orderCount = orderCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < orderCount; i++) {
            String orderId = generateOrderId(orderIdGenerator.getAndIncrement());
            Order order = new Order(orderId,
                    customerNames[random.nextInt(7)],
                    productNames[random.nextInt(7)],
                    random.nextInt(100)+1,
                    random.nextInt(10000)+1,
                    LocalDateTime.now());

            queue.put(order);
            log("주문 생성: " + order.toString());

            // 3초 대기
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String generateOrderId(int number) {
        return "ORD" + String.format("%03d", number);
    }

}
