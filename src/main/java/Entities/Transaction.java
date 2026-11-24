package Entities;

/*
A recording of a successful trade, immutable, final.
Same as order obj but also includes getters for value and cash flow, for INDIVIDUAL transactions (each obj represents
one transaction)
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Transaction {
    private final LocalDate dateCreated;
    private final BigDecimal value;
    private final String transactionType;
    private final BigDecimal cashFlow;
    private final String company;
    private final String buyOrSell;
    private final int quantity;
    private final int filledQuantity;

    public Transaction(LocalDate dateCreated, BigDecimal value, String transactionType, BigDecimal cashFlow,
                       String company, String buyOrSell, int quantity, int filledQuantity) {
        this.dateCreated = dateCreated;
        this.value = value;
        this.transactionType = transactionType;
        this.cashFlow = cashFlow;
        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
    }

    // if not buy or sell then record it as neither
    public Transaction(LocalDate dateCreated, BigDecimal value, String transactionType, BigDecimal cashFlow,
                       String company, int quantity, int filledQuantity) {
        this.dateCreated = dateCreated;
        this.value = value;
        this.transactionType = transactionType;
        this.cashFlow = cashFlow;
        this.company = company;
        this.buyOrSell = "neither";
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
    }

}
