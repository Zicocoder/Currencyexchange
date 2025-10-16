package se.lexicon.javafundementals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // Returns {eurToUsd, eurToSek} or null on failure
    public double[] fetchLatestEUR_USD_SEK() {
        String url = "https://api.exchangerate.host/latest?base=EUR&symbols=USD,SEK";
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        try {
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) return null;

            JsonNode root = mapper.readTree(resp.body());
            JsonNode rates = root.get("rates");
            if (rates == null || rates.get("USD") == null || rates.get("SEK") == null) return null;

            double eurToUsd = rates.get("USD").asDouble();
            double eurToSek = rates.get("SEK").asDouble();
            return new double[]{eurToUsd, eurToSek};
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}
