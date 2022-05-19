package com.company.Bank.CustomerAllOptions;

import com.company.Currency.Currency;
import com.company.Factories.PersonFactory;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Utils.Writer;

import java.io.IOException;
import java.util.*;

/**
 * Class responsible for creating a profile for new customers
 */

public class CreateCustomer {

    /**
     * Method which gets profile info from new customers
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */

    public static void run(Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Enter name of Account holder : ");
        String name = input.next();
        System.out.println("Enter password : ");
        String password = input.next();

        String customerId = getRandomNumberString();

        PersonFactory personFactory = new PersonFactory();
        Customer customer = personFactory.produceNewCustomer(customerId, name, password);
        Writer writer = new Writer();
        writer.writeNewPerson(customer, true);

        CustomerOptions.options(customer, currency, stockMarket);
    }

    /**
     * Method responsible for generating random six-digit ID
     * @return Six digit ID
     */
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
