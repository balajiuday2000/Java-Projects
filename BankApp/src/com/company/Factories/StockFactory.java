package com.company.Factories;

import com.company.Utils.Parser;
import com.company.Stock.Stock;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory that produces stocks.
 */
public class StockFactory {

    /**
     * Parsing utils {@link com.company.Utils.Parser}.
     */
    private Parser parser;

    public StockFactory() {
        parser = new Parser();
    }

    /**
     * Produce a single stock instance.
     *
     * @param corpName Stock name
     * @param price Stock price
     * @return Stock instance
     */
    public Stock produceSingleStock(String corpName, double price) {
        return new Stock(corpName, price);
    }

    /**
     * Produce all the stocks available.
     *
     * @return Name to stock mapping for all stocks.
     */
    public Map<String, Stock> getAllStocks() {
        Map<String, Double> stockInfo = parser.parseAllStockInfo();
        Map<String, Stock> allStocks = new HashMap<>();
        for (Map.Entry<String, Double> info : stockInfo.entrySet()) {
            allStocks.put(info.getKey(), produceSingleStock(info.getKey(), info.getValue()));
        }
        return allStocks;
    }

}
