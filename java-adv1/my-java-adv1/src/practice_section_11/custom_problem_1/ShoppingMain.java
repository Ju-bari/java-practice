package practice_section_11.custom_problem_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static utill.MyLogger.log;

public class ShoppingMain {
    public static void main(String[] args) throws InterruptedException {

        log("=== 온라인 쇼핑몰 재고 관리 시스템 ===");
        log("초기 재고: " + 30 + "개");

        Product product = new Product(1, "자전거", new AtomicInteger(30));

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new CustomerThread(product), "customer-" + (i+1));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        log("=== 주문 처리 완료 ===");
        log("최종 재고: " + product.getStock() + "개");
    }

}
