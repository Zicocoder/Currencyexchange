> This is the enhanced version with automatic live exchange-rate updates.  
> For the original assignment with static rates, see the
> [main branch](https://github.com/Zicocoder/Currencyexchange/tree/main).

# Currency Converter – Live API Version

This branch contains an enhanced version of the CurrencyExchange workshop project.

## Overview
This version automatically fetches live currency exchange rates on startup using a free online API.
It performs conversions between SEK, USD, and EUR based on the most recent market data.
If a network or API error occurs, the program falls back to predefined static rates.

## Features
- Automatic live rate fetching at startup
- Fallback to static rates if the connection fails
- Formatted output showing current exchange rates and last update timestamp
- Conversions between SEK, USD, and EUR
- Clean code structure using separate classes:
    - `Main` – runs the console menu
    - `CurrencyConverter` – performs calculations and displays rates
    - `ExchangeRateService` – handles HTTP requests and JSON parsing

## How It Works
1. When the program starts, it automatically attempts to fetch live rates.
2. If successful, the converter prints:
````
   Live rates successfully updated:
   1 EUR = 1.1637 USD
   1 EUR = 11.0302 SEK
   1 USD = 9.4785 SEK
   Last update: 2025-10-16 10:52:21 UTC  
````
3. If the API cannot be reached, it uses static fallback values and continues normally.

## Technical Details
- Implemented using Java 17
- Built and managed with Maven
- Uses Jackson (Databind) for JSON parsing
- Uses the built-in Java HttpClient for network requests
- Tries `exchangerate.host` first, with automatic fallback to `open.er-api.com`

## How to Run

1. Clone the repository and switch to this branch:
```bash
git clone https://github.com/Zicocoder/Currencyexchange.git
cd Currencyexchange
git checkout live-api-version
````
2. Build and run:
```
mvn compile exec:java
```
or simply run the ```Main``` class in IntelliJ.

## Example Output
```
Fetching latest currency rates...
Live rates successfully updated:
1 EUR = 1.1637 USD
1 EUR = 11.0302 SEK
1 USD = 9.4785 SEK
Last update: 2025-10-16 10:52:21 UTC

=== Currency Converter ===
1. SEK → USD
2. USD → SEK
3. SEK → EUR
4. EUR → SEK
5. USD → EUR
6. EUR → USD  
0. Exit

Choose option:  
```
<br>

Author  
**Zackaria Azzoug**  
Lexicon Java Fundamentals – Week 42 Workshop  
October 2025