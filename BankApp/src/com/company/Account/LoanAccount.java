package com.company.Account;

import com.company.Currency.CurrencyType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Loan account. Inherits Account.
 */
public class LoanAccount extends Account{

    //LocalDate format: yyyy-mm-dd
    /**
     * Map that stores amounts due.
     * LocalDate is the lase time this loan has paid interest, Double is the amount of this loan.
     */
    private Map<LocalDate, Double> amountsDue;

    public LoanAccount(String accountId, String ownerName, String pwd, AccountType type) {
        super(accountId, ownerName, pwd, type);
        this.amountsDue = new HashMap<>();
    }

    public LoanAccount(String accountId, String ownerName, String pwd, AccountType type, Map<CurrencyType, Double> balance) {
        this(accountId, ownerName, pwd, type);
        setAllBalance(balance);
        this.amountsDue = new HashMap<>();
    }

    /**
     * Gets a loan from the bank
     *
     * @param date Date the loan is granted
     * @param amount Amount of the loan
     */
    public void getLoan(LocalDate date, Double amount) {
        amountsDue.put(date, amountsDue.getOrDefault(date, 0.0) + amount);
    }

    //Date should exist and amount should not exceed the total amount

    /**
     * Pays a loan of a specific date
     * @param date Date of the loan
     * @param amount Amount paid
     */
    public void payLoan(LocalDate date, Double amount) {
        amountsDue.put(date, amountsDue.get(date) - amount);
        if (amountsDue.get(date) == 0) {
            amountsDue.remove(date);
        }
    }

    /**
     * Get the dates of all unpaid loans
     *
     * @return Set of the dates
     */
    public Set<LocalDate> getAmountDueDates() {
        return amountsDue.keySet();
    }

    /**
     * Gets amounts due info of this account
     *
     * @return Amounts due info of all loans
     */
    public Map<LocalDate, Double> getAmountsDue() {
        return amountsDue;
    }

    /**
     * Replaces amounts due with a new map
     *
     * @param amountsDue Map from date to amount
     */
    public void setAmountsDue(Map<LocalDate, Double> amountsDue) {
        this.amountsDue = amountsDue;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append(" ,");
        for (Map.Entry<LocalDate, Double> ent : amountsDue.entrySet()) {
            stringBuilder.append(ent.getKey().toString()).append(" ").append(ent.getValue()).append(" ");
        }
        stringBuilder.append(" ,");
        //stringBuilder.delete(stringBuilder.length() - (amountsDue.isEmpty() ? 0 : 1), stringBuilder.length());
        return stringBuilder.toString();
    }

    public boolean haveLoan() {
        return !amountsDue.isEmpty();
    }
}
