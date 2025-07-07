package practice_section3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// 게임 플레이어 클래스
class NumberGame implements Runnable {
    private final String playerName;
    private final int targetNumber;
    private final int maxAttempts;
    private final AtomicBoolean gameWon;
    private final AtomicReference<String> winner;
    private final Random random;
    private int minRange = 1;
    private int maxRange = 100;

    public NumberGame(String playerName, int targetNumber, int maxAttempts,
                      AtomicBoolean gameWon, AtomicReference<String> winner) {
        this.playerName = playerName;
        this.targetNumber = targetNumber;
        this.maxAttempts = maxAttempts;
        this.gameWon = gameWon;
        this.winner = winner;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            // 이미 다른 플레이어가 우승했다면 게임 종료
            if (gameWon.get()) {
                break;
            }

            // 현재 범위 내에서 랜덤 숫자 생성
            int guess = random.nextInt(maxRange - minRange + 1) + minRange;

            // 추측 결과 확인
            String result;
            if (guess == targetNumber) {
                result = "정답!";
                // 원자적으로 우승자 설정
                if (gameWon.compareAndSet(false, true)) {
                    winner.set(playerName + " (" + attempt + "번 시도)");
                }
            } else if (guess > targetNumber) {
                result = "너무 높음";
                // 다음 시도를 위해 범위 조정
                maxRange = guess - 1;
            } else {
                result = "너무 낮음";
                // 다음 시도를 위해 범위 조정
                minRange = guess + 1;
            }

            // 결과 출력
            System.out.println("[" + Thread.currentThread().getName() + "] " +
                    "[" + playerName + "] 시도 " + attempt + ": " + guess + " -> " + result);

            // 정답을 맞췄다면 게임 종료
            if (guess == targetNumber) {
                break;
            }

            // 사고하는 시간 (500ms ~ 1500ms)
            try {
                Thread.sleep(500 + random.nextInt(1001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// 게임 진행 상황을 체크하는 데몬 스레드
class GameResultCollector implements Runnable {
    private final AtomicBoolean gameWon;
    private final long startTime;

    public GameResultCollector(AtomicBoolean gameWon) {
        this.gameWon = gameWon;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] GameResultCollector 시작");

        while (!gameWon.get()) {
            try {
                Thread.sleep(2000); // 2초마다 체크

                if (!gameWon.get()) {
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                    System.out.println("[" + Thread.currentThread().getName() + "] " +
                            "게임 진행 상황 체크... 경과 시간: " + elapsedTime + "초");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// 메인 클래스
public class custom_problem_2_2 {
    public static void main(String[] args) {
        // 게임 설정
        final int TARGET_NUMBER = 42;
        final int MAX_ATTEMPTS = 30;
        final String[] PLAYERS = {"Alice", "Bob", "Charlie"};

        // 공유 상태 변수들
        AtomicBoolean gameWon = new AtomicBoolean(false);
        AtomicReference<String> winner = new AtomicReference<>(null);

        System.out.println("[" + Thread.currentThread().getName() + "] 숫자 게임 시작! 정답: " + TARGET_NUMBER);

        // 데몬 스레드 시작
        Thread collectorThread = new Thread(new GameResultCollector(gameWon));
        collectorThread.setDaemon(true);
        collectorThread.start();

        // 플레이어 스레드들 생성 및 시작
        Thread[] playerThreads = new Thread[PLAYERS.length];
        for (int i = 0; i < PLAYERS.length; i++) {
            NumberGame game = new NumberGame(PLAYERS[i], TARGET_NUMBER, MAX_ATTEMPTS, gameWon, winner);
            playerThreads[i] = new Thread(game);
            playerThreads[i].start();
        }

        // 모든 플레이어 스레드 완료 대기
        for (Thread thread : playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 게임 결과 출력
        String finalWinner = winner.get();
        if (finalWinner != null) {
            System.out.println("[" + Thread.currentThread().getName() + "] 우승자: " + finalWinner);
        } else {
            System.out.println("[" + Thread.currentThread().getName() + "] 최대 도전 횟수까지 맞춘 인원이 없습니다.");
        }
    }
}