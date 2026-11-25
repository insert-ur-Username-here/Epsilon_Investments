package ApplicationBusinessRules;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.regex.*;


public class StocksSearcher {

    private static final String BASE_PATH = "https://finnhub.io/api/v1";
    private static final String KEY = "dc2cu2hr01qjj5fi6s60dc2cu2hr01qjj5fi6s6g";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String FROM = LocalDate.now().minusYears(1).toString();
    private static final String TO = LocalDate.now().toString();

    private static final Pattern MSPR_PATTERN = Pattern.compile("\"mspr\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("\"symbol\"\\s*:\\s*\"([^\"]+)\"");



    // URI.create(...) converts a string to a URI object that HttpRequest uses.
    // .header(...) adds the authentication header to the http request.
    // .build builds up the http request.
    // CLIENT.send sends the request and waits for a response
    // HttpResponse.BodyHandlers.ofString() to convert the response to a String, without this the code will not compile.
    // .body extracts the String from HttpResponse to return it.
    private static String get(String path) throws Exception {
        HttpRequest request =  HttpRequest.newBuilder(URI.create(BASE_PATH + path)).header("X-Finnhub-Token", KEY)
                .build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    //URLEncoder.encode changes the format of a parameter to a URL format. Foe example, "apple inc" becomes "apple+inc"
    //@param category must be either "general", "forex", "crypto", or "merger".
    public static String getMarketNews(String category) throws Exception {
        return get("/news?category=" + URLEncoder.encode(category, StandardCharsets.UTF_8));
    }
    // returns the input company news.
    public static String getCompanyNews(String input) throws Exception {
        String symbol = searchSymbol(input);
        return get("/company-news?symbol=" + URLEncoder.encode(symbol, StandardCharsets.UTF_8)
                + "&from=" + FROM + "&to=" + TO);
    }

    // returns the sentiment value of an input company.
    public static double getSentiment(String input) throws Exception {
        String symbol = searchSymbol(input);
        String data =  get("/stock/insider-sentiment?symbol=" + URLEncoder.encode(symbol, StandardCharsets.UTF_8)
                + "&from=" + FROM + "&to=" + TO);

        return CalculateSentiment(data);
    }

    // returns the average sentiment value of a company given its data as input.
   public static double CalculateSentiment(String data) {
       Matcher m = MSPR_PATTERN.matcher(data);
       double sum = 0;
       int count = 0;

       while (m.find()) {
           sum += Double.parseDouble(m.group(1));
           count++;
       }
       return count == 0 ? 0.0 : sum / count;
    }
    // Finds the matching ticker of the input.
    public static String searchSymbol(String input) throws Exception {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank. Please enter a ticker or a company name.");
        }

        String trimmed = input.trim();
        String result = get ("/search?q=" + URLEncoder.encode(trimmed, StandardCharsets.UTF_8));
        String candidate = null;

        Matcher m = SYMBOL_PATTERN.matcher(result);
        while(m.find()) {
            String symbol = m.group(1).trim();
            if (candidate == null) {
                candidate = symbol;
            }
            if (symbol.equalsIgnoreCase(trimmed)) {
                return symbol;
            }
        }

        if (candidate != null ) {
            return candidate;
        }
        throw new IllegalArgumentException("No matching ticker found for: " + input);
    }
}