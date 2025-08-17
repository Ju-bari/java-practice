package practice_section_13.custom_problem_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static utill.MyLogger.log;

public class StockTradingSystem {

    private final ExecutorService executorService;
    private final StockPriceService stockPriceService;

    public StockTradingSystem(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.stockPriceService = new StockPriceService();
    }

    public List<StockPrice> getAllStockPrices(List<Stock> stocks) {

        // 1. task(callable) 만들기
        List<Callable<StockPrice>> callables = new ArrayList<>(); // tasks
        for (Stock stock : stocks) {
//            Callable<StockPrice> task = () -> stockPriceService.getStockPrice(stock);
            Callable<StockPrice> task = new PriceSearchCallable(stockPriceService, stock);
            callables.add(task);
        }

        List<StockPrice> results = new ArrayList<>();

        try {
            // 2. callable 실행하여 future로 변환하기
            List<Future<StockPrice>> futures = executorService.invokeAll(callables);

            // 3. future로 결과 받기
            for (Future<StockPrice> future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("StockPriceService getStockPrice interrupted");
        }

        return results;
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
