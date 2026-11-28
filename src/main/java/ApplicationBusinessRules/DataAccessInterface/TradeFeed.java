package ApplicationBusinessRules.DataAccessInterface;

/* Interface Overview:
A live stream of trades. Implemented in the outer layer (Finnhub WebSocket, or a fake for tests).
unsubscribe and close must be safe to call when not connected or called twice.
 */

public interface TradeFeed {
    void connect(TradeListener listener);
    void subscribe(String symbol);
    void unsubscribe(String symbol);
    void close();
}