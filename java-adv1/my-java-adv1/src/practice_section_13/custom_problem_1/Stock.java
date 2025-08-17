package practice_section_13.custom_problem_1;

public class Stock {

    private final String symbol;
    private final String name;

    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

}
