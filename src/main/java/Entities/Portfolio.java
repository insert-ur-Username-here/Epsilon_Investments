
package Entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Portfolio {

    private final UUID portfolioId;
    private String portfolioName;
    private final LocalDateTime dateCreated;
    private BigDecimal cashBalance;
    private BigDecimal portfolioValue; // latest known value (so updated upon view not constant)
    private final Map<String, Position> positions; // positions in portfolio
    private final List<Order> orders; // orders in portfolio
    private final List<Transaction> transactions; // Transaction history
    private final NavigableMap<LocalDate, PortfolioSnapshot> dailySnapshots; //history

    // Constructors
    public Portfolio() {
        // This is for empty portfolio initialization
        this("New Portfolio", BigDecimal.ZERO);
    }
    public Portfolio(String portfolioName) {
        this(portfolioName, BigDecimal.ZERO);
    }
    public Portfolio(BigDecimal initialCash) {
        this("New Portfolio", initialCash);
    }
    public Portfolio(String portfolioName, BigDecimal initialCash) {
        // Setting both name and initial cash
        if (portfolioName == null || portfolioName.isBlank()) {
            throw new IllegalArgumentException("Portfolio name cannot be empty.");
        }
        Objects.requireNonNull(initialCash, "Initial cash cannot be null.");
        if (initialCash.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial cash cannot be negative.");
        }

        this.portfolioId = UUID.randomUUID();
        this.portfolioName = portfolioName;
        this.dateCreated = LocalDateTime.now();
        this.cashBalance = initialCash;
        this.portfolioValue = initialCash;
        this.positions = new HashMap<>();
        this.orders = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.dailySnapshots = new TreeMap<>();
    }

    // Getters

    public UUID getPortfolioId() {
        return this.portfolioId;
    }

    public String getPortfolioName() {
        return this.portfolioName;
    }

    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    public BigDecimal getCashBalance() {
        return this.cashBalance;
    }

    public BigDecimal getPortfolioValue() {
        return this.portfolioValue;
    }

    public Duration getTimeElapsed() {
        return Duration.between(this.dateCreated, LocalDateTime.now());
    }

    public Map<String, Position> getPositions() {
        return Collections.unmodifiableMap(new HashMap<>(positions)); // Prevent modification of positions
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(new ArrayList<>(orders));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    public NavigableMap<LocalDate, PortfolioSnapshot> getDailySnapshots() {
        return Collections.unmodifiableNavigableMap(new TreeMap<>(dailySnapshots)); // Treemap for speed balance
    }

    public int getNumPositions() {
        return this.positions.size();
    }

    public int getNumOrders() {
        return this.orders.size();
    }

    public int getNumTransactions() {
        return this.transactions.size();
    }


    // Portfolio management

    public void setPortfolioName(String portfolioName) {
        if (portfolioName == null || portfolioName.isBlank()) {
            throw new IllegalArgumentException("Portfolio name cannot be empty.");
        }
        this.portfolioName = portfolioName;
    }

    public void updatePortfolioValue(BigDecimal newValue) {
        Objects.requireNonNull(newValue, "Portfolio value cannot be null.");
        this.portfolioValue = newValue;
    }

    public void applyCashFlow(BigDecimal cashFlow) {
        Objects.requireNonNull(cashFlow, "Cash flow cannot be null.");
        this.cashBalance = this.cashBalance.add(cashFlow);
    }

    public void depositCash(BigDecimal amount) {
        validatePositiveAmount(amount);
        this.cashBalance = this.cashBalance.add(amount);
        this.portfolioValue = this.portfolioValue.add(amount);
    }

    public void withdrawCash(BigDecimal amount) {
        validatePositiveAmount(amount);
        if (this.cashBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient cash balance.");
        }
        this.cashBalance = this.cashBalance.subtract(amount);
        this.portfolioValue = this.portfolioValue.subtract(amount);
    }

    public void addPosition(String instrumentId, Position position) {
        validateInstrumentId(instrumentId);
        Objects.requireNonNull(position, "Position cannot be null.");
        if (positions.containsKey(instrumentId)) {
            throw new IllegalArgumentException("A position for this instrument already exists.");
        }
        positions.put(instrumentId, position);
    }

    public void updatePosition(String instrumentId, Position position) {
        validateInstrumentId(instrumentId);
        Objects.requireNonNull(position, "Position cannot be null.");
        if (!this.positions.containsKey(instrumentId)) {
            throw new IllegalArgumentException("Position does not exist.");
        }
        this.positions.put(instrumentId, position);
    }

    public Optional<Position> getPosition(String instrumentId) {
        validateInstrumentId(instrumentId);
        return Optional.ofNullable(this.positions.get(instrumentId)); // position may not exist
    }

    public Optional<Position> removePosition(String instrumentId) {
        validateInstrumentId(instrumentId);
        return Optional.ofNullable(this.positions.remove(instrumentId)); // position may not exist
    }


    // Order management

    public void addOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null.");
        orders.add(order);
    }

    // Returns the complete order history.
    // Cancelled and filled orders should remain in this collection.
    public String orderHistory() {
        return this.orders.toString(); // TODO: give string representation of orders or change this implementation and make sure null has a representation too
    }


    // Transaction management

    public void addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null.");
        this.transactions.add(transaction);
    }


    // Historical performance

    public void recordSnapshot(LocalDate date, PortfolioSnapshot snapshot) {
        Objects.requireNonNull(date, "Snapshot date cannot be null.");
        Objects.requireNonNull(snapshot, "Snapshot cannot be null.");
        dailySnapshots.put(date, snapshot);
    }

    public Optional<PortfolioSnapshot> getSnapshot(LocalDate date) {
        Objects.requireNonNull(date, "Date cannot be null.");
        return Optional.ofNullable(dailySnapshots.get(date));
    }

    public Optional<PortfolioSnapshot> getLatestSnapshot() {
        if (dailySnapshots.isEmpty()) {return Optional.empty();}
        return Optional.of(dailySnapshots.lastEntry().getValue());
    }

    public NavigableMap<LocalDate, PortfolioSnapshot> getSnapshotsBetween(
            LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null.");
        Objects.requireNonNull(endDate, "End date cannot be null.");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        return Collections.unmodifiableNavigableMap(
                new TreeMap<>(dailySnapshots.subMap(startDate, true, endDate, true)));
    }


    // Helpers
    private void validatePositiveAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    private void validateInstrumentId(String instrumentId) {
        if (instrumentId == null || instrumentId.isBlank()) {
            throw new IllegalArgumentException("Instrument ID cannot be empty.");
        }
    }


    // Object methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (!(obj instanceof Portfolio)) {return false;}

        Portfolio other = (Portfolio) obj;
        return portfolioId.equals(other.portfolioId);
    }

    @Override
    public int hashCode() {return portfolioId.hashCode();}

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", portfolioName='" + portfolioName + '\'' +
                ", dateCreated=" + dateCreated +
                ", cashBalance=" + cashBalance +
                ", portfolioValue=" + portfolioValue +
                ", numPositions=" + positions.size() +
                ", numOrders=" + orders.size() +
                ", numTransactions=" + transactions.size() +
                '}';
    }
}
