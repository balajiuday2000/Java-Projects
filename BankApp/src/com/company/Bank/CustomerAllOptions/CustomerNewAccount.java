package com.company.Bank.CustomerAllOptions;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.AccountNotExistException;
import com.company.Factories.AccountFactory;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Utils.Writer;

import java.io.IOException;
import java.util.*;

import static com.company.Account.AccountType.ADMIN;

/**
 * Class responsible for allowing customers to create new accounts
 */
public class CustomerNewAccount {

    /**
     * Method responsible for allowing customers to create new accounts
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Customer customer, Currency currency, StockMarket stockMarket) throws IOException {

        AccountFactory accountFactory = new AccountFactory();
        Writer writer = new Writer();
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Enter type of account to be opened : ");
        System.out.println("<1> Savings");
        System.out.println("<2> Checkings");
        System.out.println("<3> Stock");
        String choice1 = input.next();

        while(!choice1.equals("1") && !choice1.equals("2") && !choice1.equals("3")){

            System.out.println("You've entered an incorrect option !");
            System.out.println("Enter type of account to be opened : ");
            System.out.println("<1> Savings");
            System.out.println("<2> Checkings");
            System.out.println("<3> Stock");

            choice1 = input.next();
        }

        if(choice1.equals("3")){

            try{
                List<Account> allStockAccounts = customer.getAccountsByType(AccountType.STOCK);
            }
            catch (AccountNotExistException e){

                String accountNo = getRandomNumberString();
                String[] args = {accountNo, customer.getName(), customer.getPwd(), "STOCK", " ", " "};
                Account newAccount = accountFactory.produceAccount(args);
                customer.addAccount(newAccount);
                writer.grantNewAccount(customer, newAccount, true);

            }
        }
        else{

            System.out.println();
            System.out.println("Enter the currency you want to deposit : ");
            System.out.println("<1> USD");
            System.out.println("<2> EUR");
            System.out.println("<3> CAD");
            System.out.println("<4> JPY");
            String choice2 = input.next();

            while(!choice2.equals("1") && !choice2.equals("2") && !choice2.equals("3") && !choice2.equals("4")){

                System.out.println("You've entered an incorrect option !");
                System.out.println("Enter the currency you want to deposit : ");
                System.out.println("<1> USD");
                System.out.println("<2> EUR");
                System.out.println("<3> CAD");
                System.out.println("<4> JPY");
                choice2 = input.next();
            }


            System.out.println("Enter the amount to be deposited : ");
            double value = input.nextDouble();
            value -= 5.0;
            String accountNo = getRandomNumberString();

            if(choice1.equals("1")){

                String values = null;
                if(choice2.equals("1")){values = "USD" + " " + value; feeToBank(CurrencyType.USD); }
                else if(choice2.equals("2")){values = "EUR" + " " + value; feeToBank(CurrencyType.EUR);}
                else if(choice2.equals("3")){values = "CAD" + " " + value; feeToBank(CurrencyType.CAD);}
                else if(choice2.equals("4")){values = "JPY" + " " + value; feeToBank(CurrencyType.JPY);}
                String[] args = {accountNo, customer.getName(), customer.getPwd(), "SAVINGS", values};
                Account newAccount = accountFactory.produceAccount(args);
                customer.addAccount(newAccount);
                writer.grantNewAccount(customer, newAccount, true);

            }

            else if(choice1.equals("2")){

                String values = null;
                if(choice2.equals("1")){values = "USD" + " " + value; feeToBank(CurrencyType.USD); }
                else if(choice2.equals("2")){values = "EUR" + " " + value; feeToBank(CurrencyType.EUR);}
                else if(choice2.equals("3")){values = "CAD" + " " + value; feeToBank(CurrencyType.CAD);}
                else if(choice2.equals("4")){values = "JPY" + " " + value; feeToBank(CurrencyType.JPY);}
                String[] args = {accountNo, customer.getName(), customer.getPwd(), "CHECKINGS", values};
                System.out.println(values);
                Account newAccount = accountFactory.produceAccount(args);
                customer.addAccount(newAccount);
                writer.grantNewAccount(customer, newAccount, true);
            }
        }

        CustomerBalance.run(customer, currency, stockMarket);
    }

    /**
     * Method responsible for extracting small fee from customer
     * @param currencyType Type of currency being deposited in new account
     * @throws IOException To ensure proper file parsing
     */
    public static void feeToBank(CurrencyType currencyType) throws IOException {

        AccountFactory accountFactory = new AccountFactory();
        List<Account> allAdminAccounts = accountFactory.produceAccountsByType(ADMIN);
        Account adminAccount = allAdminAccounts.get(0);
        if(currencyType.equals(CurrencyType.USD)){adminAccount.addToBalance(CurrencyType.USD, 5.00);}
        else if(currencyType.equals(CurrencyType.EUR)){adminAccount.addToBalance(CurrencyType.EUR, 5.00);}
        else if(currencyType.equals(CurrencyType.CAD)){adminAccount.addToBalance(CurrencyType.CAD, 5.00);}
        else if(currencyType.equals(CurrencyType.JPY)){adminAccount.addToBalance(CurrencyType.JPY, 5.00);}

        Writer writer = new Writer();
        writer.updateAccountToDisk(adminAccount);
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
