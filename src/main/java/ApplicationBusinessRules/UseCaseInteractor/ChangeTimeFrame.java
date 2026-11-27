package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.DataAccessInterface.ChartSessionRepository;
import ApplicationBusinessRules.DataAccessInterface.MarketDataGateway;
import ApplicationBusinessRules.InputBoundary.ChangeTimeFrameInputBoundary;
import ApplicationBusinessRules.InputBoundary.ChangeTimeFrameInputData;
import ApplicationBusinessRules.OutputBoundary.ChangeTimeFrameOutputBoundary;
import ApplicationBusinessRules.OutputBoundary.ChangeTimeFrameOutputData;
import Entities.CandleSeries;
import Entities.Quote;
import Entities.TimeFrame;


public class ChangeTimeFrame implements ChangeTimeFrameInputBoundary {
    private final MarketDataGateway market;
    private final ChartSessionRepository sessions;
    private final ChangeTimeFrameOutputBoundary presenter;

    public ChangeTimeFrame(MarketDataGateway market,
                           ChartSessionRepository sessions,
                           ChangeTimeFrameOutputBoundary presenter) {
        this.market = market;
        this.sessions = sessions;
        this.presenter = presenter;
    }

    @Override
    public void execute(ChangeTimeFrameInputData input) {
        ActiveChart chart = sessions.current();
        if (chart == null) {
            presenter.prepareFailView("No chart loaded.");
            return;
        }
        TimeFrame tf = input.getTimeFrame();
        if (tf == null) {
            presenter.prepareFailView("Please choose a timeframe.");
            return;
        }
        if (tf == chart.getTimeFrame()) {
            CandleSeries snapshot = chart.getSession().snapshot();
            presenter.prepareSuccessView(new ChangeTimeFrameOutputData(
                    tf, snapshot, chart.getSession().getQuote(),
                    IndicatorRunner.run(chart.getIndicators(), snapshot)));
            return;
        }
        try {
            Quote quote = chart.getSession().getQuote();   // kept fresh by the stream; saves an API call
            String symbol = quote.getSymbol();

            long to = System.currentTimeMillis() / 1000;
            long from = to - 3L * chart.getCandleCount() * tf.getSeconds();  // generous: markets close

            CandleSeries candles = market.getCandles(symbol, tf, from, to)
                    .lastN(chart.getCandleCount());
            if (candles.isEmpty()) {
                presenter.prepareFailView("No " + tf + " data for " + symbol + ".");
                return;   // the old chart is untouched
            }

            LiveChartSession session = LiveChartSession.start(candles, quote, 1000);
            ActiveChart updated = chart.withSession(session);   // keeps indicators; timeframe comes from the session
            sessions.save(updated);                             // swap in

            CandleSeries snapshot = session.snapshot();
            presenter.prepareSuccessView(new ChangeTimeFrameOutputData(
                    tf, snapshot, session.getQuote(),
                    IndicatorRunner.run(updated.getIndicators(), snapshot)));
        } catch (MarketDataException | IllegalArgumentException e) {
            presenter.prepareFailView(e.getMessage());   // the old chart is untouched
        }
    }

}