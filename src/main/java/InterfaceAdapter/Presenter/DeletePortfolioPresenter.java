package InterfaceAdapter.Presenter;
import ApplicationBusinessRules.DeletePortfolio.*;
import InterfaceAdapter.ViewModel.DeletePortfolioViewModel;
import javax.swing.SwingUtilities;
import java.util.Objects;

public class DeletePortfolioPresenter implements DeletePortfolioOutputBoundary {
    private final DeletePortfolioViewModel viewModel;

    // Constructor
    public DeletePortfolioPresenter(
            DeletePortfolioViewModel viewModel
    ) {
        this.viewModel = Objects.requireNonNull(viewModel);
    }

    // Successful deletion (update view model)
    @Override
    public void prepareSuccessView(DeletePortfolioOutputData outputData) {
        updateViewModel(() -> {
            viewModel.setSuccess(true);
            viewModel.setMessage("Portfolio successfully deleted.");
        });
    }

    // Failed deletion (error message, no updates)
    @Override
    public void prepareFailView(String errorMessage) {
        updateViewModel(() -> {
            viewModel.setSuccess(false);
            viewModel.setMessage(errorMessage);
        });
    }

    // Thread handling
    private void updateViewModel(Runnable update) {
        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        } else {
            SwingUtilities.invokeLater(update);
        }
    }
}