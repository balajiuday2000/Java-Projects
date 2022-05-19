package com.company.Bank.CustomerAllOptions;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Account.LoanAccount;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.AccountNotExistException;
import com.company.Factories.AccountFactory;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.LoanTxn;
import com.company.Transactions.Transaction;
import com.company.Utils.Writer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.company.Account.AccountType.LOAN;

/**
 * Class responsible for allowing customers to take and pay back loans
 */
public class CustomerLoan {

    /**
     * Method responsible for allowing customers to take and pay back loans
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     * @throws IOException Ensures proper file parsing
     */
    public static void run(Customer customer, Currency currency, StockMarket stockMarket) throws IOException {

        Scanner input = new Scanner(System.in);
        Writer writer = new Writer();

        System.out.println();
        System.out.println("What do you want to do ?");
        System.out.println("<1> Take a loan");
        System.out.println("<2> Pay back a loan");
        String choice1 = input.next();

        while(!choice1.equals("1") && !choice1.equals("2")){
            System.out.println("You've entered the wrong input !");
            System.out.println("What do you want to do ?");
            System.out.println("<1> Take a loan");
            System.out.println("<2> Pay back a loan");
            choice1 = input.next();
        }

        System.out.println("Enter the amount in USD : ");
        double value = input.nextDouble();


        try{
            List<Account> allLoanAccounts = customer.getAccountsByType(AccountType.LOAN);
        }
        catch (AccountNotExistException e){

            String accountNo = getRandomNumberString();
            String[] args = {accountNo, customer.getName(), customer.getPwd(), "LOAN", " ", " "};
            AccountFactory accountFactory = new AccountFactory();
            Account newAccount = accountFactory.produceAccount(args);
            customer.addAccount(newAccount);
            writer.grantNewAccount(customer, newAccount, true);
        }

        List<Account> allLoanAccounts = customer.getAccountsByType(AccountType.LOAN);
        List<Account> allCheckingsAccounts = new LinkedList<>();
        List<Account> allSavingsAccounts = new LinkedList<>() ;

        try{allCheckingsAccounts = customer.getAccountsByType(AccountType.CHECKINGS);}
        catch(AccountNotExistException e){}
        try{allSavingsAccounts = customer.getAccountsByType(AccountType.SAVINGS);}
        catch (AccountNotExistException e){}

        Map<LocalDate, Double> amountsDue = new HashMap<>();

        for(Account loanAccount : allLoanAccounts){
            amountsDue = ((LoanAccount) loanAccount).getAmountsDue();
        }



        if(choice1.equals("1")){

            for(Account loanAccount : allLoanAccounts){
                ((LoanAccount) loanAccount).getLoan(LocalDate.now(), value);
                writer.updateAccountToDisk(loanAccount);
                writer.writeTxn(recordTransaction(value, customer.getId()));
                amountsDue = ((LoanAccount) loanAccount).getAmountsDue();
            }

            System.out.println();
            System.out.println("Current Loans : ");
            System.out.println();
            System.out.println("********************************************************************************************");
            System.out.println("           Date            Balance");
            int count = 1;
            for (Map.Entry<LocalDate, Double> entry : amountsDue.entrySet()) {
                System.out.println("<" + count + ">" + "          " + entry.getKey() + "         " + entry.getValue());
                count += 1;
            }
            System.out.println("********************************************************************************************");

            System.out.println();
            System.out.println("********************************************************************************************");
            System.out.println("           Account Id      Currency      Balance");
            count = 1;
            for(Account account : allCheckingsAccounts){
                System.out.println("<" + count + "> " + "           " +  account.getAccountId());
                Map<CurrencyType, Double> map = account.getBalance();
                for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                    System.out.println("                    " + entry.getKey() + "        " + entry.getValue());
                }
                count += 1;
            }
            System.out.println("********************************************************************************************");

            System.out.println();
            System.out.println("Enter the account ID you want the amount to be deposited  :  ");
            String ID = input.next();

            for(Account account : allCheckingsAccounts){
                if(account.getAccountId().equals(ID)){
                    account.addToBalance(CurrencyType.USD, value);
                    writer.updateAccountToDisk(account);
                }
            }
        }

