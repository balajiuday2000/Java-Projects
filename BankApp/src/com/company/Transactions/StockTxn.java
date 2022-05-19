package com.company.Transactions;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * Class of stock transactions
 */
public class StockTxn extends Transaction{

    /**
     * Amount of each stock purchased
     */
    private Map<String, Integer> stockAmountMap;

    //Amount is positive if buy,negative if sell

    /**
     * Constructor of stock transactions.
     *
     * @param id Transaction ID
     * @param timestamp Timestamp
     * @param amount Amount of money paid/get
     * @param personId Person ID
     * @param purchaseInfo Amount of each stock purchased
     */
    public StockTxn(String id, LocalDate timestamp, double amount, String personId, Map<String, Integer> purchaseInfo) {
        super(id, timestamp, amount, personId, TxnType.STOCK);
        setStockAmountMap(purchaseInfo);
    }

    /**
     * Get amount of each stock purchased.
     *
     * @return mount of each stock purchased
     */
    public Map<String, Integer> getStockAmountMap() {
        return stockAmountMap;
    }

    /**
     * Set amount of each stock purchased.
     *
     * @param stockAmountMap Amount of each stock purchased
     */
    public void setStockAmountMap(Map<String, Integer> stockAmountMap) {
        this.stockAmountMap = stockAmountMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append(",");
        for (Map.Entry<String, Integer> record : stockAmountMap.entrySet()) {
            stringBuilder.append(record.getKey()).append(",").append(record.getValue()).append(",");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        return stringBuilder.toString();
    }
}
