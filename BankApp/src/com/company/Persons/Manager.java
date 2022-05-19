package com.company.Persons;

import com.company.Account.Account;
import com.company.Account.AccountType;

import java.util.List;
import java.util.Map;

/**
 * Class for managers
 */
public class Manager extends Person{

    public Manager(String id, String name, String pwd, Map<AccountType, List<Account>> accounts) {
        super(id, name, pwd, accounts);
    }

}
