package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.CreatePortfolio.*;
import InterfaceAdapter.ViewModel.CreatePortfolioViewModel;

public class CreatePortfolioPresenter implements CreatePortfolioOutputBoundary {

    private final CreatePortfolioViewModel viewModel;

    public CreatePortfolioPresenter(CreatePortfolioViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreatePortfolioOutputData outputData) {

        viewModel.setPortfolioId(outputData.getPortfolioId());
        viewModel.setPortfolioName(outputData.getPortfolioName());
        viewModel.setDateCreated(outputData.getDateCreated());
        viewModel.setInitialCash(outputData.getInitialCash());
        viewModel.setPortfolioValue(outputData.getPortfolioValue());
        viewModel.setSuccess(true);
        viewModel.setMessage("Portfolio successfully created!");
    }

    @Override
    public void prepareFailView(String errorMessage) {

        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}