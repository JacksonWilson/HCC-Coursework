package labs.lab16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utils.KeyboardReader;

public class Driver {

    public static void main(String[] args) {

        try (KeyboardReader keyReader = new KeyboardReader(System.in)) {
            Company[] companies = getCompanies("Input_Stock_Tickers.txt");
            CompanyMaintainer.sort(companies);

            String name = null;
            String exchange = null;
            do {
                name = keyReader.readLine("Enter company (or !q to stop): ", false);
                if (name.equalsIgnoreCase("!q"))
                    break;

                exchange = keyReader.readLine("Enter exchange (or !q to stop): ", false);
                if (exchange.equalsIgnoreCase("!q"))
                    break;

                Company searchValue = new Company(null, exchange, name);

                System.out.println("The matching symbol from linear search is: "
                    + CompanyMaintainer.linearSearch(companies, searchValue));
                System.out.println("The matching symbol from binary search is: "
                    + CompanyMaintainer.binarySearch(companies, searchValue));
                System.out.println("The matching symbol from ternary search is: "
                    + CompanyMaintainer.ternarySearch(companies, searchValue));
                System.out.println("The matching symbol from jump search is: "
                    + CompanyMaintainer.jumpSearch(companies, searchValue));
                System.out.println("The matching symbol from interpolation search is: "
                    + CompanyMaintainer.interpolationSearch(companies, searchValue));
                System.out.println("The matching symbol from exponential search is: "
                    + CompanyMaintainer.exponentialSearch(companies, searchValue));
                System.out.println("The matching symbol from fibonacci search is: "
                    + CompanyMaintainer.fibonacciSearch(companies, searchValue));
                System.out.println();
            } while (true);
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    private static Company[] getCompanies(String fileName) throws IOException {
        Company[] companies = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int numCompanies = Integer.parseInt(br.readLine());
            companies = new Company[numCompanies];

            for (int i = 0; i < numCompanies; i++) {
                String[] split = br.readLine().split(",");
                companies[i] = new Company(split[0], split[1], split[2]);
            }
        }

        return companies;
    }
}