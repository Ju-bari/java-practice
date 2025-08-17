package practice_section_13.custom_problem_1;

import java.time.LocalDateTime;
import java.util.Random;

public class StockPriceService{

    private final Random random = new Random();

    public StockPrice getStockPrice(Stock stock)  {
        try {
            Thread.sleep(random.nextInt(2001) + 1000);
        } catch (InterruptedException e) {
            System.out.println("StockPriceService getStockPrice interrupted");
        }
        double price = 1000 + random.nextDouble() * 49000; // 랜덤 시간
        return new StockPrice(stock.getSymbol(), price, LocalDateTime.now());
    }

}
