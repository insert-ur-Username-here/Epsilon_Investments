
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
    private BigDecimal externalCashFlowSinceLastSnapshot = BigDecimal.ZERO;

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
        /*
        Return the ID of this portfolio
        */
        return this.portfolioId;
    }

    public String getPortfolioName() {
        /*
        Return the name of this portfolio
        */
        return this.portfolioName;
    }

    public LocalDateTime getDateCreated() {
        /*
        Return dateCreated of this portfolio
        */
        return this.dateCreated;
    }

    public BigDecimal getCashBalance() {
        /*
        Return current cashBalance of this portfolio
        */
        return this.cashBalance;
    }

    public BigDecimal getPortfolioValue() {
        /*
        Return current value of this portfolio
        */
        return this.portfolioValue;
    }

    public Duration getTimeElapsed() {
        /*
        Return total time portfolio has been active for
        */
        return Duration.between(this.dateCreated, LocalDateTime.now());
    }

    public Map<String, Position> getPositions() {
        /*
        Return all portfolio positions
        */
        return Collections.unmodifiableMap(new HashMap<>(positions)); // Prevent modification of positions
    }

    public List<Order> getOrders() {
        /*
        Return all portfolio orders
        */
        return Collections.unmodifiableList(new ArrayList<>(orders));
    }

    public List<Transaction> getTransactions() {
        /*
        Return all portfolio transactions
        */
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    public NavigableMap<LocalDate, PortfolioSnapshot> getDailySnapshots() {
        /*
        Return daily performance of portfolio for all history
        */
        return Collections.unmodifiableNavigableMap(new TreeMap<>(dailySnapshots)); // Treemap for speed balance
    }

    public int getNumPositions() {
        /*
        Return number of positions of this portfolio
        */
        return this.positions.size();
    }

    public int getNumOrders() {
        /*
        Return number of orders of this portfolio
        */
        return this.orders.size();
    }

    public int getNumTransactions() {
        /*
        Return number of transactions of this portfolio
        */
        return this.transactions.size();
    }

    public BigDecimal getExternalCashFlowSinceLastSnapshot() {
        /*
        Return external cash flow since yesterday/last snapshot
         */
        return this.externalCashFlowSinceLastSnapshot;
    }

    // Portfolio management
    public void setPortfolioName(String portfolioName) {
        /*
        Set portfolio name to portfolioName
        */
        if (portfolioName == null || portfolioName.isBlank()) {
            throw new IllegalArgumentException("Portfolio name cannot be empty.");
        }
        this.portfolioName = portfolioName;
    }

    public void updatePortfolioValue(BigDecimal newValue) {
        /*
        Update value of portfolio to newValue
        */
        Objects.requireNonNull(newValue, "Portfolio value cannot be null.");
        this.portfolioValue = newValue;
    }

    public void applyCashFlow(BigDecimal cashFlow) {
        /*
        Add value of cashBalance by cashFlow
        NOTE: This is used for stuff like if you sell a stock then you use this and updatePortfolioValue
        */
        Objects.requireNonNull(cashFlow, "Cash flow cannot be null.");
        this.cashBalance = this.cashBalance.add(cashFlow);
    }

    public void depositCash(BigDecimal amount) {
        /*
        Add cash to portfolio by amount
         */
        validatePositiveAmount(amount);
        this.cashBalance = this.cashBalance.add(amount);
        this.portfolioValue = this.portfolioValue.add(amount);
        this.externalCashFlowSinceLastSnapshot =
                this.externalCashFlowSinceLastSnapshot.add(amount);
    }

    public void withdrawCash(BigDecimal amount) {
        /*
        Withdraw cash from portfolio by amount
        */
        validatePositiveAmount(amount);
        if (this.cashBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient cash balance."
            );
        }
        this.cashBalance = this.cashBalance.subtract(amount);
        this.portfolioValue = this.portfolioValue.subtract(amount);
        this.externalCashFlowSinceLastSnapshot =
                this.externalCashFlowSinceLastSnapshot.subtract(amount);
    }

    // TODO: Add orders and positions to transaction history the moment they are created and stuff

    public void addPosition(String instrumentId, Position position) {
        /*
        Add a new position to portfolio
        */
        validateInstrumentId(instrumentId);
        Objects.requireNonNull(position, "Position cannot be null.");
        if (positions.containsKey(instrumentId)) {
            throw new IllegalArgumentException("A position for this instrument already exists.");
        }
        positions.put(instrumentId, position);
    }

    public void updatePosition(String instrumentId, Position position) {
        /*
        Updates a position in portfolio
        */
        validateInstrumentId(instrumentId);
        Objects.requireNonNull(position, "Position cannot be null.");
        if (!this.positions.containsKey(instrumentId)) {
            throw new IllegalArgumentException("Position does not exist.");
        }
        this.positions.put(instrumentId, position);
    }

    public Optional<Position> getPosition(String instrumentId) {
        /*
        Return positions within portfolio
        */
        validateInstrumentId(instrumentId);
        return Optional.ofNullable(this.positions.get(instrumentId)); // position may not exist
    }

    public Optional<Position> removePosition(String instrumentId) {
        /*
        Remove position in portfolio
        */
        validateInstrumentId(instrumentId);
        return Optional.ofNullable(this.positions.remove(instrumentId)); // position may not exist
    }


    // Order management

    public void addOrder(Order order) {
        /*
        Add order to portfolio
        */
        Objects.requireNonNull(order, "Order cannot be null.");
        orders.add(order);
    }

    public String orderHistory() {
        /*
        Return complete order history
        */
        return this.orders.toString(); 
    }


    // Transaction management

    public void addTransaction(Transaction transaction) {
        /*
        Add transaction to portfolio's transaction history
        */
        Objects.requireNonNull(transaction, "Transaction cannot be null.");
        this.transactions.add(transaction);
    }

    // Historical performance
    public void recordSnapshot(
            LocalDate date,
            PortfolioSnapshot snapshot
    ) {
        /*
        Record snapshot of portfolio
         */
        Objects.requireNonNull(date);
        Objects.requireNonNull(snapshot);
        dailySnapshots.put(date, snapshot);
    }

    public Optional<PortfolioSnapshot> getSnapshot(LocalDate date) {
        /*
        Return a snapshot of portfolio
        */
        Objects.requireNonNull(date, "Date cannot be null.");
        return Optional.ofNullable(dailySnapshots.get(date));
    }

    public Optional<PortfolioSnapshot> getLatestSnapshot() {
        /*
        Return lastest snapshot of portfolio
        */
        if (dailySnapshots.isEmpty()) {return Optional.empty();}
        return Optional.of(dailySnapshots.lastEntry().getValue());
    }

    public NavigableMap<LocalDate, PortfolioSnapshot> getSnapshotsBetween(
            LocalDate startDate, LocalDate endDate) {
        /*
        Return snapshots between startDate and endDate
        */
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
        /*
        Throw exception if amount is not positive or zero
        */
        Objects.requireNonNull(amount, "Amount cannot be null.");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    private void validateInstrumentId(String instrumentId) {
        /*
        Throw exception if instrumentID is null or blank
        */
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
