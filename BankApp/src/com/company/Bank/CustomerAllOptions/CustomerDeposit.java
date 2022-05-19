package com.company.Bank.CustomerAllOptions;

import com.company.Account.Account;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.DepositOrWithdrawTxn;
import com.company.Transactions.Transaction;
import com.company.Utils.Writer;


import java.io.IOException;

import java.time.LocalDate;
import java.util.*;


import static com.company.Account.AccountType.*;

/**
 * Class responsible for allowing customers to deposit into their accounts
 */
public class CustomerDeposit {

    /**
     * Method responsible for allowing customers to deposit into their accounts
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Customer customer, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);
        Writer writer = new Writer();

        System.out.println();
        System.out.println("Please enter the currency you want to deposit : ");
        System.out.println("<1> USD");
        System.out.println("<2> EUR");
        System.out.println("<3> CAD");
        System.out.println("<4> JPY");
        String choice1 = input.next();

        if(!choice1.equals("1") && !choice1.equals("2") && !choice1.equals("3") && !choice1.equals("4")){

            System.out.println("You've entered the wrong input ! ");
            System.out.println();
            System.out.println("Please enter the currency you want to deposit : ");
            System.out.println("<1> USD");
            System.out.println("<2> EUR");
            System.out.println("<3> CAD");
            System.out.println("<4> JPY");
            choice1 = input.next();
        }

        System.out.println();
        System.out.println("Enter the amount you want to deposit : ");
        double value = input.nextInt();

        System.out.println();
        System.out.println("Please enter the account you want to deposit to :");
        System.out.println("<1> Savings");
        System.out.println("<2> Checkings");
        String choice2 = input.next();

        if(!choice2.equals("1") && !choice2.equals("2")){

            System.out.println("You've entered the wrong input ! ");
            System.out.println();
            System.out.println("Please enter the account you want to deposit to :");
            System.out.println("<1> Savings");
            System.out.println("<2> Checkings");
            choice2 = input.next();
        }


        if(choice2.equals("1")){

            List<Account> AllSavingsAccounts = customer.getAccountsByType(SAVINGS);
            System.out.println();
            System.out.println("********************************************************************************************");
            System.out.println("           Account Id      Currency      Balance");
            int count = 1;
            for(Account account : AllSavingsAccounts){
                System.out.println("<" + count + "> " + "           " +  account.getAccountId());
                Map<CurrencyType, Double> map = account.getBalance();
                for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                    System.out.println("                    " + entry.getKey() + "        " + entry.getValue());
                }
                count += 1;
            }
            System.out.println("********************************************************************************************");

            System.out.println();
            System.out.println("Enter the account ID :  ");
            String ID = input.next();

            for(Account account : AllSavingsAccounts){
                if(account.getAccountId().equals(ID)){
                    if(choice1.equals("1")){ account.addToBalance(CurrencyType.USD, value);}
                    else if(choice1.equals("2")){account.addToBalance(CurrencyType.EUR, value);}
                    else if(choice1.equals("3")){account.addToBalance(CurrencyType.CAD, value);}
                    else if(choice1.equals("4")){account.addToBalance(CurrencyType.JPY, value);}
                    writer.updateAccountToDisk(account);
                }
            }
        }

        else if(choice2.equals("2")){

            List<Account> AllCheckingsAccounts = customer.getAccountsByType(CHECKINGS);
            System.out.println();
            System.out.println("********************************************************************************************");
            System.out.println("           Account Id      Currency      Balance");
            int count = 1;
            for(Account account : AllCheckingsAccounts){
                System.out.println("<" + count + "> " + "           " +  account.getAccountId());
                Map<CurrencyType, Double> map = account.getBalance();
                for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                    System.out.println("                    " + entry.getKey() + "        " + entry.getValue());
                }
                count += 1;
            }
            System.out.println("********************************************************************************************");

            System.out.println();
            System.out.println("Enter the account ID :  ");
            String ID = input.next();

            for(Account account : AllCheckingsAccounts){
                if(account.getAccountId().equals(ID)){
                    if(choice1.equals("1")){ account.addToBalance(CurrencyType.USD, value);}
                    else if(choice1.equals("2")){account.addToBalance(CurrencyType.EUR, value);}
                    else if(choice1.equals("3")){account.addToBalance(CurrencyType.CAD, value);}
                    else if(choice1.equals("4")){account.addToBalance(CurrencyType.JPY, value);}
                    writer.updateAccountToDisk(account);
                }
            }
        }


        writer.writeTxn(recordTransaction(value, customer.getId()));
        CustomerBalance.run(customer, currency, stockMarket);
    }

    /**
     * Method responsible for recording transactions
     * @param amount Amount transacted
     * @param cusID Customer involved in transaction
     * @return Transaction info
     */
    public static Transaction recordTransaction(Double amount, String cusID){

        String ID = getRandomNumberString();
        String customerID = cusID;
        Double value = amount;
        LocalDate date = LocalDate.now();
        DepositOrWithdrawTxn transaction = new DepositOrWithdrawTxn(ID, date, value, customerID);

        return transaction;
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
