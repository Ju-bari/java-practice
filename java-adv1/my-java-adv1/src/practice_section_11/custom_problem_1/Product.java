package practice_section_11.custom_problem_1;

import java.util.concurrent.atomic.AtomicInteger;

import static utill.MyLogger.log;

public class Product {

    private final int id;
    private final String name;
    private AtomicInteger stock;

    public Product(int id, String name, AtomicInteger stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public boolean attemptPurchase(int quantity) {
        while (true) {
            int currentStock = stock.get(); // 스냅샷

            // 재고 부족 체크
            if (currentStock < quantity) {
                log(quantity + "개 주문 시도 -> 실패! 재고 부족 (현재 재고:" + currentStock + ")");
                return false;
            }

            // 스냅샷을 바탕으로 시도
            int newStock = currentStock - quantity;

            if (stock.compareAndSet(currentStock, newStock)) {
                log(quantity + "개 주문 시도 -> 성공! 남은 재고: " + newStock);
                return true;
            }
        }
    }

    public boolean attemptPurchaseV2(int quantity) {
        int newStock = stock.addAndGet(-quantity); // 원자적으로 미리 계산

        if (newStock >= 0) {
            log(quantity + "개 주문 시도 -> 성공! 남은 재고: " + newStock);
            return true;
        } else {
            int restoredStock = stock.addAndGet(quantity); // 원상복구 원자적으로 계산
            log(quantity + "개 주문 시도 -> 실패! 재고 부족 (현재 재고:" + restoredStock + ")");
            return false;
        }
    }

    public AtomicInteger getStock() {
        return stock;
    }

}
