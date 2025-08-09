package practice_section_10.custom_problem_1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. synchronized 메서드
 * 2. ReentrantLock
 * 3. CAS 연산 (이후 섹션)
 */
public class OnlineOrderSystem {

//    private int totalRevenue;
//    private int processedOrders;
    private final AtomicInteger totalRevenue = new AtomicInteger(0);
    private final AtomicInteger processedOrders = new AtomicInteger(0);

    public void processOrder(Order order) { // synchronized
//        totalRevenue += order.getTotalPrice();
//        processedOrders++;
        totalRevenue.addAndGet(order.getTotalPrice());
        processedOrders.incrementAndGet();
    }

    public int getTotalRevenue() {
//        return totalRevenue;
        return totalRevenue.get();
    }

    public int getProcessedOrders() {
//        return processedOrders;
        return processedOrders.get();
    }

}
