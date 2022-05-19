package com.company.Account;

import com.company.Currency.CurrencyType;

import java.util.Map;

/**
 * Admin account possessed only by managers. Inherits Account.
 */
public class AdminAccount extends Account{

    public AdminAccount(String accountId, String ownerName, String pwd, AccountType type) {
        super(accountId, ownerName, pwd, type);
    }

    public AdminAccount(String accountId, String ownerName, String pwd, AccountType type, Map<CurrencyType, Double> balance) {
        this(accountId, ownerName, pwd, type);
        setAllBalance(balance);
    }

}
