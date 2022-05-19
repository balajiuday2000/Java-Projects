package com.company.Account;

/**
 * Savings Account. Inherits Account.
 */
public class SavingsAccount extends Account{

    public SavingsAccount(String accountId, String ownerName, String pwd, AccountType type) {
        super(accountId, ownerName, pwd, type);
    }

}
