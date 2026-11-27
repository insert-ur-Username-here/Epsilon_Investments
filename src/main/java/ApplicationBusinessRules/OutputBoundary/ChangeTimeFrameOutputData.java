package ApplicationBusinessRules.OutputBoundary;

import Entities.CandleSeries;
import Entities.IndicatorSeries;
import Entities.Quote;
import Entities.TimeFrame;

import java.util.List;

public class ChangeTimeFrameOutputData {
    private final TimeFrame timeFrame;
    private final CandleSeries candles;
    private final Quote quote;
    private final List<IndicatorSeries> indicators;

    public ChangeTimeFrameOutputData(TimeFrame timeFrame, CandleSeries candles,
                                     Quote quote, List<IndicatorSeries> indicators) {
        if (timeFrame == null || candles == null || quote == null || indicators == null) {
            throw new IllegalArgumentException("All output data fields are required");
        }
        this.timeFrame = timeFrame;
        this.candles = candles;
        this.quote = quote;
        this.indicators = List.copyOf(indicators);
    }

    public TimeFrame getTimeFrame() { return timeFrame; }
    public CandleSeries getCandles() { return candles; }
    public Quote getQuote() { return quote; }
    public List<IndicatorSeries> getIndicators() { return indicators; }

}
