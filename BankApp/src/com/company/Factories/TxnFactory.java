package com.company.Factories;

import com.company.Transactions.*;
import com.company.Utils.Parser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory that produces transactions
 */
public class TxnFactory {

    private Parser parser;

    public TxnFactory() {
        this.parser = new Parser();
    }

    public Transaction produceTxn(String[] args) {
        String id = args[0];
        LocalDate timeStamp = getTimeStamp(args[1]);
        double amount = Double.parseDouble(args[2]);
        String personId = args[3];
        TxnType type = TxnType.valueOf(args[4]);
        switch (type) {
            case DEPOSIT_OR_WITHDRAW -> {
                return new DepositOrWithdrawTxn(id, timeStamp, amount, personId);
            }
            case TRANSFER -> {
                String fromAccId = args[5], toPersonId = args[6], toAccId = args[7];
                return new TransferTxn(id, timeStamp, amount, personId, fromAccId, toPersonId, toAccId);
            }
            case LOAN -> {
                return new LoanTxn(id, timeStamp, amount, personId);
            }
            case STOCK -> {
                Map<String, Integer> stockAmountMap = new HashMap<>();
                for (int i = 5; i < args.length; i+=2) {
                    stockAmountMap.put(args[i], Integer.parseInt(args[i + 1]));
                }
                return new StockTxn(id, timeStamp, amount, personId, stockAmountMap);
            }
            case ADMIN -> {
                return new AdminTxn(id, timeStamp, amount, personId);
            }
            default -> {
                return null;
            }
        }
    }

    //Format: yyyy-mm-dd
    private LocalDate getTimeStamp(String strFormatDate) {
        String[] args = strFormatDate.split("-");
        int year = Integer.parseInt(args[0]), month = Integer.parseInt(args[1]), date = Integer.parseInt(args[2]);
        return LocalDate.of(year, month, date);
    }

}
