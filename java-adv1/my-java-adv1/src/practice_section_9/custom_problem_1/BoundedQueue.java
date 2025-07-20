package practice_section_9.custom_problem_1;

import java.util.ArrayDeque;
import java.util.Queue;

import static utill.MyLogger.log;

public class BoundedQueue {

    private final Queue<Order> queue = new ArrayDeque<>();
    private final int maxSize;

    public BoundedQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(Order order) throws InterruptedException {
        while (queue.size() >= maxSize) { // while 사용 조심
            log("[put] 큐가 가득 참, 생산자 대기");
            wait();
            log("[put] 생산자 깨어남");
        }

        log("[put] 생산자 데이터 저장, notify() 호출");
        queue.offer(order);
        notify();
    }

    public synchronized Order take() throws InterruptedException {
        while (queue.isEmpty()) { // while 사용 조심
            log("[take] 큐에 데이터 없음, 소비자 대기");
            wait();
            log("[take] 소비자 깨어남");
        }

        log("[take] 소비자 데이터 획득, notify() 호출");
        Order order = queue.poll();
        notify();
        return order;
    }

}
