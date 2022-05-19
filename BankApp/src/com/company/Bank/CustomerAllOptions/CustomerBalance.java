package com.company.Bank.CustomerAllOptions;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;

import java.io.IOException;
import java.util.*;

/**
 * Class responsible for displaying the balances of all accounts held by the customer.
 */

public class CustomerBalance {

    /**
     * Method which displays the balances of all accounts held by the customer
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket  Holds Stock info for the day
     * @throws IOException to ensure proper file parsing
     */
    public static void run(Customer customer, Currency currency, StockMarket stockMarket) throws IOException {

        System.out.println();
        System.out.println("********************************************************************************************");
        System.out.println("           Account Id       Account Type   Currency    Balance ");
        int count = 1;
        Map<AccountType, List<Account>> accounts = customer.getAllAccounts();
        for (AccountType accountType : accounts.keySet()) {
            System.out.println(accountType);
        }
        for(AccountType accountType : accounts.keySet()){
            System.out.println(accountType);
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
        CustomerOptions.options(customer, currency, stockMarket);


    }
}
