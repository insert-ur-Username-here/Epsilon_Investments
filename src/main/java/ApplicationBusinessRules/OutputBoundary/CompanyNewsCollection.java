package ApplicationBusinessRules.OutputBoundary;
import Entities.CompanyNews;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyNewsCollection {

    // Identifies the blocks of new to fetch information from  in the JSON format.
    private static final Pattern OBJECT_PATTERN = Pattern.compile("\\{[^{}]*\\}");
    // Identifies the pattern of the headline to fetch for each news.
    private static final Pattern HEADLINE_PATTERN = Pattern.compile("\"headline\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    // Identifies the pattern of the summary to fetch for each news.
    private static final Pattern SUMMARY_PATTERN = Pattern.compile("\"summary\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    // Identifies the pattern of the URL to fetch for each news.
    private static final Pattern URL_PATTERN = Pattern.compile("\"url\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    // Identifies the pattern of the "related" section to fetch for each news.
    private static final Pattern RELATED_PATTERN = Pattern.compile("\"related\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

    private String CompanyName;
    private ArrayList<CompanyNews> CollectionOfCompanyNews;


    public CompanyNewsCollection(String data) {
        this.CompanyName = "";
        CollectionOfCompanyNews = new ArrayList<>();
        fetchJSONData(data);
    }
    // Fetches the data from the api JSON response and populates the Array CollectionOfCompanyNews
    private void fetchJSONData(String data) {
        if (data == null || data.isBlank()) {
            return;
        }

        Matcher objectMatcher = OBJECT_PATTERN.matcher(data);
        while (objectMatcher.find()) {
            String JSONItem = objectMatcher.group();
            String related = getValue(RELATED_PATTERN, JSONItem);
            if (CompanyName.isEmpty() && !related.isEmpty()) {
                CompanyName = related;
            }

            String headline = getValue(HEADLINE_PATTERN, JSONItem);
            String summary = getValue(SUMMARY_PATTERN, JSONItem);
            String url = getValue(URL_PATTERN, JSONItem);

            CollectionOfCompanyNews.add(new CompanyNews(related, headline, summary, url));
        }
    }

    // returns the string  from the api JSON response with the specified pattern and an empty string if nothing is found.
    public String getValue(Pattern pattern, String data) {
        Matcher matcher = pattern.matcher(data);
        if (!matcher.find()) {
            return "";
        }
        return JSONToText(matcher.group(1));
    }

    // converts text to a readable text and returns it.
    private String JSONToText(String text) {
        return text
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\/", "/")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }


    //getters
    public ArrayList<CompanyNews> getCollectionOfCompanyNews() {
        return CollectionOfCompanyNews;
    }
    public String getCompanyName() {
        return CompanyName;
    }
}
