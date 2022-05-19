package com.company.Exceptions;

/**
 * Exception to call when try to visit/operate on an account that does not exist.
 */
public class AccountNotExistException extends RuntimeException{

    public AccountNotExistException(){super("Account does not Exist!");}

}
