package practice_section_13.problem_1;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;
import static utill.MyLogger.log;

public class InventoryWork implements Callable<Boolean> {

    private final String orderNo;

    public InventoryWork(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public Boolean call() throws Exception {
        log("재고 업데이트: " + orderNo);
        sleep(1000);
        return true;
    }

}
