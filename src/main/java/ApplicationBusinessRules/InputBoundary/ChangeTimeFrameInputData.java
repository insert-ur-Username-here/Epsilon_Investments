package ApplicationBusinessRules.InputBoundary;

import Entities.TimeFrame;

public class ChangeTimeFrameInputData {

    private final TimeFrame timeFrame;

    public ChangeTimeFrameInputData(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;   // null is allowed; the interactor presents a friendly message
    }

    public TimeFrame getTimeFrame() { return timeFrame; }

}
