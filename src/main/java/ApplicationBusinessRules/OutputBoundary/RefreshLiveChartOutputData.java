package ApplicationBusinessRules.OutputBoundary;

import Entities.CandleSeries;
import Entities.IndicatorSeries;
import Entities.Quote;

import java.util.List;

public class RefreshLiveChartOutputData {
    private final CandleSeries candles;
    private final Quote quote;
    private final List<IndicatorSeries> indicators;

    public RefreshLiveChartOutputData(CandleSeries candles, Quote quote,
                                      List<IndicatorSeries> indicators) {
        if (candles == null || quote == null || indicators == null) {
            throw new IllegalArgumentException("All output data fields are required");
        }
        this.candles = candles;
        this.quote = quote;
        this.indicators = List.copyOf(indicators);
    }

    public CandleSeries getCandles() { return candles; }
    public Quote getQuote() { return quote; }
    public List<IndicatorSeries> getIndicators() { return indicators; }

}
