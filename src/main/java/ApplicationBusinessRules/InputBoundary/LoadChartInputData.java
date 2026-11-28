package ApplicationBusinessRules.InputBoundary;

import Entities.TimeFrame;

import java.util.List;

public class LoadChartInputData {
    private final String symbol;
    private final TimeFrame timeFrame;
    private final int candleCount;
    private final List<IndicatorSpec> indicators;

    public LoadChartInputData(String symbol, TimeFrame timeFrame, int candleCount,
                              List<IndicatorSpec> indicators) {
        this.symbol = symbol;           // null/blank allowed: message given in that case
        this.timeFrame = timeFrame;
        this.candleCount = candleCount;
        this.indicators = indicators == null ? List.of() : List.copyOf(indicators);
    }

    public String getSymbol() { return symbol; }
    public TimeFrame getTimeFrame() { return timeFrame; }
    public int getCandleCount() { return candleCount; }
    public List<IndicatorSpec> getIndicators() { return indicators; }
}