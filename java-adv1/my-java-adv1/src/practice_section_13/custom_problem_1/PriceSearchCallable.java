package practice_section_13.custom_problem_1;

import java.util.concurrent.Callable;

import static utill.MyLogger.log;

public class PriceSearchCallable implements Callable<StockPrice> {

    private final StockPriceService stockPriceService;
    private final Stock stock;

    public PriceSearchCallable(StockPriceService stockPriceService, Stock stock) {
        this.stockPriceService = stockPriceService;
        this.stock = stock;
    }

    @Override
    public StockPrice call() throws Exception {
        log(stock.getName() + " 조회 중...");
        return stockPriceService.getStockPrice(stock);
    }

}
