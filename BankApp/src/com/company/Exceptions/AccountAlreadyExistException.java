package com.company.Exceptions;

/**
 * Exception to throw when trying to create an account that already exists.
 */
public class AccountAlreadyExistException extends RuntimeException{

    public AccountAlreadyExistException() {super("Account of this type already exist!");}

}
