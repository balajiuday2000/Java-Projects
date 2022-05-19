package com.company.Bank.ManagerAllOptions;

import com.company.Currency.Currency;
import com.company.Factories.StockFactory;
import com.company.Persons.Manager;
import com.company.Stock.Stock;
import com.company.Stock.StockMarket;
import com.company.Utils.Parser;
import com.company.Utils.Writer;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * Class responsible for allowing managers ot modify stocks
 */
public class ManagerModifyStock {

    /**
     * Method responsible for allowing managers ot modify stocks
     * @param manager Holds info about current manager
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException {

        Parser parser = new Parser();
        Scanner input = new Scanner(System.in);
        Writer writer = new Writer();
        StockFactory stockFactory = new StockFactory();

        Map<String, Double> allStocks = parser.parseAllStockInfo();
        System.out.println();
        System.out.println("STOCK MARKET : ");
        System.out.println("********************************************************************************************");
        System.out.println("    Stock        Price ");
        int count = 1;
        for (Map.Entry<String, Double> entry : allStocks.entrySet()){
            System.out.println("<" + count + "> " + entry.getKey() + "         " + entry.getValue());
            count += 1;
        }
        System.out.println("********************************************************************************************");
        System.out.println();

        System.out.println("What do you want to do : ");
        System.out.println("<1> Update price");
        System.out.println("<2> Delete stock");
        System.out.println("<3> Add Stock");
        System.out.println("<4> See Stocks");
        String choice = input.next();

        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")){
            System.out.println();
            System.out.println("You've entered an incorrect input");
            System.out.println("What do you want to do : ");
            System.out.println("<1> Update price");
            System.out.println("<2> Delete stock");
            System.out.println("<3> Add Stock");
            System.out.println("<4> Set Stocks");
            choice = input.next();
        }

        if(choice.equals("1")){

            System.out.println("Enter Name of Stock :");
            String name = input.next();
            System.out.println("Enter New Price : ");
            Double price = input.nextDouble();

            Stock stock = stockFactory.produceSingleStock(name, price);
            writer.updateStock(stock, false);
        }

        else if(choice.equals("2")){

            System.out.println("Enter Name of Stock :");
            String name = input.next();

            Stock stock = stockFactory.produceSingleStock(name, 0.0);
            writer.updateStock(stock, true);
        }

        else if(choice.equals("3")){

            System.out.println("Enter Name of Stock :");
            String name = input.next();
            System.out.println("Enter Price : ");
            Double price = input.nextDouble();

            Stock stock = stockFactory.produceSingleStock(name, price);
            writer.updateStock(stock, false);
        }

        else if(choice.equals("4")){

            System.out.println();
            System.out.println("STOCK MARKET : ");
            System.out.println("********************************************************************************************");
            System.out.println("    Stock        Price ");
            count = 1;
            for (Map.Entry<String, Double> entry : allStocks.entrySet()){
                System.out.println("<" + count + "> " + entry.getKey() + "         " + entry.getValue());
                count += 1;
            }
            System.out.println("********************************************************************************************");
            System.out.println();

        }

        stockMarket.refresh();
        ManagerOptions.options(manager, currency, stockMarket);
    }
}
