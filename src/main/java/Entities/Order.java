package Entities;

import java.math.BigDecimal;

/*
An instruction to trade, if orders aren't fulfilled it'll wait until it is fulfilled
Has orderID, whether it's buy/sell order, type of order (mkt, limit, stop, etc), how long it remains valid, whether it
opens or closes position, qty requested, max purchase or min sell prices, stopprice, trailing stop distance, qty
already executed, average execution price, current order state, created time

 */
public abstract class Order {

    private String company;       // Company Code
    private String buyOrSell;     // "BUY" or "SELL"
    private int quantity;         // how many shares the order wants
    private int filledQuantity;   // how many shares have traded so far
    private BigDecimal price;     // the price the order can execute at

    // The Order class

    public Order(String company, String buyOrSell, int quantity, BigDecimal price) {
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
        this.price = price;
    }

    // Each order has a difference execution
    public abstract boolean canExecuteAt(BigDecimal marketPrice);

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

    public BigDecimal getPrice()
    {
        return price;
    }

    public String toString() {
        return "Order: " + buyOrSell + " " + quantity + " " + company
                + " (" + filledQuantity + " filled)";
    }
}
