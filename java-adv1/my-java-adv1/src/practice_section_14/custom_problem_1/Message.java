package practice_section_14.custom_problem_1;

public class Message {

    private final String roomId;
    private final Long userId;
    private final String content;

    public Message(String roomId, Long userId, String content) {
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
    }

    public String getRoomId() {
        return roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

}