        else if (choice1.equals("2")){

            System.out.println();
            System.out.println("Current Loans : ");
            System.out.println();
            System.out.println("********************************************************************************************");
            System.out.println("           Date            Balance");
            int count = 1;
            for (Map.Entry<LocalDate, Double> entry : amountsDue.entrySet()) {
                System.out.println("<" + count + ">" + "          " + entry.getKey() + "         " + entry.getValue());
                count += 1;
            }
            System.out.println("********************************************************************************************");

            System.out.println();
            System.out.println("Enter date of the loan you want to pay back : (YYYY-MM-DD)");
            String date1 = input.next();
            LocalDate date = LocalDate.parse(date1);

            System.out.println("Enter the account you want to pay from : ");
            System.out.println("<1> Checkings");
            System.out.println("<2> Savings");
            String choice2 = input.next();

            while(!choice2.equals("1") && !choice2.equals("2")){
                System.out.println("You've entered the wrong input !");
                System.out.println("Enter the account you want to pay from : ");
                System.out.println("<1> Checkings");
                System.out.println("<2> Savings");
                choice2 = input.next();
            }

            if(choice2.equals("1")){

                System.out.println();
                System.out.println("********************************************************************************************");
                System.out.println("           Account Id      Currency      Balance");
                count = 1;
                for(Account account : allCheckingsAccounts){
                    System.out.println("<" + count + "> " + "           " +  account.getAccountId());
                    Map<CurrencyType, Double> map = account.getBalance();
                    for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                        System.out.println("                    " + entry.getKey() + "        " + entry.getValue());
                    }
                    count += 1;
                }
                System.out.println("********************************************************************************************");

                System.out.println();
                System.out.println("Enter the account ID you want to pay back from :  ");
                String ID = input.next();

                for(Account account : allCheckingsAccounts){
                    if(account.getAccountId().equals(ID)){
                        account.addToBalance(CurrencyType.USD, -value);
                        writer.updateAccountToDisk(account);
                    }
                }

                for(Account loanAccount : allLoanAccounts){
                    ((LoanAccount)loanAccount).payLoan(date, value);
                    writer.updateAccountToDisk(loanAccount);
                    writer.writeTxn(recordTransaction(-value, customer.getId()));
                }

                Account loanAccount = allLoanAccounts.get(0);
                if(((LoanAccount)loanAccount).getAmountsDue().isEmpty()){
                    customer.deleteAccount(LOAN, loanAccount.getAccountId());
                    writer.deleteAccount(LOAN, loanAccount.getAccountId(), customer);
                }
            }

            else if (choice2.equals("2")){

                System.out.println();
                System.out.println("********************************************************************************************");
                System.out.println("           Account Id      Currency      Balance");
                count = 1;
                for(Account account : allSavingsAccounts){
                    System.out.println("<" + count + "> " + "           " +  account.getAccountId());
                    Map<CurrencyType, Double> map = account.getBalance();
                    for (Map.Entry<CurrencyType, Double> entry : map.entrySet()){
                        System.out.println("                    " + entry.getKey() + "        " + entry.getValue());
                    }
                    count += 1;
                }
                System.out.println("********************************************************************************************");

                System.out.println();
                System.out.println("Enter the account ID you want to pay back from :  ");
                String ID = input.next();

                for(Account account : allSavingsAccounts){
                    if(account.getAccountId().equals(ID)){
                        account.addToBalance(CurrencyType.USD, -value);
                        writer.updateAccountToDisk(account);
                    }
                }

                for(Account loanAccount : allLoanAccounts){
                    ((LoanAccount)loanAccount).payLoan(date, value);
                    writer.updateAccountToDisk(loanAccount);
                    writer.writeTxn(recordTransaction(-value, customer.getId()));
                }
            }

        }

        //writer.writeTxn(recordTransaction(value, customer.getId()));
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
        LoanTxn transaction = new LoanTxn(ID,date, amount, customerID);
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
