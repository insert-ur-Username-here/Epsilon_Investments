package Entities;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public final class Transaction {

    private final UUID transactionId;
    private final LocalDateTime executedAt;
    private final String company;
    private final String buyOrSell;
    private final int filledQuantity;
    private final BigDecimal executionPrice;
    private final BigDecimal fees;
    private final BigDecimal multiplier; // options multiplier


    // Constructors
    // Constructor for ordinary securities
    public Transaction(
            LocalDateTime executedAt,
            String company,
            String buyOrSell,
            int filledQuantity,
            BigDecimal executionPrice,
            BigDecimal fees
    ) {
        this(
                executedAt,
                company,
                buyOrSell,
                filledQuantity,
                executionPrice,
                fees,
                BigDecimal.ONE
        );
    }

    // Constructor allowing a custom contract multiplier
    public Transaction(
            LocalDateTime executedAt,
            String company,
            String buyOrSell,
            int filledQuantity,
            BigDecimal executionPrice,
            BigDecimal fees,
            BigDecimal multiplier
    ) {
        // Exceptions
        Objects.requireNonNull(executedAt, "Execution date cannot be null.");
        Objects.requireNonNull(executionPrice, "Execution price cannot be null.");
        Objects.requireNonNull(fees, "Fees cannot be null.");
        Objects.requireNonNull(multiplier, "Multiplier cannot be null.");
        if (company == null || company.isBlank()) {
            throw new IllegalArgumentException("Company cannot be empty.");
        }
        if (buyOrSell == null) {
            throw new IllegalArgumentException("Transaction direction cannot be null.");
        }
        String direction = buyOrSell.trim().toUpperCase(Locale.ROOT);
        if (!direction.equals("BUY") && !direction.equals("SELL")) {
            throw new IllegalArgumentException("Must be BUY or SELL.");
        }
        if (filledQuantity <= 0) {
            throw new IllegalArgumentException("Filled quantity must be positive.");
        }

        if (executionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Execution price must be positive.");
        }
        if (fees.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fees cannot be negative.");
        }
        if (multiplier.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Multiplier must be positive.");
        }
         // Assignment
        this.transactionId = UUID.randomUUID();
        this.executedAt = executedAt;
        this.company = company;
        this.buyOrSell = direction;
        this.filledQuantity = filledQuantity;
        this.executionPrice = executionPrice;
        this.fees = fees;
        this.multiplier = multiplier;
    }


    // Getters
    public UUID getTransactionId() {
        return this.transactionId;
    }

    public LocalDateTime getExecutedAt() {
        return this.executedAt;
    }

    public String getCompany() {
        return this.company;
    }

    public String getBuyOrSell() {
        return this.buyOrSell;
    }

    public int getFilledQuantity() {
        return this.filledQuantity;
    }

    public BigDecimal getExecutionPrice() {
        return this.executionPrice;
    }

    public BigDecimal getFees() {
        return this.fees;
    }

    public BigDecimal getMultiplier() {
        return this.multiplier;
    }


    // Financial information
    public BigDecimal getValue() {
        /*
        Return the gross value of the executed trade, excluding fees
         */
        return executionPrice
                .multiply(BigDecimal.valueOf(filledQuantity))
                .multiply(multiplier);
    }

    // Actual change in portfolio cash balance.
    // BUY = negative cash flow
    // SELL = positive cash flow
    public BigDecimal getCashFlow() {
        /*
        Return the cash flow of portfolio, where buying indicated negative and selling positive cash flow
         */
        BigDecimal totalValue = getValue();
        if (isBuy()) {
            return totalValue.add(fees).negate();
        }
        return totalValue.subtract(fees);
    }


    // Additional methods
    public boolean isBuy() {
        /*
        Return information to show BUY
         */
        return this.buyOrSell.equals("BUY");
    }
    public boolean isSell() {
        /*
        Return information to show SELL
         */
        return this.buyOrSell.equals("SELL");
    }


    // Object methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) obj;
        return this.transactionId.equals(other.transactionId);
    }

    @Override
    public int hashCode() {
        return this.transactionId.hashCode();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", executedAt=" + executedAt +
                ", company='" + company + '\'' +
                ", buyOrSell='" + buyOrSell + '\'' +
                ", filledQuantity=" + filledQuantity +
                ", executionPrice=" + executionPrice +
                ", value=" + getValue() +
                ", fees=" + fees +
                ", cashFlow=" + getCashFlow() +
                '}';
    }
}
