package com.company.Persons;

import com.company.Account.*;
import com.company.Exceptions.AccountAlreadyExistException;
import com.company.Exceptions.AccountNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * General person class.
 */
public class Person {

    private String id;

    private String name;

    private String pwd;

    /**
     * All kinds of accounts a person has.
     */
    private Map<AccountType, List<Account>> accounts;

    /**
     * Constructor of a person.
     *
     * @param id ID of the person
     * @param name Name of ther person
     * @param pwd Password of the person
     * @param accounts Accounts the person has
     */
    public Person(String id, String name, String pwd, Map<AccountType, List<Account>> accounts) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.accounts = accounts;
    }

    /**
     * Get person ID.
     *
     * @return Person ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set person ID.
     *
     * @param id Person ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get person name.
     *
     * @return Person name
     */
    public String getName() {
        return name;
    }

    /**
     * Set person name.
     *
     * @param name Person name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get password.
     *
     * @return Password
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Set password.
     *
     * @param pwd Password
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Check if the password is correct.
     *
     * @param inputPwd Input password
     * @return If the password is correct
     */
    public boolean isCorrectPwd(String inputPwd) {
        return pwd.equals(inputPwd);
    }

    /**
     * Get account by account ID.
     *
     * @param type Type of the account
     * @param accountId ID of the account
     * @return Account instance
     * @throws AccountNotExistException Does not have that account
     */
    public Account getAccountById(AccountType type, String accountId) throws AccountNotExistException {
        List<Account> accountList = getAccountsByType(type);
        for (Account account : accountList) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        throw new AccountNotExistException();
    }

    /**
     * Get all accounts of a given type.
     *
     * @param type Type of accounts
     * @return A list of accounts of the type given
     * @throws AccountNotExistException Does not have that kind of account
     */
    public List<Account> getAccountsByType(AccountType type) throws AccountNotExistException {
        List<Account> accountList = accounts.getOrDefault(type, new ArrayList<>());
        if (accountList.size() == 0) {
            throw new AccountNotExistException();
        }
        return accountList;
    }

    /**
     * Get all the accounts a person has.
     *
     * @return All the accounts of all types of the person
     */
    public Map<AccountType, List<Account>> getAllAccounts() {
        return accounts;
    }

    /**
     * Set all accounts of a person.
     *
     * @param accounts Accounts of all types
     */
    public void setAccounts(Map<AccountType, List<Account>> accounts) {
        this.accounts = accounts;
    }

    /**
     * Add a certain type of account to the accounts.
     *
     * @param account Account instance
     * @throws AccountAlreadyExistException
     */
    public void addAccount(Account account) throws AccountAlreadyExistException {
        List<Account> accountList = accounts.getOrDefault(account.getTYPE(), new ArrayList<>());
        accountList.add(account);
        accounts.put(account.getTYPE(), accountList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",");
        stringBuilder.append(name).append(",");
        stringBuilder.append(pwd).append(",");
        for (AccountType type : AccountType.values()) {
            appendAccExistence(type, stringBuilder);
        }
        return stringBuilder.toString();
    }

    /**
     * Append account existence info (T/F) to person info string
     *
     * @param type
     * @param stringBuilder
     */
    private void appendAccExistence(AccountType type, StringBuilder stringBuilder) {
        stringBuilder.append(accounts.containsKey(type) ? "T" : "F").append(",");
    }

    /**
     * Delete the account from accounts
     *
     * @param type Account type
     * @param accId Account TD
     */
    public void deleteAccount(AccountType type, String accId) {
        if (!accounts.containsKey(type)) {
            return;
        }
        List<Account> accountList = accounts.get(type);
        int deleteIdx = -1;
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getAccountId().equals(accId)) {
                deleteIdx = i;
                break;
            }
        }
        if (deleteIdx >= 0) {
            accountList.remove(deleteIdx);
            if (accountList.isEmpty()) {
                accounts.remove(type);
            }
            else {
                accounts.put(type, accountList);
            }
        }
    }
}
