package ApplicationBusinessRules.DataAccessInterface;

import ApplicationBusinessRules.UseCaseInteractor.MarketDataException;
import Entities.CandleSeries;
import Entities.Quote;
import Entities.TimeFrame;

/* Interface Overview:
How the use cases get market data. Implemented in the data access layer
(Finnhub, or a fake for tests).

Contract for implementations:
 - Symbols are case-insensitive; returned entities carry the normalized symbol.
 - Timestamps are unix seconds.
 - getCandles returns candles in ascending time order. "No data in that range" is an
   empty series, not an error. Never returns null.
 - Throws MarketDataException for an unknown symbol, missing access, rate limiting,
   network failure, or a response that can't be parsed.
 - Translating TimeFrame into the provider's resolution codes happens here, not in entities.
 */

public interface MarketDataGateway {

    CandleSeries getCandles(String symbol, TimeFrame timeframe, long from, long to)
            throws MarketDataException;

    Quote getQuote(String symbol) throws MarketDataException;
}
