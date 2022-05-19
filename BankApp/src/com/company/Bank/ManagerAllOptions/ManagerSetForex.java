package com.company.Bank.ManagerAllOptions;

import com.company.Currency.Currency;
import com.company.Persons.Manager;
import com.company.Stock.StockMarket;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class responsible for allowing managers to modify Forex
 */
public class ManagerSetForex {

    /**
     * Method responsible for allowing managers to modify Forex
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println();
        currency.display();

        System.out.println();
        System.out.println("Enter the currency you want to update : ");
        System.out.println("<1> EUR");
        System.out.println("<2> CAD");
        System.out.println("<3> JPY ");

        String choice = input.next();

        while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")){

            System.out.println();
            System.out.println("You've entered an incorrect input !");
            System.out.println("Enter the currency you want to update : ");
            System.out.println("<1> EUR");
            System.out.println("<2> CAD");
            System.out.println("<3> JPY ");
            choice = input.next();

        }

        System.out.println();
        System.out.println("Enter new value :");
        double value = input.nextDouble();

        if(choice.equals("1")){currency.setForex("EUR", value);}
        else if(choice.equals("2")){currency.setForex("CAD",  value);}
        else if(choice.equals("3")){currency.setForex("JPY", value);}

        currency.display();
        ManagerOptions.options(manager, currency, stockMarket);
    }
}
