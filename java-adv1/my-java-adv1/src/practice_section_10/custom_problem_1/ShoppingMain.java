package practice_section_10.custom_problem_1;

import static utill.MyLogger.log;

public class ShoppingMain {

    private final static BoundedQueue queue = new BoundedQueue(15);
    private final static OnlineOrderSystem system = new OnlineOrderSystem();

    public static void main(String[] args) throws InterruptedException {

        log("=== 온라인 주문 처리 시스템 시작 ===");

        int PRODUCER_THREAD_COUNT = 5;
        int CONSUMER_THREAD_COUNT = 3;

        Thread[] producerThreads = new Thread[PRODUCER_THREAD_COUNT];
        Thread[] consumerThreads = new Thread[CONSUMER_THREAD_COUNT];

        for (int i = 1; i <= PRODUCER_THREAD_COUNT; i++) {
            producerThreads[i-1] = new Thread(new ProducerTask(queue, 8), "producer" + i);
            producerThreads[i-1].start();
            Thread.sleep(300);
        }

        for (int i = 1; i <= CONSUMER_THREAD_COUNT; i++) {
            consumerThreads[i-1] = new Thread(new ConsumerTask(queue, system), "consumer-" + i);
            consumerThreads[i-1].start();
        }

        for (int i = 0; i < PRODUCER_THREAD_COUNT; i++) {
            producerThreads[i].join();
        }

        Thread.sleep(15000);

        for (int i = 0; i < CONSUMER_THREAD_COUNT; i++) {
            consumerThreads[i].interrupt();
        }

        log("=== 주문 처리 완료 ===");
        log("총 처리 주문: " + system.getProcessedOrders() + "개");
        log("총 매출액: " + system.getTotalRevenue() + "원");

    }

}
