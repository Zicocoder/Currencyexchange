package se.lexicon.javafundementals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrencyConverter {

    // Defaults (static fallback)
    private double SEK_TO_USD = 0.105468;
    private double USD_TO_SEK = 9.481587;
    private double SEK_TO_EUR = 0.090748;
    private double EUR_TO_SEK = 11.019500;
    private double USD_TO_EUR = 0.860437;
    private double EUR_TO_USD = 1.162200;
    private Instant lastUpdatedUtc = null;

    // Update all pairs from two base inputs (EUR base)
    public void setRatesFromEUR(double eurToUsd, double eurToSek) {
        this.EUR_TO_USD = eurToUsd;
        this.USD_TO_EUR = 1.0 / eurToUsd;

        this.EUR_TO_SEK = eurToSek;
        this.SEK_TO_EUR = 1.0 / eurToSek;

        this.USD_TO_SEK = this.USD_TO_EUR * eurToSek; // USD→SEK
        this.SEK_TO_USD = this.SEK_TO_EUR * eurToUsd; // SEK→USD
        this.lastUpdatedUtc = Instant.now(); // store in UTC

    }

    public double convert(int choice, double amount) {
        return switch (choice) {
            case 1 -> amount * SEK_TO_USD; // SEK → USD
            case 2 -> amount * USD_TO_SEK; // USD → SEK
            case 3 -> amount * SEK_TO_EUR; // SEK → EUR
            case 4 -> amount * EUR_TO_SEK; // EUR → SEK
            case 5 -> amount * USD_TO_EUR; // USD → EUR
            case 6 -> amount * EUR_TO_USD; // EUR → USD
            default -> -1;
        };
    }

    public void printResult(int choice, double amount, double result) {
        String[] labels = {"SEK → USD","USD → SEK","SEK → EUR","EUR → SEK","USD → EUR","EUR → USD"};
        String conversion = (choice >= 1 && choice <= 6) ? labels[choice - 1] : "Unknown";
        String ts = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        System.out.printf("Converted (%.2f) %s = %.2f  [%s]%n", amount, conversion, result, ts);
    }

    public void printCurrentRates() {
        System.out.printf("Rates now: EUR→USD=%.6f, EUR→SEK=%.6f, USD→SEK=%.6f%n",
                EUR_TO_USD, EUR_TO_SEK, USD_TO_SEK);
    }

    public void printUpdatedRatesMessage() {
        System.out.println("\n✅ Live rates successfully updated:");
        System.out.printf("   1 EUR = %.4f USD%n", EUR_TO_USD);
        System.out.printf("   1 EUR = %.4f SEK%n", EUR_TO_SEK);
        System.out.printf("   1 USD = %.4f SEK%n", USD_TO_SEK);

        if (lastUpdatedUtc != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")
                    .withZone(ZoneId.of("UTC"));
            System.out.println("   Last update: " + fmt.format(lastUpdatedUtc));
        }
        System.out.println();
    }

}
