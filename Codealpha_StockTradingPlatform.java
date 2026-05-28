import java.util.*;

// Class to represent a Stock
class Stock {
    String symbol;
    String companyName;
    double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public void displayStock() {
        System.out.println(symbol + " - " + companyName + " : ₹" + price);
    }
}

// Class to represent a User Portfolio
class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double balance;

    public Portfolio(double balance) {
        this.balance = balance;
    }

    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;

        if (totalCost <= balance) {
            balance -= totalCost;
            holdings.put(stock.symbol,
                    holdings.getOrDefault(stock.symbol, 0) + quantity);

            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock.symbol) &&
                holdings.get(stock.symbol) >= quantity) {

            holdings.put(stock.symbol,
                    holdings.get(stock.symbol) - quantity);

            balance += stock.price * quantity;

            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }

    public void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n===== PORTFOLIO =====");
        double totalValue = balance;

        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);

            if (qty > 0) {
                Stock stock = market.get(symbol);
                double value = qty * stock.price;

                System.out.println(symbol + " : " + qty +
                        " shares | Value = ₹" + value);

                totalValue += value;
            }
        }

        System.out.println("Available Balance : ₹" + balance);
        System.out.println("Total Portfolio Value : ₹" + totalValue);
    }
}

// Main Class
public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Market Data
        Map<String, Stock> market = new HashMap<>();

        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500));
        market.put("INFY", new Stock("INFY", "Infosys", 1500));
        market.put("RELIANCE", new Stock("RELIANCE", "Reliance Industries", 2800));

        // User Portfolio
        Portfolio user = new Portfolio(100000);

        int choice;

        do {
            System.out.println("\n====== STOCK TRADING PLATFORM ======");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock stock : market.values()) {
                        stock.displayStock();
                    }
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        user.buyStock(market.get(buySymbol), qty);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        user.sellStock(market.get(sellSymbol), qty);
                    } else {
                        System.out.println("Stock not found!");
                    }
                    break;

                case 4:
                    user.displayPortfolio(market);
                    break;

                case 5:
                    System.out.println("Thank You for Using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}