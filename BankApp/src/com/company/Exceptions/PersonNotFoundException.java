package com.company.Exceptions;

/**
 * Exception to throw when the person is not found
 */
public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(){super("Person Not Found!");}

}
