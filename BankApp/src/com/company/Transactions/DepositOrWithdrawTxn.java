package com.company.Transactions;

import java.time.LocalDate;
import java.util.Date;

/**
 * Class for Deposit/Withdraw transactions
 */
public class DepositOrWithdrawTxn extends Transaction{

    //Amount is positive if deposit, negative if withdraw
    public DepositOrWithdrawTxn(String id, LocalDate timestamp, double amount, String personId) {
        super(id, timestamp, amount, personId, TxnType.DEPOSIT_OR_WITHDRAW);
    }
}
