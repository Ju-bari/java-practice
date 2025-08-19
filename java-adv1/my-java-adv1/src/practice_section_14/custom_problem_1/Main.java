package practice_section_14.custom_problem_1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChatSystemProcessor processor = new ChatSystemProcessor();

        // 방 생성
        List<String> rooms = new ArrayList<>();
        rooms.add("room1");
        rooms.add("room2");
        rooms.add("room3");
        processor.initializeRoom(rooms);

        // 메시지 생성
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("room1", 1001L, "안녕하세요 좋은 아침입니다"));
        messages.add(new Message("room1", 1002L, "바보 같은 소리네요"));
        messages.add(new Message("room1", 1003L, "좋은 하루 되세요"));

        messages.add(new Message("room2", 1001L, "반갑습니다 여러분"));
        messages.add(new Message("room2", 1004L, "멍청이네 정말"));
        messages.add(new Message("room2", 1002L, "오늘 날씨가 정말 좋네요"));
        messages.add(new Message("room2", 1005L, "회의는 언제 시작하나요"));

        messages.add(new Message("room3", 1005L, "나쁜말 하지 말아요"));
        messages.add(new Message("room3", 1001L, "점심은 뭐 먹을까요"));
        messages.add(new Message("room3", 1006L, "회의 곧 시작합니다"));
        messages.add(new Message("room3", 1007L, "욕설은 안 좋습니다"));
        messages.add(new Message("room3", 1008L, "즐거운 오후 되세요"));

        // 작업 시작
        processor.processAllMessages(messages);
        processor.printFinalStats();
        processor.shutdown();
    }

}
