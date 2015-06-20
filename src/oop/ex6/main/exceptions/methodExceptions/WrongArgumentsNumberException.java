package oop.ex6.main.exceptions.methodExceptions;

import oop.ex6.main.exceptions.SjavaException;

/**
 * Created by yuvalavrami on 6/14/15.
 */
public class WrongArgumentsNumberException extends SjavaException {
    String errorMessage = "number of arguments received does not match number of arguments required by this method";
}