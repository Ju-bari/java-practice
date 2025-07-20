package practice_section_9.custom_problem_1;

import static java.lang.Thread.sleep;
import static practice_section_9.custom_problem_1.DeliveryMain.DeliveryCompleteCount;
import static utill.MyLogger.log;

public class DeliveryTask implements Runnable {

    private final BoundedQueue pickupQueue;

    public DeliveryTask(BoundedQueue pickupQueue) {
        this.pickupQueue = pickupQueue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) { // 부정 ! 조심
            try {
                Order order = pickupQueue.take();
                sleep(2000);
                log("배달 완료: " + order.toString());
                DeliveryCompleteCount.incrementAndGet();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
