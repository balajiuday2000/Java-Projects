package com.company.Stock;

import com.company.Exceptions.StockNotExistException;
import com.company.Factories.StockFactory;

import java.util.Map;

/**
 * Class for stock Market. It is a singleton.
 */
public class StockMarket {

    /**
     * Information of all stocks available.
     */
    private Map<String, Stock> stockInfo;

    /**
     * StockFactory {@link com.company.Factories.StockFactory}.
     */
    private StockFactory stockFactory;

    /**
     * Singleton object.
     */
    private static StockMarket stockMarket = new StockMarket();

    private StockMarket() {
        stockFactory = new StockFactory();
        stockInfo = stockFactory.getAllStocks();
    }

    /**
     * Get instance of StockMarket.
     *
     * @return Instance of StockMarket
     */
    public static StockMarket getInstance() {
        return stockMarket;
    }

    /**
     * Get stock by its name.
     *
     * @param name Name of stock
     * @return Instance of that stock
     * @throws StockNotExistException If the stock does not exist
     */
    public Stock getStockByName(String name) throws StockNotExistException {
        if (!stockInfo.containsKey(name)) {
            throw new StockNotExistException();
        }
        return stockInfo.get(name);
    }

    /**
     * Refreshes the market if there are updates.
     */
    public void refresh() {
        stockInfo = stockFactory.getAllStocks();
    }
}
