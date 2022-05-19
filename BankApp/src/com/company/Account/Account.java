package com.company.Account;

import com.company.Currency.CurrencyType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class of general accounts.
 */
public class Account {

    private String ownerName;

    private String accountId;

    private String pwd;

    /**
     * The balance in different currencies
     */
    private Map<CurrencyType, Double> balance;

    private AccountType TYPE;

    /**
     * Constructor of Account class
     *
     * @param accountId Unique ID of the account
     * @param ownerName Name of the owner
     * @param pwd Password of the owner
     * @param type Type of the account
     */
    public Account(String accountId, String ownerName, String pwd, AccountType type) {
        this.ownerName = ownerName;
        this.accountId = accountId;
        this.pwd = pwd;
        this.TYPE = type;
        this.balance = new HashMap<>();
    }

    /**
     * Constructor with initial balance.
     *
     * @param accountId Unique ID of the account
     * @param ownerName Name of the owner
     * @param pwd Password of the owner
     * @param type Type of the account
     * @param currency Currency of initial balance
     * @param balance Amount of initial balance
     */
    public Account(String accountId, String ownerName, String pwd, AccountType type, CurrencyType currency, double balance) {
        this.ownerName = ownerName;
        this.accountId = accountId;
        this.pwd = pwd;
        this.balance = new HashMap<>();
        this.balance.put(currency, balance);
        this.TYPE = type;
    }

    /**
     * Gets name of owner.
     *
     * @return Name of Owner
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Sets name of owner.
     *
     * @param ownerName Name of Owner
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Gets account ID.
     *
     * @return Account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets account ID.
     *
     * @param accountId Account ID
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets password of owner.
     *
     * @return Passwords
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets password of owner.
     *
     * @param pwd Password of owner
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Gets balance of all currencies.
     *
     * @return Map from currency to account
     */
    public Map<CurrencyType, Double> getBalance() {
        return balance;
    }

    /**
     * Gets the balance of a specific currency.
     *
     * @param currencies Type of currency
     * @return Balance of the input currency
     */
    public double getBalanceByCurrency(CurrencyType currencies) {
        return balance.get(currencies);
    }

    /**
     * Sets the balance of a given type
     *
     * @param currencyType Type of currency
     * @param balance Balance of that currency
     */
    public void setBalance(CurrencyType currencyType, double balance) {
        this.balance.put(currencyType, balance);
    }

    /**
     * Replaces the current balance map with the input map
     *
     * @param balance A map from currency to balance amount
     */
    public void setAllBalance(Map<CurrencyType, Double> balance) {
        this.balance = balance;
    }

    /**
     * Add balance amount to a specific currency type
     *
     * @param currencyType Type of currency
     * @param balance Balance to be added
     */
    public void addToBalance(CurrencyType currencyType, double balance) {
        this.balance.put(currencyType, this.balance.getOrDefault(currencyType, 0.0) + balance);
    }

    /**
     * Get the type of this account
     *
     * @return Account type
     */
    public AccountType getTYPE() {
        return TYPE;
    }

    /**
     * Sets the type of this account
     *
     * @param TYPE Account type
     */
    public void setTYPE(AccountType TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getAccountId() + "," +
                getOwnerName() + "," +
                getPwd() + "," +
                getTYPE() + ",");
        for (Map.Entry<CurrencyType, Double> ent : balance.entrySet()) {
            sb.append(ent.getKey().name()).append(" ").append(ent.getValue()).append(" ");
        }
        //sb.delete(sb.length() - (balance.isEmpty() ? 0 : 1), sb.length());
        return sb.toString();
    }
}
