package com.company.Stock;

/**
 * Class for stocks
 */
public class Stock {

    private String corpName;

    private double price;

    /**
     * Constructor of stock objects.
     *
     * @param corpName Name of stock
     * @param price Price of stock
     */
    public Stock(String corpName, double price) {
        this.corpName = corpName;
        this.price = price;
    }

    /**
     * Get stock corp name.
     *
     * @return Corp name
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * Set stock corp name.
     *
     * @param corpName Corp/Stock name
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * Get price of stock.
     *
     * @return Price of stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set price of stock.
     *
     * @param price Price of stock
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return corpName + "," + price;
    }
}
