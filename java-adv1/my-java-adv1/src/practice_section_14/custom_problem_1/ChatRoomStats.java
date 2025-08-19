package practice_section_14.custom_problem_1;

public class ChatRoomStats {

    private final String roomId;
    private int messageCount;
    private int score;

    public ChatRoomStats(String roomId) {
        this.roomId = roomId;
        this.messageCount = 0;
        this.score = 0;
    }

    // 동시성 문제 발생 -> CAS도 가능
    public synchronized void incrementMessage() {
        messageCount++;
        score = messageCount * 10 + 50;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ChatRoomStats{" +
                "roomId='" + roomId + '\'' +
                ", messageCount=" + messageCount +
                ", score=" + score +
                '}';
    }

}
