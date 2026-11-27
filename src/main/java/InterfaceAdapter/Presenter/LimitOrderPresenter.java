package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.OutputBoundary.LimitOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.LimitOrderOutputData;
import InterfaceAdapter.ViewModel.OrderViewModel;

public class LimitOrderPresenter implements LimitOrderOutputBoundary {

    private final OrderViewModel viewModel;

    public LimitOrderPresenter(OrderViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @Override
    public void successView(LimitOrderOutputData outputData) {
        viewModel.setSuccess(true);
        viewModel.setCompany(outputData.getCompany());
        viewModel.setQuantity(outputData.getQuantity());
        viewModel.setPrice(outputData.getLimitPrice());
        viewModel.setCashBalance(outputData.getNewCashBalance());

        String detailText = String.format(
                "Limit %s%nCompany: %s%nShares: %d%n"
                        + "Limit price per share: $%s%nCash balance: $%s",
                outputData.getBuyOrSell(),
                outputData.getCompany(),
                outputData.getQuantity(),
                outputData.getLimitPrice().toPlainString(),
                outputData.getNewCashBalance().toPlainString());

        viewModel.setDetailsText(detailText);
        viewModel.setSuccess(true);
    }

    @Override
    public void failView(LimitOrderOutputData outputData)
    {
        viewModel.setSuccess(false);

        viewModel.setDetailsText("The order could not be completed.");
    }
}
