package practice_section_14.custom_problem_1;

import java.util.*;
import java.util.concurrent.*;

import static utill.MyLogger.log;

public class ChatSystemProcessor {

    private final ExecutorService messagePool;
    private final ExecutorService filterPool;
    private final ExecutorService statsPool;

    private final Map<String, ChatRoomStats> roomStatsMap;
    private final List<String> bannedWords;

    private final Random random = new Random();

    public ChatSystemProcessor() {
        messagePool = Executors.newFixedThreadPool(4);
        filterPool = Executors.newCachedThreadPool();
        statsPool = Executors.newFixedThreadPool(2);
        roomStatsMap = new ConcurrentHashMap<>(); // 스레드 안정성
        bannedWords = List.of("바보", "멍청이", "나쁜말", "욕설");
    }

    public void initializeRoom(List<String> rooms) {
        roomStatsMap.clear();
        for (String room : rooms) {
            roomStatsMap.put(room, new ChatRoomStats(room));
        }
    }

    public Future<String> processMessage(Message message) {
        return messagePool.submit(() -> {
            try {
                log("메시지 처리 시작 -> " + "RoomId: " + message.getRoomId() + ", UserId: " + message.getUserId());
                Thread.sleep(random.nextInt(1000, 3001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return String.format("%s-%s 메시지 처리 완료", message.getRoomId(), message.getUserId());
        });
    }

    public Future<Boolean> filterMessage(Message message) {
        return filterPool.submit(() -> {
            try {
                log("메시지 필터 시작 -> " + "RoomId: " + message.getRoomId() + ", UserId: " + message.getUserId());
                Thread.sleep(random.nextInt(100, 201));
                String content = message.getContent();
                for (String bannedWord : bannedWords) {
                    if (content.contains(bannedWord)) {
                        return true;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return false;
        });
    }

    public Future<ChatRoomStats> updateRoomStats(Message message) {
        return statsPool.submit(() -> {
                    try {
                        log("룸 통계 업데이트 시작 -> " + "RoomId: " + message.getRoomId() + ", UserId: " + message.getUserId());
                        Thread.sleep(random.nextInt(500, 1001));
                        ChatRoomStats roomStats = roomStatsMap.get(message.getRoomId());
                        if (roomStats == null) {
                            log("해당 하는 방을 찾을 수 없습니다: " + message.getRoomId());
                        }
                        roomStats.incrementMessage();
                        return roomStats;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                }
        );
    }

    public void processAllMessages(List<Message> messages) {

        for (Message message : messages) {
            long messageProcessStartTime = System.currentTimeMillis();
            try {
                String processedMessage = processMessage(message).get();
                Boolean isBadWord = filterMessage(message).get();
                ChatRoomStats updatedRoomStats = updateRoomStats(message).get();
                long messageProcessEndTime = System.currentTimeMillis();
                long elapsedTime = messageProcessEndTime - messageProcessStartTime;

                log("완료: [ " + message.getRoomId() + " ]-[ " + message.getUserId() + " ] | " +
                        "처리: [ " + processedMessage + " ] | 욕설: [ " + isBadWord  + " ] | " +
                        "방 점수: " + updatedRoomStats.getScore() + " | 소요시간: " + elapsedTime + "ms");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void printFinalStats() {
        for (ChatRoomStats roomStats : roomStatsMap.values()) {
            System.out.println(roomStats.toString());
        }
    }

    public void shutdown() {
        shutdownAndAwaitTermination(messagePool);
        shutdownAndAwaitTermination(filterPool);
        shutdownAndAwaitTermination(statsPool);
    }

    public void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않습니다: shutdown, shutdownNow 둘 다 시행");
                }
            } else {
                log(pool.getClass() + " - shutdown 종료 완료");
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

}
