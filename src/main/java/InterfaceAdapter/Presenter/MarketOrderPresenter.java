package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.OutputBoundary.MarketOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.MarketOrderOutputData;
import InterfaceAdapter.ViewModel.OrderViewModel;

public class MarketOrderPresenter implements MarketOrderOutputBoundary {

    private final OrderViewModel viewModel;

    public MarketOrderPresenter(OrderViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @Override
    public void successView(MarketOrderOutputData outputData) {
        viewModel.setSuccess(true);
        viewModel.setCompany(outputData.getCompany());
        viewModel.setQuantity(outputData.getQuantity());
        viewModel.setPrice(outputData.getPrice());
        viewModel.setCashBalance(outputData.getNewCashBalance());

        String detailText = String.format(
                "Market %s%nCompany: %s%nShares: %d%n"
                        + "Price per share: $%s%nCash balance: $%s",
                outputData.getBuyOrSell(),
                outputData.getCompany(),
                outputData.getQuantity(),
                outputData.getPrice().toPlainString(),
                outputData.getNewCashBalance().toPlainString());

        viewModel.setDetailsText(detailText);
        viewModel.setSuccess(true);
    }

    @Override
    public void failView(MarketOrderOutputData outputData)
    {
        viewModel.setSuccess(false);

        viewModel.setDetailsText("The order could not be completed.");
    }
}
