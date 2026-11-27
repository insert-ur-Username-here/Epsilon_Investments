package ApplicationBusinessRules.UseCaseInteractor;

/* Class Overview:
Thrown by a MarketDataGateway when market data can't be retrieved: unknown symbol,
no access on the current plan, rate limit hit, network failure, or a malformed response.
Unchecked, so interactors catch it only where they can present a useful message.
The message is shown to the user, so gateways should word it for people, and keep
technical details in the cause.
 */

public class MarketDataException extends RuntimeException {
    public MarketDataException(String message) {
        super(message);
    }

    public MarketDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
