package practice_section3;

import static practice_section3.MyLogger.log;

public class problem1 {
    public static void main(String[] args) {
        CountThread countThread = new CountThread();
        countThread.start();
    }

    static class CountThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                log("value: " + (i+1));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(); // 예외 던지기
                }
            }

        }
    }
}
