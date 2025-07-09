package practice_section3;

import static utill.MyLogger.log;

public class problem_2 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    log("value: " + (i+1));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread thread = new Thread(runnable); // new Thread(runnable, "counter");
        thread.setName("counter");
        thread.start();
    }
}
