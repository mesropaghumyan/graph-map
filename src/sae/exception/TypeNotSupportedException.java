package sae.exception;

/**
/**
 * Cette classe représente l'excéption TypeNotSupportedException.
 * @author Loan & Mesrop
 * @version 1.0
 */

public class TypeNotSupportedException extends RuntimeException{
    
    private String message = "Type not supported";
    
    public TypeNotSupportedException(){
        super();
    }
    
    @Override
    public String toString(){
        return message;
    }
    
}
