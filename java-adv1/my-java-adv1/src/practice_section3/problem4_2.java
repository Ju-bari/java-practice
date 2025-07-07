package practice_section3;

import static practice_section3.MyLogger.log;

public class problem4_2 {
    public static void main(String[] args) {

        Thread threadA = new Thread(new Printer("A", 1000), "Thread-A");
        threadA.start();
        Thread threadB = new Thread(new Printer("B", 500), "Thread-B");
        threadB.start();

    }

    static class Printer implements Runnable {
        String content;
        int time;

        Printer(String content, int time) {
            this.content = content;
            this.time = time;
        }

        @Override
        public void run() {
            while (true) {
                log(content);

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
