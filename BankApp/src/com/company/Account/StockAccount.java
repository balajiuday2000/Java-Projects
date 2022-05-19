package com.company.Account;

import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.InadequateBalanceException;
import com.company.Exceptions.NotEnoughShareException;
import com.company.Exceptions.StockNotExistException;
import com.company.Stock.Stock;
import com.company.Stock.StockMarket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Stocks Account. Inherits Account.
 */
public class StockAccount extends Account{

    /**
     * The amount of each stock that is held by the customer.
     *
     * String -> Stock name, Integer -> amount
     */
    private Map<String, Integer> sharesHolding;

    /**
     * Instance of the stock market.
     */
    private StockMarket stockMarket;

    public StockAccount(String accountId, String ownerName, String pwd, AccountType type, Map<CurrencyType, Double> balance) {
        this(accountId, ownerName, pwd, type);
        setAllBalance(balance);
        sharesHolding = new HashMap<>();
        stockMarket = StockMarket.getInstance();
    }

    public StockAccount(String accountId, String ownerName, String pwd, AccountType type) {
        super(accountId, ownerName, pwd, type);
        sharesHolding = new HashMap<>();
        stockMarket = StockMarket.getInstance();
    }

    /**
     * Buys a share.
     *
     * @param corpName Name of the stock
     * @param amount Amount of shares bought
     * @param currencyType Currency paid with
     * @throws StockNotExistException If the stock does not exist
     * @throws InadequateBalanceException If inadequate balance in this account
     * @throws IOException Data file not exist
     */
    public void buyShare(String corpName, int amount, CurrencyType currencyType) throws StockNotExistException, InadequateBalanceException, IOException {
        Currency currency = new Currency();
        Stock stock = stockMarket.getStockByName(corpName);
        double cost = stock.getPrice() * amount;
        double currentBalance = getBalanceByCurrency(currencyType) * currency.getForex(currencyType.name());
        if (currentBalance < cost) {
            throw new InadequateBalanceException();
        }
        //stockLookUpMap.putIfAbsent(corpName, stock);
        sharesHolding.put(corpName, sharesHolding.getOrDefault(corpName, 0) + amount);
        addToBalance(currencyType, -(cost / currency.getForex(currencyType.name())));
    }

    /**
     * Sells a stock.
     *
     * @param corpName Stock name
     * @param amount Amount of shares sold
     * @throws StockNotExistException Does not have any shares of that stock
     * @throws NotEnoughShareException Attempting to sell more shares than possessed
     */
    public void sellShare(String corpName, int amount)  throws StockNotExistException, NotEnoughShareException {
        if (!sharesHolding.containsKey(corpName)) {
            throw new StockNotExistException();
        }
        Stock stock = stockMarket.getStockByName(corpName);
        int holdingAmount = sharesHolding.get(corpName);
        if (holdingAmount < amount) {
            throw new NotEnoughShareException();
        }
        addToBalance(CurrencyType.USD,getBalanceByCurrency(CurrencyType.USD) + amount * stock.getPrice());
        holdingAmount -= amount;
        if (holdingAmount == 0) {
            sharesHolding.remove(corpName);
            //sharesHolding.remove(stock);
        }
        else {
            sharesHolding.put(corpName, holdingAmount);
        }
    }

    //Should only use in account factory

    /**
     * Adds a share directly to shares holding.
     * SHOULD ONLY BE CALLED IN AccountFactory.
     *
     * @param corpName Name of the stock
     * @param amount Amount of shares
     */
    public void addShare(String corpName, int amount) {
        //Stock stock = stockMarket.getStockByName(corpName);
        sharesHolding.put(corpName, amount);
        //sharesHolding.put(stock, amount);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append(" ,");
        for (Map.Entry<String, Integer> ent : sharesHolding.entrySet()) {
            stringBuilder.append(ent.getKey()).append(" ").append(ent.getValue()).append(" ");
        }
        stringBuilder.append(" ,");
        //stringBuilder.delete(stringBuilder.length() - (sharesHolding.isEmpty() ? 0 : 1), stringBuilder.length());
        return stringBuilder.toString();
    }

    /**
     * Get the info of all shares holding.
     *
     * @return Map of String to Integer, from stock name to amount holding.
     */
    public Map<String, Integer> getSharesHolding() {
        return sharesHolding;
    }
}
