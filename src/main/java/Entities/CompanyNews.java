package Entities;
public class CompanyNews {

    private String CompanyName;
    private String headline;
    private String summary;
    private String URL;

    public CompanyNews(String CompanyName, String headline, String summary, String URL) {
        this.CompanyName = CompanyName;
        this.headline = headline;
        this.summary = summary;
        this.URL = URL;
    }

    //getters
    public String getCompanyName() {
        return this.CompanyName;
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
    public void setCompanyName(String companyName) {
        this.CompanyName = companyName;
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
