package se.lexicon.javafundementals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ExchangeRateService {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    // Tries multiple providers. Returns {eurToUsd, eurToSek} or null
    public double[] fetchLatestEUR_USD_SEK() {
        // 1) exchangerate.host (HTTPS)
        double[] r = fetchFromExchangerateHost("https://api.exchangerate.host/latest?base=EUR&symbols=USD,SEK");
        if (r != null) return r;

        // 2) exchangerate.host (HTTP – some machines have TLS issues)
        r = fetchFromExchangerateHost("http://api.exchangerate.host/latest?base=EUR&symbols=USD,SEK");
        if (r != null) return r;

        // 3) fallback: open.er-api.com (base code set by path)
        r = fetchFromErApi("https://open.er-api.com/v6/latest/EUR");
        if (r != null) return r;

        return null;
    }

    private double[] fetchFromExchangerateHost(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(8))
                    .header("User-Agent", "JavaHttpClient/1.0")
                    .GET().build();

            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                System.out.println("[exchangerate.host] HTTP " + resp.statusCode()
                        + " body: " + truncate(resp.body(), 200));
                return null;
            }

            JsonNode root = mapper.readTree(resp.body());
            JsonNode rates = root.get("rates");
            if (rates == null || rates.get("USD") == null || rates.get("SEK") == null) {
                System.out.println("[exchangerate.host] Unexpected JSON: " + truncate(resp.body(), 200));
                return null;
            }
            double eurToUsd = rates.get("USD").asDouble();
            double eurToSek = rates.get("SEK").asDouble();
            return new double[]{eurToUsd, eurToSek};
        } catch (IOException | InterruptedException e) {
            System.out.println("[exchangerate.host] Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return null;
        }
    }

    private double[] fetchFromErApi(String url) {
        try {
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(8))
                    .header("User-Agent", "JavaHttpClient/1.0")
                    .GET().build();

            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                System.out.println("[open.er-api.com] HTTP " + resp.statusCode()
                        + " body: " + truncate(resp.body(), 200));
                return null;
            }

            JsonNode root = mapper.readTree(resp.body());
            JsonNode rates = root.get("rates");
            JsonNode base = root.get("base_code");
            if (rates == null || base == null || !"EUR".equals(base.asText())) {
                System.out.println("[open.er-api.com] Unexpected JSON: " + truncate(resp.body(), 200));
                return null;
            }
            if (rates.get("USD") == null || rates.get("SEK") == null) {
                System.out.println("[open.er-api.com] Missing USD/SEK: " + truncate(resp.body(), 200));
                return null;
            }
            double eurToUsd = rates.get("USD").asDouble();
            double eurToSek = rates.get("SEK").asDouble();
            return new double[]{eurToUsd, eurToSek};
        } catch (IOException | InterruptedException e) {
            System.out.println("[open.er-api.com] Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return null;
        }
    }

    private static String truncate(String s, int n) {
        if (s == null) return "null";
        return s.length() <= n ? s : s.substring(0, n) + "...";
    }
}
