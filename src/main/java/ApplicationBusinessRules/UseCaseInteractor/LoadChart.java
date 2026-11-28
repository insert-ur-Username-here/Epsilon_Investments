package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.DataAccessInterface.ChartSessionRepository;
import ApplicationBusinessRules.DataAccessInterface.MarketDataGateway;
import ApplicationBusinessRules.InputBoundary.LoadChartInputBoundary;
import ApplicationBusinessRules.InputBoundary.LoadChartInputData;
import ApplicationBusinessRules.OutputBoundary.LoadChartOutputBoundary;
import ApplicationBusinessRules.OutputBoundary.LoadChartOutputData;
import Entities.CandleSeries;
import Entities.Indicator;
import Entities.Quote;
import Entities.TimeFrame;
import java.util.List;

public class LoadChart implements LoadChartInputBoundary {
    private final MarketDataGateway market;
    private final ChartSessionRepository sessions;
    private final LoadChartOutputBoundary presenter;

    public LoadChart(MarketDataGateway market,
                     ChartSessionRepository sessions,
                     LoadChartOutputBoundary presenter) {
        this.market = market;
        this.sessions = sessions;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoadChartInputData input) {
        String raw = input.getSymbol();
        if (raw == null || raw.isBlank()) {
            presenter.prepareFailView("Please enter a symbol.");
            return;
        }
        String symbol = raw.trim().toUpperCase();

        TimeFrame tf = input.getTimeFrame();
        if (tf == null) {
            presenter.prepareFailView("Please choose a timeframe.");
            return;
        }

        int count = input.getCandleCount();
        if (count < 1 || count > ChartDefaults.MAX_CANDLES) {
            presenter.prepareFailView(
                    "Candle count must be between 1 and " + ChartDefaults.MAX_CANDLES + ".");
            return;
        }

        try {
            // Build indicators first
            List<Indicator> indicators = IndicatorFactory.create(input.getIndicators());

            long to = System.currentTimeMillis() / 1000;
            long from = ChartDefaults.fetchFrom(to, count, tf);

            CandleSeries candles = market.getCandles(symbol, tf, from, to).lastN(count);
            if (candles.isEmpty()) {
                presenter.prepareFailView("No price data for " + symbol + ".");
                return;   // the previous chart, if any, is untouched
            }
            Quote quote = market.getQuote(symbol);

            LiveChartSession session =
                    LiveChartSession.start(candles, quote, ChartDefaults.MAX_CANDLES);

            // Save only after everything succeeded
            sessions.save(new ActiveChart(session, count, indicators));

            CandleSeries snapshot = session.snapshot();
            presenter.prepareSuccessView(new LoadChartOutputData(
                    tf, snapshot, session.getQuote(),
                    IndicatorRunner.run(indicators, snapshot)));
        } catch (MarketDataException | IllegalArgumentException e) {
            presenter.prepareFailView(e.getMessage());
        }
    }
}