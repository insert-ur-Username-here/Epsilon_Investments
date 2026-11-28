package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.OutputBoundary.ExecuteOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.ExecuteOrderOutputData;
import InterfaceAdapter.ViewModel.ExecuteOrderViewModel;

public class ExecuteOrderPresenter implements ExecuteOrderOutputBoundary
{
    private final ExecuteOrderViewModel viewModel;

    public ExecuteOrderPresenter(ExecuteOrderViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ExecuteOrderOutputData outputData)
    {
        if (outputData.getFills().isEmpty())
        {
            viewModel.setDetailsText("");
            return;
        }

        StringBuilder detailsText = new StringBuilder();
        for (ExecuteOrderOutputData.Fill fill : outputData.getFills())
        {
            detailsText.append(String.format("%s %d shares of %s%nExecution price per share: $%s%n%n", fill.getBuyOrSell(), fill.getQuantity(), fill.getCompany(), fill.getExecutionPrice().toPlainString()));
        }

        detailsText.append("Available cash: $").append(outputData.getCashBalance().toPlainString());
        viewModel.setDetailsText(detailsText.toString());
    }
}
