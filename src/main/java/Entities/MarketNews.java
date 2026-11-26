package Entities;

public class MarketNews {

    private String Catagory;
    private String headline;
    private String summary;
    private String URL;


    public MarketNews(String Catagory, String headline, String summary, String URL) {
        this.Catagory = Catagory;
        this.headline = headline;
        this.summary = summary;
        this.URL = URL;
    }

    //getters
    public String getCatagory() {
        return this.Catagory;
    }
    public String getHeadline() {
        return this.headline;
    }
    public String getSummary() {
        return this.summary;
    }
    public String getURL() {
        return this.URL;
    }

    //setters
    public void setCatagory(String Catagory) {
        this.Catagory = Catagory;
    }
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }



}
