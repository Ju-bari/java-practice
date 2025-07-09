package practice_section4;

import static java.lang.Thread.sleep;
import static utill.MyLogger.log;

public class custom_problem_1 {
    public static void main(String[] args) throws InterruptedException {
        ArraySumTask arraySumTask1 = new ArraySumTask(1, 5);
        ArraySumTask arraySumTask2 = new ArraySumTask(6, 10);
        ArraySumTask arraySumTask3 = new ArraySumTask(11, 15);

        Thread t1 = new Thread(arraySumTask1);
        Thread t2 = new Thread(arraySumTask2);
        Thread t3 = new Thread(arraySumTask3);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        int sumAll = arraySumTask1.sum + arraySumTask2.sum + arraySumTask3.sum;
        log("전체 합계: " + sumAll);
    }

    static class ArraySumTask implements Runnable {

        int start;
        int end;
        int sum;

        public ArraySumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                sum += i;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log("작업 완료 result = " + sum);
        }
    }
}
