package oop.ex6.main.exceptions.methodExceptions;

import oop.ex6.methods.Method;

/**
 * Created by yuvalavrami on 6/14/15.
 */
public class WrongArgumentsNumberException extends MethodException {
    private final static String TYPE_ERROR_MESSAGE =
            "number of arguments received does not match number of arguments required by this method";

    /**
     * constructor for exception. holds an error message according to the type of exception,
     * and a unique message updated according to the illegal number of arguments for which it was thrown.
     */
    public WrongArgumentsNumberException(Method method, int numOfArgs) {
        ERROR_MESSAGE = TYPE_ERROR_MESSAGE;
        uniqueMessage = "the method -" + method.getName() + "- should get " + method.getNumOfArguments() + " arguments"
                + " but got " + numOfArgs + ".";
    }
}
