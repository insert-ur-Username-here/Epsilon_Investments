package ApplicationBusinessRules.InputBoundary;

import java.util.List;

/* Class Overview:
The specification for one indicator in plain values (e.g. SMA with period 20), so the UI never
constructs entities. IndicatorFactory turns it into a real Indicator. Immutable.
 */
public final class IndicatorSpec {
    private final IndicatorType type;
    private final List<Integer> params;

    public IndicatorSpec(IndicatorType type, List<Integer> params) {
        if (type == null) {
            throw new IllegalArgumentException("Indicator type is required");
        }
        if (params == null) {
            throw new IllegalArgumentException("Indicator parameters are required");
        }
        for (Integer p : params) {
            if (p == null) {
                throw new IllegalArgumentException("Null indicator parameter");
            }
        }
        this.type = type;
        this.params = List.copyOf(params);
    }

    public static IndicatorSpec sma(int period) {
        return new IndicatorSpec(IndicatorType.SMA, List.of(period));
    }

    public IndicatorType getType() { return type; }
    public List<Integer> getParams() { return params; }

    @Override
    public String toString() {
        return type + params.toString();
    }
}