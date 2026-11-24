package Entities;

public abstract class Order {

    private String company;       // Company Code
    private String buyOrSell;     // "BUY" or "SELL"
    private int quantity;         // how many shares the order wants
    private int filledQuantity;   // how many shares have traded so far

    public Order(String company, String buyOrSell, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!buyOrSell.equals("BUY") && !buyOrSell.equals("SELL")) {
            throw new IllegalArgumentException("Must be BUY or SELL");
        }

        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.filledQuantity = 0;
    }

    // Each order has a difference execution
    public abstract boolean canExecuteAt(double marketPrice);

    // Record which shares have been traded
    public void fill(int amount) {
        if (amount <= 0 || amount > getRemainingQuantity()) {
            throw new IllegalArgumentException("Invalid fill amount: " + amount);
        }
        filledQuantity = filledQuantity + amount;
    }

    // Checks if the order has been filled
    public boolean isFilled() {
        return filledQuantity == quantity;
    }

    // Check if the order type is to buy
    public boolean isBuy() {
        return buyOrSell.equals("BUY");
    }

    // Gets the remaining quantity of orders
    public int getRemainingQuantity() {
        return quantity - filledQuantity;
    }

    // Getters
    public String getCompany()
    {
        return company;
    }

    public String getBuyOrSell()
    {
        return buyOrSell;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getFilledQuantity()
    {
        return filledQuantity;
    }

    public String toString() {
        return "Order: " + buyOrSell + " " + quantity + " " + company
                + " (" + filledQuantity + " filled)";
    }
}
