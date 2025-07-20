package practice_section_9.custom_problem_1;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static utill.MyLogger.log;

public class DeliveryMain {

    private static final int ORDER_THREAD_COUNT = 2;
    private static final int CHEF_THREAD_COUNT = 1;
    private static final int DELIVERY_THREAD_COUNT = 2;

    public static AtomicInteger DeliveryCompleteCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        log("=== 배달 주문 처리 시스템 시작 ===");

        // Queue
        BoundedQueue kitchenQueue = new BoundedQueue(3);
        BoundedQueue pickupQueue = new BoundedQueue(2);

        // Task
        Thread[] orderThreads = new Thread[ORDER_THREAD_COUNT];
        Thread[] chefThreads = new Thread[CHEF_THREAD_COUNT];
        Thread[] deliveryThreads = new Thread[DELIVERY_THREAD_COUNT];

        for (int i = 0; i < ORDER_THREAD_COUNT; i++) {
            orderThreads[i] = new Thread(new OrderTask(kitchenQueue, 4), "주문접수" + (i+1));
            orderThreads[i].start();
        }

        for (int i = 0; i < CHEF_THREAD_COUNT; i++) {
            chefThreads[i] = new Thread(new ChefTask(kitchenQueue, pickupQueue), "요리사" + (i+1));
            chefThreads[i].start();
        }

        for (int i = 0; i < DELIVERY_THREAD_COUNT; i++) {
            deliveryThreads[i] = new Thread(new DeliveryTask(pickupQueue), "배달원" + (i+1));
            deliveryThreads[i].start();
        }

        for (int i = 0; i < ORDER_THREAD_COUNT; i++) {
            orderThreads[i].join();
        }

        // 배달까지 대기 시간
        sleep(20000);

        for (int i = 0; i < CHEF_THREAD_COUNT; i++) {
            chefThreads[i].interrupt();
        }

        for (int i = 0; i < DELIVERY_THREAD_COUNT; i++) {
            deliveryThreads[i].interrupt();
        }

        log("=== 시스템 종료 ===");
        log("배달까지 완료된 주문: " + DeliveryCompleteCount + "개");
    }

}
