package Entities;
import java.util.*;

public class Portfolio {
    private float revenue;
    private float loss;
    private float num_transactions;
    private float current_date; // Time elapsed can be difference between these two
    private final float date_created; // TODO: import time and change this to time
    private float value;
    private HashMap<String, Float> securities;

    // Constructors
    // TODO: Overload this with a no value constructor as well
    private Portfolio(String stock, float value) {
        this.revenue = 0;
        this.loss = 0;
        this.num_transactions = 0;
        this.current_date = 0; // TODO: Change this
        this.date_created = 0; // TODO: Change this
        this.value = value;
        this.securities = new HashMap<>(Map.of(stock, value));

    }

    public float getTimeElapsed() {return this.current_date - this.date_created;} // TODO: Change this

    public float getRevenue() {return this.revenue;}

    public float getLoss() {return this.loss;}

    public float getProfit() {return this.revenue - this.loss;}

    public float getNumTransactions () {return this.num_transactions;}

    public float getPortfolioValue() {return this.value;}

    public void addMoneyAndStock(String stock, float added_value) {
        this.securities.put(stock, added_value);
        this.num_transactions += 1;
        this.value += added_value;
    }

    public AbstractMap<String, Float> getExposures() {
        HashMap<String, Float> list_exposures = new HashMap<>();
        this.securities.forEach((stock, value) -> {
            list_exposures.put(stock, value/this.value);
        });
        return list_exposures;
    }

    public float getSharpeRatio(float time) { // TODO: time stuff lol
        // TODO: Get risk-free rate from database/API
        // TODO: Get value and profit over certain amount of time
        float risk_free_rate = 0.03F;
        return ((this.getProfit()/this.value) - risk_free_rate)/time;
    }
}
