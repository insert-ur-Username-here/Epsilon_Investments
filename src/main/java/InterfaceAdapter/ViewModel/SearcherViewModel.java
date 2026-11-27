package InterfaceAdapter.ViewModel;

import Entities.CompanyNews;
import Entities.MarketNews;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class SearcherViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String activeMode = "";
    private String header = "";
    private String errorMessage = "";
    private List<CompanyNews> companyNewsList = new ArrayList<>();
    private List<MarketNews> marketNewsList = new ArrayList<>();

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setCompanyNewsState(String header, List<CompanyNews> items) {
        this.activeMode = "COMPANY";
        this.header = header;
        this.errorMessage = "";
        this.companyNewsList = (items == null) ? new ArrayList<>() : items;
        this.marketNewsList = new ArrayList<>();
        support.firePropertyChange("state", null, this.activeMode);
    }

    public void setMarketNewsState(String header, List<MarketNews> items) {
        this.activeMode = "MARKET";
        this.header = header;
        this.errorMessage = "";
        this.marketNewsList = (items == null) ? new ArrayList<>() : items;
        this.companyNewsList = new ArrayList<>();
        support.firePropertyChange("state", null, this.activeMode);
    }

    public void setErrorState(String errorMessage) {
        this.activeMode = "ERROR";
        this.header = "Error";
        this.errorMessage = (errorMessage == null) ? "" : errorMessage;
        this.companyNewsList = new ArrayList<>();
        this.marketNewsList = new ArrayList<>();
        support.firePropertyChange("state", null, this.activeMode);
    }

    public String getActiveMode() {
        return activeMode;
    }

    public String getHeader() {
        return header;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<CompanyNews> getCompanyNewsList() {
        return companyNewsList;
    }

    public List<MarketNews> getMarketNewsList() {
        return marketNewsList;
    }
}