package practice_section3;

import static utill.MyLogger.log;

public class problem_4 {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        threadA.setName("threadA");
        threadA.start();

        ThreadB threadB = new ThreadB();
        threadB.setName("threadB");
        threadB.start();

    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            while (true) {
                log("A");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            while (true) {
                log("B");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
