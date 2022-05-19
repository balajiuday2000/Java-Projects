package com.company.Persons;

import com.company.Account.Account;
import com.company.Account.AccountType;

import java.util.List;
import java.util.Map;

/**
 * Class for customers
 */
public class Customer extends Person{

    public Customer(String id, String name, String pwd, Map<AccountType, List<Account>> accounts) {
        super(id, name, pwd, accounts);
    }

}
