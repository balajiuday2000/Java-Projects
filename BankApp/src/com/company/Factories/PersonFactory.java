package com.company.Factories;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Account.LoanAccount;
import com.company.Persons.Customer;
import com.company.Persons.Manager;
import com.company.Exceptions.PersonNotFoundException;
import com.company.Utils.Parser;

import java.time.LocalDate;
import java.util.*;

/**
 * Factory that produces person from file.
 */
public class PersonFactory{

    /**
     * Parsing utils {@link com.company.Utils.Parser}.
     */
    private Parser parser;

    /**
     * Account factory instance {@link com.company.Factories.AccountFactory}.
     */
    private AccountFactory accountFactory;

    public PersonFactory() {
        parser = new Parser();
        accountFactory = new AccountFactory();
    }

    /**
     * Produce customers from file.
     *
     * @param name Name of customer
     * @param pwd Password of customer
     * @return Customer instance
     * @throws PersonNotFoundException Person does not exist
     */
    public Customer produceCustomer(String name, String pwd) throws PersonNotFoundException {
        int id = parser.checkPresence(name, pwd, true);
        Map<AccountType, List<String[]>> accountInfo = getAccountInfo(name, pwd, true);
        Map<AccountType, List<Account>> accounts = accountFactory.produceAccountMap(accountInfo);
        return new Customer(String.valueOf(id), name, pwd, accounts);
    }

    /**
     * Produce customer instance given ID from file
     * @param id Customer ID
     * @return Customer instance
     * @throws PersonNotFoundException Customer does not exist
     */
    public Customer produceCustomerById(String id)  throws PersonNotFoundException {
        String[] customerInfo = parser.parsePersonInfoById(id, true);
        return produceCustomer(customerInfo[1], customerInfo[2]);
    }

    /**
     * Produce a new customer instance, not from file.
     *
     * @param id Customer ID
     * @param name Customer Name
     * @param pwd Customer password
     * @return Customer instance
     */
    public Customer produceNewCustomer(String id, String name, String pwd) {
        return new Customer(id, name, pwd, new HashMap<>());
    }

    /**
     * Produce Manager instance from file.
     *
     * @param name Manager name
     * @param pwd Manager password
     * @return Manager instance
     */
    public Manager produceManager(String name, String pwd) {
        int id = parser.checkPresence(name, pwd, false);
        Map<AccountType, List<String[]>> accountInfo = getAccountInfo(name, pwd, false);
        Map<AccountType, List<Account>> accounts = accountFactory.produceAccountMap(accountInfo);
        return new Manager(String.valueOf(id), name, pwd, accounts);
    }

    /**
     * Produce a new manager instance, not from file.
     *
     * @param id Manager ID
     * @param name Manager Name
     * @param pwd Manager Password
     * @param adminAccounts Admin accounts, all managers shares the same set of admin accounts
     * @return
     */
    public Manager produceNewManager(String id, String name, String pwd, List<Account> adminAccounts) {
        Map<AccountType, List<Account>> accountsMap = new HashMap<>();
        accountsMap.put(AccountType.ADMIN, adminAccounts);
        return new Manager(id, name, pwd, accountsMap);
    }

    /**
     * Get account information of a person from file.
     *
     * @param name Owner name
     * @param pwd Password
     * @param isCustomer If the person is a customer
     * @return Information of all accounts of different types
     */
    private Map<AccountType, List<String[]>> getAccountInfo(String name, String pwd, boolean isCustomer) {
        //int personId = parser.checkPresence(name, pwd, isCustomer);
        Map<AccountType, Boolean> accountExistenceMap = parser.parsePersonAccountExistence(name, pwd, isCustomer);
        Map<AccountType, List<String[]>> accountInfo = new HashMap<>();
        for (AccountType type : AccountType.values()) {
            if (accountExistenceMap.get(type)) {
                accountInfo.put(type, parser.parseAccounts(type, name, pwd));
            }
        }
        System.out.println(accountInfo.containsKey(AccountType.STOCK));
        return accountInfo;
    }

    /**
     * Get customers who have loans.
     *
     * @return List of customers with overdue accounts.
     */
    public List<Customer> getCustomersWithLoan() {
        List<Customer> customerList = new ArrayList<>();
        AccountFactory accountFactory = new AccountFactory();
        List<Account> accounts = accountFactory.produceAccountsByType(AccountType.LOAN);
        for (Account account : accounts) {
            if (((LoanAccount)account).haveLoan()) {
                customerList.add(produceCustomer(account.getOwnerName(), account.getPwd()));
            }
        }
        return customerList;
    }
}
