package com.company.Bank.ManagerAllOptions;

import com.company.Currency.Currency;
import com.company.Persons.Manager;
import com.company.Stock.StockMarket;
import com.company.Transactions.Transaction;
import com.company.Utils.Parser;
import com.company.Utils.Writer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Class responsible for allowing managers to get transactions by date
 */
public class ManagerViewTransaction {

    /**
     * Method responsible for allowing managers to get transactions by date
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Manager manager, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);
        Parser parser = new Parser();
        Writer writer = new Writer();

        System.out.println();
        System.out.println("Enter date : ( YYYY/MM/DD ) ");
        String date1 = input.next();
        LocalDate date = LocalDate.parse(date1);
        List<Transaction> transactions = parser.parseTxnByDate(date);
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
