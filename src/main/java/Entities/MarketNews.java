package Entities;

public class MarketNews {

    private String Category;
    private String headline;
    private String summary;
    private String URL;


    public MarketNews(String Category, String headline, String summary, String URL) {
        this.Category = Category;
        this.headline = headline;
        this.summary = summary;
        this.URL = URL;
    }

    //getters
    public String getCategory() {
        return this.Category;
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
    public void setCategory(String Category) {
        this.Category = Category;
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
