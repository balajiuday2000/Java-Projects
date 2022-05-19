package com.company.Bank.ManagerAllOptions;
import com.company.Account.AccountType;
import com.company.Currency.Currency;
import com.company.Factories.PersonFactory;
import com.company.Persons.Manager;
import com.company.Stock.StockMarket;
import com.company.Utils.Writer;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Class responsible for creating new managers
 */
public class CreateManager {

    /**
     * Method responsible for creating new managers
     * @param manager Holds info about current manager
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Enter name of Account holder : ");
        String name = input.next();
        System.out.println("Enter password : ");
        String password = input.next();

        String managerId = getRandomNumberString();

        PersonFactory personFactory = new PersonFactory();
        Manager newManager = personFactory.produceNewManager(managerId, name, password, manager.getAccountsByType(AccountType.ADMIN));
        Writer writer = new Writer();
        writer.writeNewPerson(newManager, false);

        ManagerOptions.options(manager, currency, stockMarket);
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
