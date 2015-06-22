package oop.ex6.main.exceptions.methodExceptions;

import oop.ex6.methods.Method;

/**
 an exception representing a state where a method argument's value is illegal.
 */
public class IllegalArgumentValueException extends MethodException {
    private final static String TYPE_ERROR_MESSAGE = "cannot assign argument with this value: value is illegal";

    /**
     * constructor for exception. holds an error message according to the type of exception,
     * and a unique message updated according to the illegal argument for which it was thrown.
     */
    public IllegalArgumentValueException(Method method, int argumentIndex, String value) {
        uniqueMessage = "in " + method.getName() + " method the value " + value + " as the " +argumentIndex +
                " argument is illegal";
        ERROR_MESSAGE = TYPE_ERROR_MESSAGE;
    }
}
