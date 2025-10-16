package se.lexicon.javafundementals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrencyConverter {

    private final double SEK_TO_USD = 0.09;
    private final double USD_TO_SEK = 11.10;
    private final double SEK_TO_EUR = 0.088;
    private final double EUR_TO_SEK = 11.40;
    private final double USD_TO_EUR = 0.96;
    private final double EUR_TO_USD = 1.04;

    public double convert(int choice, double amount) {
        return switch (choice) {
            case 1 -> amount * SEK_TO_USD;
            case 2 -> amount * USD_TO_SEK;
            case 3 -> amount * SEK_TO_EUR;
            case 4 -> amount * EUR_TO_SEK;
            case 5 -> amount * USD_TO_EUR;
            case 6 -> amount * EUR_TO_USD;
            default -> -1;
        };
    }

    public void printResult(int choice, double amount, double result) {
        String[] labels = {
                "SEK → USD", "USD → SEK", "SEK → EUR",
                "EUR → SEK", "USD → EUR", "EUR → USD"
        };
        String conversion = (choice >= 1 && choice <= 6) ? labels[choice - 1] : "Unknown";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("Converted (%.2f) %s = %.2f  [%s]%n",
                amount, conversion, result, dtf.format(LocalDateTime.now()));
    }
}
