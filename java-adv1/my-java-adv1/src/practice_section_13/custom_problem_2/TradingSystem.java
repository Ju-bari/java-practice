package practice_section_13.custom_problem_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static utill.MyLogger.log;

public class TradingSystem {

    private final ExecutorService executorService;
    private final Map<String, Future<TradeResult>> runningOrders;

    public TradingSystem(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
        this.runningOrders = new ConcurrentHashMap<>(); // 동시성 주의
    }

    public void submitOrder(TradeOrder order) {
        Future<TradeResult> resultFuture = executorService.submit(new OrderProcessor(order));
        runningOrders.put(order.getOrderId(), resultFuture);
    }

    public void cancelOrder(String orderId, boolean forceCancel) {
        if (!runningOrders.containsKey(orderId)) {
            log(orderId + "에 해당하는 주문이 없어서 취소에 실패하였습니다");
            return;
        }

        log(orderId + " 취소 중...");
        if (forceCancel) {
            Future<TradeResult> resultFuture = runningOrders.get(orderId);
            boolean cancelled = resultFuture.cancel(true);
//            System.out.println(orderId + " 주문 취소 시도 결과: " + cancelled);
//            System.out.println("isCancelled: " + resultFuture.isCancelled() + ", isDone: " + resultFuture.isDone());
        } else {
            Future<TradeResult> resultFuture = runningOrders.get(orderId);
            boolean cancelled = resultFuture.cancel(false);
//            System.out.println(orderId + " 주문 취소 시도 결과: " + cancelled);
//            System.out.println("isCancelled: " + resultFuture.isCancelled() + ", isDone: " + resultFuture.isDone());
        }
    }

    public List<TradeResult> waitForAllOrders() {
        List<TradeResult> results = new ArrayList<>();

        for (Map.Entry<String, Future<TradeResult>> entry : runningOrders.entrySet()) {
            String orderId = entry.getKey();
            Future<TradeResult> resultFuture = entry.getValue();

            try {
                TradeResult tradeResult = resultFuture.get();
                if (tradeResult != null) {
                    results.add(tradeResult);
                } else {
                    results.add(new TradeResult(orderId, TradeResultStatus.FAILED, "작업 결과가 null입니다"));
                }
            } catch (CancellationException e) {
                results.add(new TradeResult(orderId, TradeResultStatus.CANCELLED, "주문이 취소되었습니다", e.getClass().getSimpleName()));
            } catch (ExecutionException e) { // RuntimeException, IOException -> Callable의 예외는 아님. 이미 Catch함.
                Throwable originalException = e.getCause(); // 원인 예외 추출
                results.add(new TradeResult(orderId, TradeResultStatus.FAILED,
                        "알 수 없는 오류입니다: " + originalException.getMessage(), originalException.getClass().getSimpleName()));
            }  catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                results.add(new TradeResult(orderId, TradeResultStatus.FAILED, "알 수 없는 오류입니다.", e.getClass().getSimpleName()));
                break; // main 스레드에서 Interrupted 한 부분이라서 for문 중단
            }
        }
        return results;
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
