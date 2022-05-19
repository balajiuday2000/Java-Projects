package com.company.Transactions;

import java.time.LocalDate;

/**
 * Transfer transactions
 */
public class TransferTxn extends Transaction{

    private String fromAccId;

    private String toPersonId;

    private String toAccId;

    /**
     * Constructor for Transfer transactions.
     *
     * @param id Transaction ID
     * @param timestamp Date of transfer
     * @param amount Amount of transfer
     * @param personId ID of Person that initiated the transfer
     * @param fromAccId ID of the account where money is transferred from
     * @param toPersonId ID of the person where money is transferred to
     * @param toAccId ID of the account where money is transferred to
     */
    public TransferTxn(String id, LocalDate timestamp, double amount, String personId, String fromAccId,
                       String toPersonId, String toAccId) {
        super(id, timestamp, amount, personId, TxnType.TRANSFER);
        setFromAccId(fromAccId);
        setToPersonId(toPersonId);
        setToPersonAccId(toAccId);
    }

    /**
     * Gets ID of the account where money is transferred from.
     *
     * @return ID of the account where money is transferred from
     */
    public String getFromAccId() {
        return fromAccId;
    }

    /**
     * Sets ID of the account where money is transferred from.
     *
     * @param fromAccId ID of the account where money is transferred from
     */
    public void setFromAccId(String fromAccId) {
        this.fromAccId = fromAccId;
    }

    /**
     * Gets ID of the person where money is transferred to.
     *
     * @return ID of the person where money is transferred to
     */
    public String getToPersonId() {
        return toPersonId;
    }

    /**
     * Sets ID of the person where money is transferred to.
     *
     * @param toPersonId ID of the person where money is transferred to
     */
    public void setToPersonId(String toPersonId) {
        this.toPersonId = toPersonId;
    }

    /**
     * Gets ID of the account where money is transferred to.
     *
     * @return ID of the account where money is transferred to
     */
    public String getToAccId() {
        return toAccId;
    }

    /**
     * Sets ID of the account where money is transferred to.
     *
     * @param toAccId ID of the account where money is transferred to
     */
    public void setToPersonAccId(String toAccId) {
        this.toAccId = toAccId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append(",");
        stringBuilder.append(fromAccId).append(",");
        stringBuilder.append(toPersonId).append(",");
        stringBuilder.append(toAccId);
        return stringBuilder.toString();
    }

}
