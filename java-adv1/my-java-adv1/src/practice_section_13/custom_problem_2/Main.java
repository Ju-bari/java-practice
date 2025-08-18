package practice_section_13.custom_problem_2;

import java.util.ArrayList;
import java.util.List;

import static utill.MyLogger.log;

public class Main {

    private final static TradingSystem tradingSystem = new TradingSystem(3);

    public static void main(String[] args) throws InterruptedException {

        log("=== 증권회사 대량 주문 처리 시스템 ===");
//        test1();
//        test2();
//        test3();
        test4();
        log("=== 증권회사 대량 주문 처리 시스템 종료 ===");
    }

    public static void test1() {
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));

        List<TradeResult> results = tradingSystem.waitForAllOrders();

        // 결과 출력
        for (TradeResult result : results) {
            log(result.toString());
        }

        tradingSystem.shutdown();
    }

    public static void test2() throws InterruptedException {
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));

        // 취소 추가
        Thread.sleep(2000);
        tradingSystem.cancelOrder("SELL002", false);
        tradingSystem.cancelOrder("BUY005", false);

        List<TradeResult> results = tradingSystem.waitForAllOrders();

        // 결과 출력
        for (TradeResult result : results) {
            log(result.toString());
        }

        tradingSystem.shutdown();
    }

    public static void test3() throws InterruptedException {
        tradingSystem.submitOrder(new TradeOrder("BUY001", "AAPL", 100, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));

        // 취소 추가
        Thread.sleep(4000);
        tradingSystem.cancelOrder("SELL002", true);
        tradingSystem.cancelOrder("BUY005", true);

        List<TradeResult> results = tradingSystem.waitForAllOrders();

        // 결과 출력
        for (TradeResult result : results) {
            log(result.toString());
        }

        tradingSystem.shutdown();
    }

    public static void test4() throws InterruptedException {
        tradingSystem.submitOrder(new TradeOrder("SELL002", "GOOG", 50, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY003", "MSFT", 200, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("ERROR_SELL004", "TSLA", 10, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("BUY005", "AMZN", 30, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("NETWORK_BUY006", "AAPL", 150, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL007", "NVDA", 25, TradeOrderType.SELL));
        tradingSystem.submitOrder(new TradeOrder("ERROR_BUY008", "META", 75, TradeOrderType.BUY));
        tradingSystem.submitOrder(new TradeOrder("SELL009", "NFLX", 40, TradeOrderType.SELL));


        // 취소 추가
        Thread.sleep(4000);
        tradingSystem.cancelOrder("SELL002", true);
        tradingSystem.cancelOrder("BUY005", true);

        List<TradeResult> results = tradingSystem.waitForAllOrders();

        // 결과 출력
        for (TradeResult result : results) {
            log(result.toString());
        }

        tradingSystem.shutdown();
    }

}
