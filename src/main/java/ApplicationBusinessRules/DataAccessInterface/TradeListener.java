package ApplicationBusinessRules.DataAccessInterface;

import Entities.Trade;

public interface TradeListener {
    void onTrade(Trade trade);       // called on the stream thread
    void onError(Throwable error);   // called on the stream thread
}