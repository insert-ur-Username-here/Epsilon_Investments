package ApplicationBusinessRules.OutputBoundary;

import Entities.MarketNews;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarketNewsCollection {

    // Identifies the pattern for the category.
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("\"category\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");

    private String Category;
    private ArrayList<MarketNews> CollectionOfMarketNews;


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

        List<String> jsonObjects = extractTopLevelObjects(data);
        for (String jsonItem : jsonObjects) {
            String extractedCategory = getValue(CATEGORY_PATTERN, jsonItem);
            if (Category.isEmpty() && !extractedCategory.isEmpty()) {
                Category = extractedCategory;
            }

            String itemCategory = !Category.isEmpty() ? Category : extractedCategory;
            String headline = extractStringField(jsonItem, "headline");
            String summary = extractStringField(jsonItem, "summary");
            String url = extractStringField(jsonItem, "url");

            CollectionOfMarketNews.add(new MarketNews(itemCategory, headline, summary, url));
        }
    }

    //Most of the code from here is to prevent StackOverFlows that might occur from a long api response.
    private List<String> extractTopLevelObjects(String json) {
        List<String> objects = new ArrayList<>();
        int depth = 0;
        int startIndex = -1;
        boolean inString = false;
        boolean escaping = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escaping) {
                escaping = false;
                continue;
            }
            if (c == '\\' && inString) {
                escaping = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (inString) {
                continue;
            }

            if (c == '{') {
                if (depth == 0) {
                    startIndex = i;
                }
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && startIndex != -1) {
                    objects.add(json.substring(startIndex, i + 1));
                    startIndex = -1;
                } else if (depth < 0) {
                    depth = 0;
                    startIndex = -1;
                }
            }
        }

        return objects;
    }


    private String extractStringField(String source, String fieldName) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        String token = "\"" + fieldName + "\"";
        int searchFrom = 0;

        while (searchFrom < source.length()) {
            int keyPos = source.indexOf(token, searchFrom);
            if (keyPos == -1) {
                return "";
            }

            int colonPos = source.indexOf(':', keyPos + token.length());
            if (colonPos == -1) {
                return "";
            }

            int i = colonPos + 1;
            while (i < source.length() && Character.isWhitespace(source.charAt(i))) {
                i++;
            }

            if (i < source.length() && source.charAt(i) == '"') {
                StringBuilder sb = new StringBuilder();
                boolean escaped = false;

                for (int j = i + 1; j < source.length(); j++) {
                    char c = source.charAt(j);
                    if (escaped) {
                        sb.append('\\').append(c);
                        escaped = false;
                    } else if (c == '\\') {
                        escaped = true;
                    } else if (c == '"') {
                        return JSONToText(sb.toString());
                    } else {
                        sb.append(c);
                    }
                }
                return "";
            }

            searchFrom = colonPos + 1;
        }

        return "";
    }

    public String getValue(Pattern pattern, String data) {
        if (pattern == null || data == null || data.isEmpty()) {
            return "";
        }
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

    public String getCategory() {
        return Category;
    }
}