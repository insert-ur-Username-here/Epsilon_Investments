package Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class PortfolioSnapshot {

    private final LocalDateTime capturedAt;
    private final BigDecimal cashBalance;
    private final BigDecimal positionsMarketValue;
    private final BigDecimal portfolioValue;
    private final BigDecimal netExternalCashFlow;

    // Constructor
    public PortfolioSnapshot(
            LocalDateTime capturedAt,
            BigDecimal cashBalance,
            BigDecimal positionsMarketValue,
            BigDecimal netExternalCashFlow
    ) {
        Objects.requireNonNull(capturedAt, "Capture time cannot be null.");
        Objects.requireNonNull(cashBalance, "Cash balance cannot be null.");
        Objects.requireNonNull(positionsMarketValue,
                "Positions market value cannot be null.");
        Objects.requireNonNull(netExternalCashFlow,
                "External cash flow cannot be null.");
        this.capturedAt = capturedAt;
        this.cashBalance = cashBalance;
        this.positionsMarketValue = positionsMarketValue;
        this.netExternalCashFlow = netExternalCashFlow;
        this.portfolioValue = cashBalance.add(positionsMarketValue);
    }

    // Getters
    public LocalDateTime getCapturedAt() {
        return this.capturedAt;
    }
    public BigDecimal getCashBalance() {
        return this.cashBalance;
    }
    public BigDecimal getPositionsMarketValue() {
        return this.positionsMarketValue;
    }
    public BigDecimal getPortfolioValue() {
        return this.portfolioValue;
    }
    public BigDecimal getNetExternalCashFlow() {
        return this.netExternalCashFlow;
    }
    public BigDecimal getTotalExternalCashFlow() {
        // Same-period external net contribution.
        return this.netExternalCashFlow;
    }

    // Convenience methods
    public BigDecimal getDailyProfitLoss(
            PortfolioSnapshot previousSnapshot
    ) {
        Objects.requireNonNull(previousSnapshot, "Previous snapshot cannot be null.");
        if (!this.capturedAt.isAfter(previousSnapshot.capturedAt)) {
            throw new IllegalArgumentException("Current snapshot must be later than previous snapshot.");
        }
        return this.portfolioValue
                .subtract(previousSnapshot.portfolioValue)
                .subtract(this.netExternalCashFlow);
    }


    // Object methods
    @Override
    public String toString() {
        return "PortfolioSnapshot{" +
                "capturedAt=" + capturedAt +
                ", cashBalance=" + cashBalance +
                ", positionsMarketValue=" + positionsMarketValue +
                ", portfolioValue=" + portfolioValue +
                ", netExternalCashFlow=" + netExternalCashFlow +
                '}';
    }
}
