package com.company.Transactions;

import com.company.Persons.Customer;

import java.time.LocalDate;

/**
 * Class for transactions of Admin accounts
 */
public class AdminTxn extends Transaction{

    //For AdminTxn, personId is the id of the customer, not manager, as all managers should be able to view the Txn
    public AdminTxn(String id, LocalDate timestamp, double amount, String personId) {
        super(id, timestamp, amount, personId, TxnType.ADMIN);
    }

}
