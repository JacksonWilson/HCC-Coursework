package labs.lab16;

import utils.SearchAlgorithms;
import utils.SortAlgorithms;

public class CompanyMaintainer {

    public static String linearSearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.linear(companies, c));
    }

    public static String binarySearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.binary(companies, c));
    }
    
    public static String ternarySearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.ternary(companies, c));
    }
    
    public static String jumpSearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.jump(companies, c));
    }
    
    public static String interpolationSearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.interpolation(companies, c));
    }
    
    public static String exponentialSearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.exponential(companies, c));
    }
    
    public static String fibonacciSearch(Company[] companies, Company c) {
        return getTickerSymbol(companies, SearchAlgorithms.fibonacci(companies, c));
    }

    public static void sort(Company[] companies) {
        SortAlgorithms.quick(companies);
    }
    
    private static String getTickerSymbol(Company[] companies, int index) {
        return (index != -1) ? companies[index].getTickerSymbol() : "Unavailable";
    }
}