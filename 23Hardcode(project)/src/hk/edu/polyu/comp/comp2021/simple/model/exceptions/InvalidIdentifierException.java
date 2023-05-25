package hk.edu.polyu.comp.comp2021.simple.model.exceptions;

public class InvalidIdentifierException extends SyntaxErrorException {
    public InvalidIdentifierException(String message){
        super(message);
    }
}
