package practice_section_10.custom_problem_1;

import static utill.MyLogger.log;

public class ConsumerTask implements Runnable {

    private final BoundedQueue queue;
    private final OnlineOrderSystem system;

    public ConsumerTask(BoundedQueue queue, OnlineOrderSystem system) {
        this.queue = queue;
        this.system = system;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Order order = queue.take();
                log("주문 처리 시작: " + order.toString());
                system.processOrder(order);
                Thread.sleep(1000);
                log("주문 처리 완료: " + order.toString());
            } catch (InterruptedException | RuntimeException e) {
//                log("소비자 스레드 종료");
                break;
            }
        }
    }

}
