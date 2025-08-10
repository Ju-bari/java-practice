package practice_section_11.custom_problem_1;

public class CustomerThread implements Runnable {

    private final Product product;

    public CustomerThread(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        int purchase = 4;
        product.attemptPurchase(purchase);
    }

}
