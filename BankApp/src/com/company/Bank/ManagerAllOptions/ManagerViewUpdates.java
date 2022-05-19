package com.company.Bank.ManagerAllOptions;

import com.company.Currency.Currency;
import com.company.Persons.Manager;
import com.company.Stock.StockMarket;
import com.company.Transactions.Transaction;
import com.company.Utils.Parser;
import com.company.Utils.Writer;

import java.io.IOException;
import java.util.List;

/**
 * Class responsible for allowing manager to view all transactions since last report
 */
public class ManagerViewUpdates {

    /**
     * Method responsible for allowing manager to view all transactions since last report
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException{

        Parser parser = new Parser();
        Writer writer = new Writer();
        List<Transaction> transactions = parser.parseCumulativeTxn();

        int count = 1;

        System.out.println();
        System.out.println("********************************************************************************************");
        System.out.println("        Transaction ID      Date       Amount      Customer ID      Type ");
        for (Transaction transaction : transactions){
            System.out.println("<" + count + ">" + "    " + transaction.getId() + "   " + transaction.getTimestamp() + "    " + transaction.getAmount() + "    " + transaction.getPersonId() + "    " + transaction.getType());
            count += 1;
        }
        System.out.println("********************************************************************************************");

        writer.writeCurTimeToReportDate();
        ManagerOptions.options(manager, currency, stockMarket);

    }
}
