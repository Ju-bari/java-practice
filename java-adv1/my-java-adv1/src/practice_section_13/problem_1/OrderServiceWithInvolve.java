package practice_section_13.problem_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static utill.MyLogger.log;

public class OrderServiceWithInvolve {

    private final ExecutorService es = Executors.newFixedThreadPool(3);

    public void order(String orderNo) throws ExecutionException, InterruptedException {

        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);
        List<Callable<Boolean>> callables = List.of(inventoryWork, shippingWork, accountingWork);

        try {
            List<Future<Boolean>> futures = es.invokeAll(callables); // submit
            boolean successFlag = true;

            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    log("일부 작업이 실패했습니다.");
                    successFlag = false;
                    break;
                }
            }

            if (successFlag) {
                log("모든 주문 처리가 성공적으로 완료되었습니다.");
            }
        } finally {
            es.close();
        }
    }

}
