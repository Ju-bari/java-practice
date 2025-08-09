package practice_section_10.custom_problem_1;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utill.MyLogger.log;

public class BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition producerCond = lock.newCondition();
    private final Condition consumerCond = lock.newCondition();

    private final Queue<Order> queue = new ArrayDeque<>();
    private final int maxSize;

    public BoundedQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void put(Order order) {
        lock.lock();
        try {
            while (queue.size() == maxSize) {
                try {
                    producerCond.await();
//                    log("[put] 생산자 깨어남 -> 현재 큐 상태: " + queue.size() + "/" + maxSize);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offer(order);
//            log("[put] 생산자 데이터 저장, consumerCond.signal() 호출 -> 현재 큐 상태: " + queue.size() + "/" + maxSize);
            consumerCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public Order take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    consumerCond.await();
//                    log("[take] 소비자 깨어남 -> 현재 큐 상태: " + queue.size() + "/" + maxSize);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Order order = queue.poll();
//            log("[take] 소비자 데이터 획득, producerCond.signal() 호출 -> 현재 큐 상태: " + queue.size() + "/" + maxSize);
            producerCond.signal();
            return order;
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        return queue.toString();
    }

}
