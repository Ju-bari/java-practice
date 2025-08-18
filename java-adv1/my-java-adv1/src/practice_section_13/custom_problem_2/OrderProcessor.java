package practice_section_13.custom_problem_2;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;

import static utill.MyLogger.log;

public class OrderProcessor implements Callable<TradeResult> {

    private final TradeOrder order;

    private final Random random = new Random();

    public OrderProcessor(TradeOrder order) {
        this.order = order;
    }

    @Override
    public TradeResult call() {
        log(order.getOrderId() + " 처리 중...");

        try {
            Thread.sleep(random.nextInt(4000, 8001)); // 4~8초

            if (order.getOrderId().startsWith("ERROR")) {
                throw new RuntimeException("시스템 오류로 인한 주문 실패");
            } else if (order.getOrderId().startsWith("NETWORK")) {
                throw new IOException("네트워크 오류로 인한 주문 실패");
            }
            return new TradeResult(order.getOrderId(), TradeResultStatus.COMPLETED, "주문이 성공적으로 완료되었습니다");
        } catch (InterruptedException e) {
            // future.cancel(true)로 강제 종료
            log(order.getOrderId() + " 실행 중인 주문 강제 종료...");
            Thread.currentThread().interrupt();
            // future.get()에서 CancellationException이 발생하여 반환값이 의미가 없어짐
//            return new TradeResult(order.getOrderId(), TradeResultStatus.INTERRUPTED, "주문이 강제로 취소되었습니다");
            return null;
        } catch (RuntimeException | IOException e) {
            return new TradeResult(order.getOrderId(), TradeResultStatus.FAILED, "주문이 실패하였습니다", e.getClass().getSimpleName());
        }
    }

}
