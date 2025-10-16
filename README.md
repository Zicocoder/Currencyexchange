> This is the assignment version with static, predefined exchange rates.  
> For the bonus version with automatic live exchange-rate updates, see the  
> [`live-api-version` branch](https://github.com/Zicocoder/Currencyexchange/tree/live-api-version).

# Currency Converter (Workshop Project)

This project was developed as part of the Lexicon Java Fundamentals course (Week 42 Workshop).

## Overview
The application is a console-based currency converter that converts between SEK, USD, and EUR.  
It demonstrates the use of Java fundamentals such as user input handling, control structures, classes, and methods.

## Features
- Console menu for selecting conversion options
- Input validation for numeric and positive values
- Predefined static exchange rates between SEK, USD, and EUR
- Formatted output with date and time of conversion
- Clear separation of logic using classes (`Main` and `CurrencyConverter`)
- Maven project structure for clean organization

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/Zicocoder/Currencyexchange.git
Switch to the main branch:

bash
Copy code
git checkout main
Run the program using Maven or from IntelliJ IDEA:

bash
Copy code
mvn compile exec:java
or simply run the Main class inside IntelliJ.

Example Output
```
=== Currency Converter ===
1. SEK → USD
2. USD → SEK
3. SEK → EUR
4. EUR → SEK
5. USD → EUR
6. EUR → USD
0. Exit

Choose option:
Enter amount: 100
Converted (100.00) SEK → USD = 10.50 [2025-10-16 10:50:29]
```


   Author  
   Zackaria Azzoug  
   Lexicon Java Fundamentals – Week 42 Workshop  
   October 2025




