package com.company.Exceptions;

/**
 * Exception to throw when the stock does not exist
 */
public class StockNotExistException extends RuntimeException{

    public StockNotExistException() {
        super("Stock does not exist!");
    }

}
