package InterfaceAdapter.Controller;

import ApplicationBusinessRules.InputBoundary.ExecuteOrderInputBoundary;
import ApplicationBusinessRules.InputData.ExecuteOrderInputData;

public class ExecuteOrderController
{
    private final ExecuteOrderInputBoundary useCase;

    public ExecuteOrderController(ExecuteOrderInputBoundary useCase)
    {
        this.useCase = useCase;
    }

    public void execute()
    {
        ExecuteOrderInputData inputData = new ExecuteOrderInputData();
        useCase.execute(inputData);
    }
}
