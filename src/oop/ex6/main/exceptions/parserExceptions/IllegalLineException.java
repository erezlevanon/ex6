package oop.ex6.main.exceptions.parserExceptions;
import oop.ex6.main.exceptions.*;

/**
 * Created by yuvalavrami on 6/14/15.
 */
public class IllegalLineException extends SjavaException{
    protected final static String TYPE_ERROR_MESSAGE = "could not parse file - found an illegal line";

    public IllegalLineException (String line) {
        ERROR_MESSAGE = TYPE_ERROR_MESSAGE;
        uniqueMessage = "the line \n\t" + line + "\nis illegal.";
    }

    public IllegalLineException (String line, int lineNumber) {
        ERROR_MESSAGE = TYPE_ERROR_MESSAGE;
        uniqueMessage = "the line \n\t" + line + "\nis illegal.";
        this.addLineNumber(lineNumber);
    }
}
