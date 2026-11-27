package InterfaceAdapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DeletePortfolioViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private String message;

    // Property change listener
    public void addPropertyChangeListener(
            PropertyChangeListener listener
    ) {
        support.addPropertyChangeListener(listener);
    }

    // Getters and setters
    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        boolean old = this.success;
        this.success = success;
        // Report success upon call
        support.firePropertyChange("success", old, success);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        // Report message change upon call
        support.firePropertyChange("message", old, message);
    }
}