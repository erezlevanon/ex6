package oop.ex6.main.exceptions.variableExceptions;

import oop.ex6.main.exceptions.SjavaException;
import oop.ex6.variables.Variable;

public class TypeMismatchException extends VariableException {
    protected final String ERROR_MESSAGE = "cannot assign to variable - assigned type does not match required type";
    private String uniqueMessage;
    public TypeMismatchException(Variable intoVariable, String value) {
        String type = intoVariable.getVariableType().toString();
        String variableName = intoVariable.toString();
        uniqueMessage = "cannot assign variable " + variableName + " of type " +type + " with the value " + value;
    }

    public TypeMismatchException(Variable intoVariable, Variable variable) {
        String intoType = intoVariable.getVariableType().toString();
        String intoName = intoVariable.toString();
        String ofType = variable.getVariableType().toString();
        String ofName = variable.toString();
        uniqueMessage = "cannot assign variable " + intoName + " of type "+intoType + " with the variable" + ofName +
                " of type " + ofType;
    }
}
