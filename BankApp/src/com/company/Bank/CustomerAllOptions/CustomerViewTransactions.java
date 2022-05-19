package com.company.Bank.CustomerAllOptions;

import com.company.Currency.Currency;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.Transaction;
import com.company.Utils.Parser;

import java.io.IOException;
import java.util.List;

/**
 * Class responsible for allowing customers to view all their transactions
 */
public class CustomerViewTransactions {

    /**
     * Method responsible for allowing customers to view all their transactions
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Customer customer, Currency currency, StockMarket stockMarket) throws IOException {

        Parser parser = new Parser();
        List<Transaction> transactions = parser.parseTxnByPersonId(customer.getId());
        int count = 1;
        System.out.println();
        System.out.println("********************************************************************************************");
        System.out.println("        Transaction ID      Date       Amount      Customer ID      Type ");
        for (Transaction transaction : transactions){
            System.out.println("<" + count + ">" + "    " + transaction.getId() + "   " + transaction.getTimestamp() + "    " + transaction.getAmount() + "    " + transaction.getPersonId() + "    " + transaction.getType());
            count += 1;
        }
        System.out.println("********************************************************************************************");

        CustomerOptions.options(customer, currency, stockMarket);
    }
}
