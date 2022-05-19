package com.company.Utils;

import com.company.Account.LoanAccount;
import com.company.Factories.TxnFactory;
import com.company.Transactions.Transaction;
import com.company.Utils.FilePaths;
import com.company.Account.AccountType;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static com.company.Utils.FilePaths.*;

import com.opencsv.CSVReader;

/**
 * Parsing util class.
 */
public class Parser {

    private Scanner input;

    /**
     * Parses foreign currency exchange information.
     *
     * @return A map from currency name to exchange rate
     * @throws IOException If file does not exist
     */
    public HashMap<String, Double> parseForex() throws IOException{

        HashMap<String, Double> foreignExchange = new HashMap<>();
        String fileName = System.getProperty("user.dir") + "/src/com/company/Files/" + "Forex.csv";
        input = new Scanner(new File(fileName));
        input.useDelimiter(",");
        while (input.hasNext()){
            foreignExchange.put(input.next(), Double.parseDouble(input.next()));
        }

        return foreignExchange;

    }

    /**
     * Parses person information by ID.
     * @param id Person ID
     * @param isCustomer If the person is customer
     * @return An array of String containing information of that person, return empty array if person does not exist
     */
    public String[] parsePersonInfoById(String id, boolean isCustomer) {
        try {
            String personFilePath = isCustomer ? CUST_PATH : MANAGER_PATH;
            CSVReader personReader = new CSVReader(new FileReader(personFilePath), ',');
            List<String[]> personCsvBody = personReader.readAll();
            for (String[] personInfo: personCsvBody) {
                if (personInfo[0].equals(id)) {
                    return personInfo;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    //-1 if not exist, if exists, return person Id

    /**
     * Check if a person is present.
     *
     * @param name Name of the person
     * @param pwd Password of the person
     * @param isCustomer If the person is a customer
     * @return ID of the person, -1 if person does not exist
     */
    public int checkPresence(String name, String pwd, boolean isCustomer) {
        try {
            String personFilePath = isCustomer ? CUST_PATH : MANAGER_PATH;
            CSVReader personReader = new CSVReader(new FileReader(personFilePath), ',');
            List<String[]> personCsvBody = personReader.readAll();
            for (String[] personInfo: personCsvBody) {
                if (personInfo[1].equals(name) && personInfo[2].equals(pwd)) {
                    return Integer.parseInt(personInfo[0]);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Customer/Manager layout:id, name, pwd, if_have_save, if_have_check, if_have_stock, if_have_loan, if_have_admin ("T/F")

    /**
     * Parses the types of accounts a person has.
     *
     * @param name Name of the person
     * @param pwd Password of the person
     * @param isCustomer If the person is a customer
     * @return A map from AccountType to Boolean indicating if a person has a certain type of account
     */
    public Map<AccountType, Boolean> parsePersonAccountExistence(String name, String pwd, boolean isCustomer) {
        Map<AccountType, Boolean> accountExistMap = new HashMap<>();
        try {
            String personFilePath = isCustomer ? CUST_PATH : MANAGER_PATH;
            CSVReader personReader = new CSVReader(new FileReader(personFilePath), ',');
            List<String[]> personCsvBody = personReader.readAll();
            for (String[] personInfo: personCsvBody) {
                if (personInfo[1].equals(name) && personInfo[2].equals(pwd)) {
                    for (int i = 3; i < personInfo.length; i++) {
                        AccountType type = AccountType.values()[i - 3];
                        if (personInfo[i].equals("T")) {
                            accountExistMap.put(type, true);
                        }
                        else {
                            accountExistMap.put(type, false);
                        }
                    }
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return accountExistMap;

    }

    //Account record layout (general): acc_id, name, pwd, accountType, "currency_1, balance, currency2, ....."

    /**
     * Parses a type of accounts by a person's name and password
     *
     * @param type Type of the account
     * @param name Name of the person
     * @param pwd Password of the person
     * @return A list of information of the accounts of the given type the person has
     */
    public List<String[]> parseAccounts(AccountType type, String name, String pwd) {
        List<String[]> accountsInfo = new ArrayList<>();
        try {
            String filePath = FilePaths.getPathByAccountType(type);
            CSVReader accReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> accCsvBody = accReader.readAll();
            if (type == AccountType.ADMIN) {
                return accCsvBody;
            }
            for (String[] accountInfo: accCsvBody) {
                if (accountInfo[1].equals(name) && accountInfo[2].equals(pwd)) {
                    accountsInfo.add(accountInfo);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return accountsInfo;
    }

    /**
     * Parses information of all stocks available in market.
     *
     * @return A map from stock name to price
     */
    public Map<String, Double> parseAllStockInfo() {
        Map<String, Double> stockInfo = new HashMap<>();
        try {
            String filePath = STOCK_PATH;
            CSVReader stockReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> stockCsvBody = stockReader.readAll();
            for (String[] info: stockCsvBody) {
                stockInfo.put(info[0], Double.parseDouble(info[1]));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stockInfo;
    }

    /**
     * Parses the transactions of a given person.
     *
     * @param personId ID of the person
     * @return A list of transactions
     */
    public List<Transaction> parseTxnByPersonId(String personId) {
        List<Transaction> txnList = new ArrayList<>();
        try {
            String filePath = TXN_FILE_PATH;
            CSVReader txnReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> txnCsvBody = txnReader.readAll();
            TxnFactory txnFactory = new TxnFactory();
            for (String[] info: txnCsvBody) {
                if (info[3].equals(personId)) {
                    txnList.add(txnFactory.produceTxn(info));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return txnList;
    }

    /**
     * Parse all transactions at a specific date.
     *
     * @param date A given date
     * @return A list of transactions
     */
    public List<Transaction> parseTxnByDate(LocalDate date) {
        List<Transaction> txnList = new ArrayList<>();
        try {
            String filePath = TXN_FILE_PATH;
            CSVReader txnReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> txnCsvBody = txnReader.readAll();
            TxnFactory txnFactory = new TxnFactory();
            for (String[] info: txnCsvBody) {
                if (info[1].equals(date.toString())) {
                    txnList.add(txnFactory.produceTxn(info));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return txnList;
    }

    /**
     * Parses all transactions.
     *
     * @return A list of transactions
     */
    public List<Transaction> parseAllTxns() {
        List<Transaction> txnList = new ArrayList<>();
        try {
            String filePath = TXN_FILE_PATH;
            CSVReader txnReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> txnCsvBody = txnReader.readAll();
            TxnFactory txnFactory = new TxnFactory();
            for (String[] info: txnCsvBody) {
                txnList.add(txnFactory.produceTxn(info));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return txnList;
    }

    /**
     * Parse all the overdue accounts on a given date.
     *
     * @param date A specific date
     * @return A list of string arrays containing account info
     */
    public List<String[]> parseOverdueLoanAccounts(LocalDate date){
        String filePath = FilePaths.getPathByAccountType(AccountType.LOAN);
        List<String[]> overdueAccList = new ArrayList<>();
        try {
            CSVReader loanAccReader = new CSVReader(new FileReader(filePath), ',');
            List<String[]> loanAccBody = loanAccReader.readAll();
            for (String[] accInfo : loanAccBody) {
                String[] loans = accInfo[5].split(" ");
                if (loans.length >= 2) {
                    for (int i = 0; i < loans.length; i+=2) {
                        String dateStr = loans[i];
                        LocalDate localDate = LocalDate.parse(dateStr);
                        if (localDate.isBefore(date.minusMonths(1))) {
                            overdueAccList.add(accInfo);
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return overdueAccList;
    }

    /**
     * Parses accounts by type.
     *
     * @param type Type of account
     * @return A list of string arrays containing account info
     */
    public List<String[]> parseAccountsByType(AccountType type){
        String filePath = FilePaths.getPathByAccountType(type);
        try {
            CSVReader accReader = new CSVReader(new FileReader(filePath), ',');
            return accReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Get transactions after last time he got a report.
     *
     * @return List of transactions during the time period
     * @throws FileNotFoundException File not exist
     */
    public List<Transaction> parseCumulativeTxn() throws FileNotFoundException {
        String filePath = LAST_REPORT_DATE_PATH;
        input = new Scanner(new File(filePath));
        List<Transaction> transactionList = new ArrayList<>();
        if (input.hasNext()) {
            String dateStr = input.next();
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate today = LocalDate.now();
            while (date.isBefore(today)) {
                transactionList.addAll(parseTxnByDate(date));
                date = date.plusDays(1);
            }
            transactionList.addAll(parseTxnByDate(today));
            return transactionList;
        }
        else {
            return parseAllTxns();
        }
    }
}
