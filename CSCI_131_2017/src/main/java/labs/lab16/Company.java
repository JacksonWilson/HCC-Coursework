package labs.lab16;

public class Company implements Comparable<Company> {
    private String tickerSymbol;
    private String stockExchange;
    private String name;

    public Company(String tickerSymbol, String stockExchange, String name) {
        this.tickerSymbol = tickerSymbol;
        this.stockExchange = stockExchange;
        this.name = name;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Company other) {
        int cmp = this.name.compareToIgnoreCase(other.name);
        if (cmp == 0)
            return this.stockExchange.compareToIgnoreCase(other.stockExchange);
        return cmp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj instanceof Company) {
            Company other = (Company)obj;
            return this.name.equalsIgnoreCase(other.name)
                && this.stockExchange.equalsIgnoreCase(other.stockExchange);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", tickerSymbol, name, stockExchange);
    }
}