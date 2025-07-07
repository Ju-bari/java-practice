package practice_section3;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static practice_section3.MyLogger.log;

public class custom_problem2 {
    private final static ConcurrentLinkedQueue<String> winnerPlayer = new ConcurrentLinkedQueue<>(); // 동시성 안정한 컬렉션 사용
    private final static ConcurrentLinkedQueue<Integer> winnerAttempts = new ConcurrentLinkedQueue<>();
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        int TARGET_NUMBER = 42;
        int MAX_ATTEMPTS = 30;
        String[] playerNames = {"Alice", "Bob", "Charlie"};
        Thread[] threads = new Thread[playerNames.length];

        log("숫자 게임 시작! 정답: " + TARGET_NUMBER);


        // 데몬 스레드
        Thread gameResultCollector = new Thread(new GameResultCollectorRunnable());
        gameResultCollector.setDaemon(true);
        gameResultCollector.start();

        // 사용자 스레드
        for (int i = 0; i < playerNames.length; i++) {
            threads[i] = new Thread(new NumberGame(playerNames[i], TARGET_NUMBER, MAX_ATTEMPTS));
            threads[i].start();
        }

        // 모두 종료되지 않도록 join 추가 -> 모두 start하고 join을 해야 함
        for (Thread thread : threads) {
            thread.join();
        }

        if (!winnerPlayer.isEmpty()) {
            log("우승자: " + winnerPlayer.peek() + " (" + winnerAttempts.peek() + "번 시도)");
        } else {
            log("최대 도전 횟수까지 맞춘 인원이 없습니다.");
        }

    }

    static class GameResultCollectorRunnable implements Runnable {
        @Override
        public void run() {
            int totalTime = 2;
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log("게임 진행 상황 체크... 경과 시간:" + totalTime + "초");
                totalTime++;
            }
        }
    }

    static class NumberGame implements Runnable {
        String playerName;
        int targetNumber;
        int maxAttempts;

        public NumberGame(String playerName, int targetNumber, int maxAttempts) {
            this.playerName = playerName;
            this.targetNumber = targetNumber;
            this.maxAttempts = maxAttempts;
        }

        @Override
        public void run() {
            int tryAttempts = 1;
            boolean success = false;

            while (tryAttempts < maxAttempts) {
//                int tryNumber = (int) (Math.random() * 100 + 1);
                int tryNumber = random.nextInt(100) + 1;

                if (tryNumber == targetNumber) {
                    log("[" + playerName + "] 시도 " + tryAttempts + ": " + tryNumber + " -> 정답!");
                    success = true;
                    break;
                } else if (tryNumber > targetNumber) {
                    log("[" + playerName + "] 시도: " + tryAttempts + ": " + tryNumber + " -> 너무 높음");
                } else {
                    log("[" + playerName + "] 시도: " + tryAttempts + ": " + tryNumber + " -> 너무 낮음");
                }

                tryAttempts++;

                if (!winnerPlayer.isEmpty()) {
                    break;
                }

                // 사고 시간
//                int sleepTime = (int) (Math.random() * 1001 + 500);
                int sleepTime = random.nextInt(1000) + 500;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // 종료 로직
            if (success) {
                winnerPlayer.offer(playerName);
                winnerAttempts.offer(tryAttempts);
            }
        }

    }

}
