package com.company.Exceptions;

/**
 * Exception to throw when the share is not enough
 */
public class NotEnoughShareException extends RuntimeException{

    public NotEnoughShareException() {super("Does not have enough share!");}

}
