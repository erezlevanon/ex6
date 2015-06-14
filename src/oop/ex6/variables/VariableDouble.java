package oop.ex6.variables;

/**
 * Created by Erez Levanon on 14/06/2015.
 */
public class VariableDouble extends Variable{

    private final static VARIABLE_TYPES[] LEGAL_TYPES = new VARIABLE_TYPES[]{VARIABLE_TYPES.INTEGER,
                                                                                    VARIABLE_TYPES.DOUBLE};
    final static String LEGAL_VAL = "\\-?\\d+\\.?\\d+";

    /**
     * determines whether a string is legal as the value of the specific variable type.
     *
     * @param value
     */
    @Override
    protected boolean isValueLegal(String value) {
        return value.matches(LEGAL_VAL);
    }

    /**
     * determines whether this variable can get another variable as a value.
     *
     * @param otherVariable the variable to determine whether it can be used as a value.
     * @return
     */
    @Override
    protected boolean canGetVariable(Variable otherVariable) {
        return false;
    }

    /**
     * check which type of variable is a specific instance.
     *
     * @return the type of this Variable instance.
     */
    @Override
    public VARIABLE_TYPES getVariableType() {
        return null;
    }
}