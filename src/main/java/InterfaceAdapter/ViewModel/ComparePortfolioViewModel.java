package InterfaceAdapter.ViewModel;

import java.time.LocalDate;
import java.util.*;

public class ComparePortfolioViewModel {

    private String metric;
    private LocalDate startDate;
    private LocalDate endDate;

    private boolean success;
    private String message;

    private final List<ComparisonRowViewModel> results = new ArrayList<>();

    // Getters and setters
    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ComparisonRowViewModel> getResults() {
        return Collections.unmodifiableList(results);
    }

    // Constructor
    public void setResults(List<ComparisonRowViewModel> results) {
        this.results.clear();
        this.results.addAll(results);
    }
}