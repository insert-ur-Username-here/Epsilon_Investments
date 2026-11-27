package ApplicationBusinessRules.UseCaseInteractor;

import Entities.CandleSeries;
import Entities.Indicator;
import Entities.IndicatorSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Class Overview:
Runs a list of indicators over one candle series, so every interactor computes them
the same way. All results come from the same series, so they stay aligned with it.
 */

public final class IndicatorRunner {

    private IndicatorRunner() {}   // static helper only

    public static List<IndicatorSeries> run(List<Indicator> indicators, CandleSeries candles) {
        if (indicators == null) {
            throw new IllegalArgumentException("Indicators are required");
        }
        if (candles == null) {
            throw new IllegalArgumentException("Candles are required");
        }
        List<IndicatorSeries> results = new ArrayList<>(indicators.size());
        for (Indicator indicator : indicators) {
            results.add(indicator.calculate(candles));
        }
        return Collections.unmodifiableList(results);
    }
}