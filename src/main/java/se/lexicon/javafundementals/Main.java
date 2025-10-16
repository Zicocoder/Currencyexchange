package se.lexicon.javafundementals;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        ExchangeRateService rateService = new ExchangeRateService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Currency Converter ===");
            System.out.println("1. SEK → USD");
            System.out.println("2. USD → SEK");
            System.out.println("3. SEK → EUR");
            System.out.println("4. EUR → SEK");
            System.out.println("5. USD → EUR");
            System.out.println("6. EUR → USD");
            System.out.println("9. Update live rates (online)");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            if (choice == 9) {
                double[] rr = rateService.fetchLatestEUR_USD_SEK();
                if (rr == null) {
                    System.out.println("Could not fetch live rates. Keeping existing rates.");
                } else {
                    converter.setRatesFromEUR(rr[0], rr[1]);
                    System.out.printf("Live rates updated. EUR→USD=%.6f, EUR→SEK=%.6f%n", rr[0], rr[1]);
                    converter.printCurrentRates();
                }
                continue;
            }

            System.out.print("Enter amount: ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount < 0) {
                    System.out.println("Amount cannot be negative.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
                continue;
            }

            double result = converter.convert(choice, amount);
            if (result >= 0) {
                converter.printResult(choice, amount, result);
            } else {
                System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
