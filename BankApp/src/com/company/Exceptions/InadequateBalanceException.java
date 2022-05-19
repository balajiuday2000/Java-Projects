package com.company.Exceptions;

/**
 * Exception to throw when the balance is inadequate
 */
public class InadequateBalanceException extends RuntimeException{

    public InadequateBalanceException(){super("Not enough balance!");}

}
