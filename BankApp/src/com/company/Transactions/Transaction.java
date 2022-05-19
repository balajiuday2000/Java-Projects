package com.company.Transactions;

import java.time.LocalDate;

/**
 * Class for general transactions
 */
public class Transaction {

    private String id;

    private LocalDate timestamp;

    private double amount;

    private String personId;

    private TxnType type;

    /**
     * Constructor of general transactions
     *
     * @param id Transaction ID
     * @param timestamp Date of transaction
     * @param amount Amount of transaction
     * @param personId ID of person that initiated the transaction
     * @param type Transaction type
     */
    public Transaction(String id, LocalDate timestamp, double amount, String personId, TxnType type) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.personId = personId;
        this.type = type;
    }

    /**
     * Get transaction ID.
     *
     * @return Transaction ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set transaction ID.
     *
     * @param id Transaction ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get timestamp.
     *
     * @return Timestamp
     */
    public LocalDate getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp.
     *
     * @param timestamp Timestamp
     */
    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get amount of transaction.
     *
     * @return Amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Set amount of transaction.
     *
     * @param amount Amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Get initiator ID.
     *
     * @return ID of initiator
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Set initiator ID.
     *
     * @param personId ID of initiator
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * Get type of transaction.
     *
     * @return Type of transaction
     */
    public TxnType getTxnType() {
        return type;
    }

    /**
     * Set type of transaction.
     *
     * @param type Type of transaction
     */
    public void setTxnType(TxnType type) {
        this.type = type;
    }

    /**
     * Get type of transaction.
     *
     * @return Type of transaction
     */
    public TxnType getType() {
        return type;
    }

    /**
     * Set type of transaction.
     *
     * @param type Type of transaction
     */
    public void setType(TxnType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getId()).append(",");
        stringBuilder.append(getTimestamp().toString()).append(",");
        stringBuilder.append(getAmount()).append(",");
        stringBuilder.append(getPersonId()).append(",");
        stringBuilder.append(getTxnType());
        return stringBuilder.toString();
    }
}
