package com.company.Transactions;

import java.time.LocalDate;


/**
 * Class of Loan transactions.
 */
public class LoanTxn extends Transaction{

    //amount positive if get loan, negative if pay back

    /**
     * Constructor of loan accounts.
     *
     * @param id Transaction ID
     * @param timestamp Timestamp
     * @param amount Amount get/paid
     * @param personId ID of person get/paid loan
     */
    public LoanTxn(String id, LocalDate timestamp, double amount, String personId) {
        super(id, timestamp, amount, personId, TxnType.LOAN);
    }

}
