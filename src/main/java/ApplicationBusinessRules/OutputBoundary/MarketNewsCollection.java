package ApplicationBusinessRules.OutputBoundary;

import Entities.MarketNews;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarketNewsCollection implements OutputBoundary {

    // Identifies JSON object blocks in the response.
    private static final Pattern OBJECT_PATTERN = Pattern.compile("\\{[^{}]*\\}");
    // Identifies field patterns for each part.
    private static final Pattern HEADLINE_PATTERN = Pattern.compile("\"headline\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    private static final Pattern SUMMARY_PATTERN = Pattern.compile("\"summary\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    private static final Pattern URL_PATTERN = Pattern.compile("\"url\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("\"category\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

    private String Category;
    private ArrayList<MarketNews> CollectionOfMarketNews;

    public MarketNewsCollection(String data) {
        this(data, "");
    }

    public MarketNewsCollection(String data, String category) {
        if (category == null) {
            this.Category = "";
        } else {
            this.Category = category;
        }
        this.CollectionOfMarketNews = new ArrayList<>();
        fetchJSONData(data);
    }

    // Fetches data from the API JSON response and populates CollectionOfMarketNews.
    private void fetchJSONData(String data) {
        if (data == null || data.isBlank()) {
            return;
        }

        Matcher objectMatcher = OBJECT_PATTERN.matcher(data);
        while (objectMatcher.find()) {
            String jsonItem = objectMatcher.group();

            String extractedCategory = getValue(CATEGORY_PATTERN, jsonItem);
            if (Category.isEmpty() && !extractedCategory.isEmpty()) {
                Category = extractedCategory;
            }

            String itemCategory;
            if (!Category.isEmpty()) {
                itemCategory = Category;
            } else {
                itemCategory = extractedCategory;
            }

            String headline = getValue(HEADLINE_PATTERN, jsonItem);
            String summary = getValue(SUMMARY_PATTERN, jsonItem);
            String url = getValue(URL_PATTERN, jsonItem);

            CollectionOfMarketNews.add(new MarketNews(itemCategory, headline, summary, url));
        }
    }

    // returns the string from the api JSON response with the specified pattern and an empty string if nothing is found.
    public String getValue(Pattern pattern, String data) {
        Matcher matcher = pattern.matcher(data);
        if (!matcher.find()) {
            return "";
        }
        return JSONToText(matcher.group(1));
    }

    private String JSONToText(String text) {
        return text
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\/", "/")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

    // getters
    public List<MarketNews> getCollectionOfMarketNews() {
        return CollectionOfMarketNews;
    }

    public String getCatagory() {
        return Category;
    }
}