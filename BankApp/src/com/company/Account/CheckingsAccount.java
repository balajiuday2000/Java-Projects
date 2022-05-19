package com.company.Account;

import com.company.Currency.CurrencyType;

import java.util.Map;

/**
 * Checkings Account. Inherits Account.
 */
public class CheckingsAccount extends Account{

    public CheckingsAccount(String accountId, String ownerName, String pwd, AccountType type) {
        super(accountId, ownerName, pwd, type);
    }

    public CheckingsAccount(String accountId, String ownerName, String pwd, AccountType type, Map<CurrencyType, Double> balance) {
        this(accountId, ownerName, pwd, type);
        setAllBalance(balance);
    }

}
