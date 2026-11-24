package Entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/*
Data for snapshot of portfolio, whether it's daily, monthly, weekly, or yearly performance, should just store for each
day then calculate using datetime as id's in the usecaseinteractor for performance
DAILY snapshot, should be end of day, if first day use previous as 0
 */
// TODO: I feel like I'm forgetting something here, check later

public class PortfolioSnapshot {
    private LocalDate date;
    private BigDecimal snapshotPortfolioValue;
    private final Map<String, Position> positions; // snapshot of positions
    private final List<Order> orders; // snapshot of ongoing orders
    private final List<Transaction> transactions; // snapshot of transaction history

    private PortfolioSnapshot(LocalDate date, BigDecimal endValue, Map<String, Position> positions, List<Order> orders,
                              List<Transaction> transactions) {
        this.date = Objects.requireNonNull(date, "Date cannot be null.");
        this.snapshotPortfolioValue = Objects.requireNonNull(endValue, "Value cannot be null");
        this.positions = Objects.requireNonNull(positions, "Positions cannot be null");
        this.orders = Objects.requireNonNull(orders, "Orders cannot be null");
        this.transactions = Objects.requireNonNull(transactions, "Transactions cannot be null");
    }

    // case for start
    private PortfolioSnapshot() {
        this.date = null;
        this.snapshotPortfolioValue = BigDecimal.valueOf(0);
        this.positions = null;
        this.orders = null;
        this.transactions = null;
    }

    public BigDecimal getEndValue() {return this.snapshotPortfolioValue;}

    public LocalDate getDate() {return this.date;}

    public Map<String, Position> getPositions() {return this.positions;}

    public List<Order> getOrders() {return this.orders;}

    public List<Transaction> getTransactions() {return this.transactions;}

}
