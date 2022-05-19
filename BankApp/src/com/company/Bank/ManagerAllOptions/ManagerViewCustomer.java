package com.company.Bank.ManagerAllOptions;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Factories.PersonFactory;
import com.company.Persons.Customer;
import com.company.Persons.Manager;
import com.company.Stock.StockMarket;

import java.io.IOException;
import java.util.*;

/**
 * Class responsible for allowing managers to check upon customers
 */
public class ManagerViewCustomer {

    /**
     * Method responsible for allowing managers to check upon customers
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);
        PersonFactory personFactory = new PersonFactory();

        System.out.println();
        System.out.println("Enter Customer ID :");
        String ID = input.next();

        Customer customer = personFactory.produceCustomerById(ID);
        System.out.println();
        System.out.println("********************************************************************************************");
        System.out.println("           Account Id       Account Type   Currency    Balance ");
        int count = 1;
        Map<AccountType, List<Account>> accounts = customer.getAllAccounts();
        for(AccountType accountType : accounts.keySet()){
            List<Account> accountsByType = customer.getAccountsByType(accountType);
            for(Account account : accountsByType){
                System.out.println("<" + count + "> " + "           " +  account.getAccountId() + "             " + accountType);
                Map<CurrencyType, Double> map = account.getBalance();
                for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                    System.out.println("                                             " + entry.getKey() + "         " + entry.getValue());
                }
                count += 1;
            }
        }
        System.out.println("********************************************************************************************");
        System.out.println();
        ManagerOptions.options(manager, currency, stockMarket);
    }
}
