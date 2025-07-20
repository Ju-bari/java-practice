package practice_section_9.custom_problem_1;

import static java.lang.Thread.sleep;
import static utill.MyLogger.log;

public class ChefTask implements Runnable {

    private final BoundedQueue kitchenQueue;
    private final BoundedQueue pickupQueue;

    public ChefTask(BoundedQueue kitchenQueue, BoundedQueue pickupQueue) {
        this.kitchenQueue = kitchenQueue;
        this.pickupQueue = pickupQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Order order = kitchenQueue.take();
                sleep(2000);
                pickupQueue.put(order);
                log("요리 완료: " + order.toString());
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
