package com.company.Bank;


import com.company.Account.LoanAccount;
import com.company.Bank.CustomerAllOptions.*;
import com.company.Bank.ManagerAllOptions.ManagerOptions;
import com.company.Currency.Currency;
import com.company.Factories.AccountFactory;
import com.company.Stock.StockMarket;
import com.company.Utils.Printer;
import com.company.Utils.Writer;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Bank class which displays and welcome message and identifies type of person. Also, computes forex and loan interests each time.
 */

public class BankATM {

    /**
     * Identifies type of person and directs to appropriate page
     * @throws IOException to ensure proper file parsing
     */
    public static void run() throws IOException {

        com.company.Currency.Currency currency = new Currency();
        StockMarket stockMarket = StockMarket.getInstance();
        Writer writer = new Writer();

        AccountFactory accountFactory = new AccountFactory();
        LocalDate localDate = LocalDate.now();
        List<LoanAccount> overDueAccounts = accountFactory.produceOverdueAccounts(localDate);
        for(LoanAccount account : overDueAccounts){
            Map<LocalDate, Double> map = account.getAmountsDue();
            Set<LocalDate> dates = map.keySet();
            for(LocalDate date : dates){
                if (date.isBefore(localDate.minusMonths(1))){
                    Double value = map.get(date);
                    value += (value * (0.15));
                    map.remove(date);
                    map.put(localDate, value);
                }
            }
            account.setAmountsDue(map);
            writer.updateAccountToDisk(account);
        }

        Scanner in = new Scanner(System.in);

        Printer.printWelcomeMessage();

        System.out.println("Please choose an option :");
        System.out.println("I'm a  <1> Customer ");
        System.out.println("       <2> Manager");
        System.out.println("       <3> New Customer");
        System.out.println("       <q> Quit");
        String choice = in.next();

        while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("q")){
            System.out.println("You've entered an incorrect option !");
            System.out.println("Please choose again :");
            System.out.println("I'm a  <1> Customer ");
            System.out.println("       <2> Manager");
            System.out.println("       <3> New Customer");
            System.out.println("       <q> Quit");
            choice = in.next();
        }

        if(choice.equals("1")){
            CustomerOptions.run(currency, stockMarket);
        }

        else if(choice.equals("2")){
            ManagerOptions.run(currency, stockMarket);
        }

        else if(choice.equals("3")){
            CreateCustomer.run(currency, stockMarket);
        }

        else{
            Printer.printExitMessage();
        }
    }
}
