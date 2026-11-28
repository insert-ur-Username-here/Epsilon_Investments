package ApplicationBusinessRules.UseCaseInteractor;
import ApplicationBusinessRules.InputBoundary.IndicatorSpec;
import Entities.Indicator;
import Entities.SimpleMovingAverage;
import java.util.ArrayList;
import java.util.List;

/* Class Overview:
Parses IndicatorSpecs into Indicator entities. Invalid parameters surface as
IllegalArgumentException (from the entity constructors), which interactors present to the user.
 */

public final class IndicatorFactory {

    private IndicatorFactory() {}

    public static List<Indicator> create(List<IndicatorSpec> specs) {
        if (specs == null) {
            throw new IllegalArgumentException("Indicator specs are required");
        }
        List<Indicator> out = new ArrayList<>(specs.size());
        for (IndicatorSpec spec : specs) {
            out.add(create(spec));
        }
        return out;
    }

    public static Indicator create(IndicatorSpec spec) {
        if (spec == null) {
            throw new IllegalArgumentException("Indicator spec is required");
        }
        switch (spec.getType()) {
            case SMA:
                requireParams(spec, 1, "period");
                return new SimpleMovingAverage(spec.getParams().get(0));
            default:
                throw new IllegalArgumentException("Unsupported indicator: " + spec.getType());
        }
    }

    private static void requireParams(IndicatorSpec spec, int count, String description) {
        if (spec.getParams().size() != count) {
            throw new IllegalArgumentException(
                    spec.getType() + " needs " + count + " parameter(s): " + description);
        }
    }
}