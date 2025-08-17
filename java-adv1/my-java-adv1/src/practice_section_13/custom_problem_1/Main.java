package practice_section_13.custom_problem_1;

import java.util.ArrayList;
import java.util.List;

import static utill.MyLogger.log;

public class Main {

    private static final StockTradingSystem stockTradingSystem = new StockTradingSystem(5);

    public static void main(String[] args) {

        log("=== 증권회사 주식 가격 조회 시스템 ===");

        List<Stock> stocks = List.of(
                new Stock("005930", "삼성전자"),
                new Stock("000660", "SK하이닉스"),
                new Stock("035420", "NAVER"),
                new Stock("035720", "카카오"),
                new Stock("005380", "현대차"),
                new Stock("066570", "LG전자"),
                new Stock("005490", "POSCO홀딩스")
        );

        long startTime = System.currentTimeMillis();

        List<StockPrice> results = stockTradingSystem.getAllStockPrices(stocks);

        log("=== 증권회사 주식 가격 조회 시스템 결과 ===");
        for (StockPrice stockPrice : results) {
            log(stockPrice.toString());
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        log("[총 소요 시간: " + duration + "초]");

        stockTradingSystem.shutdown();
        log("=== 증권회사 주식 가격 조회 시스템 종료 ===");
    }

}
