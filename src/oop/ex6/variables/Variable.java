package oop.ex6.variables;

/**
 * classes implementing this interface will represent variables of different types in
 * java-s.
 */
public abstract class Variable {

    private boolean initialized;
    private String varName;

    public Variable() {
        // TODO throw SHIT AT IT
    }

    /**
     * create a new variable, setValue with the value of an old one to setValue.
     * @param variable the value to be used
     * @param variableName the name of the variable
     */
    public Variable(String variableName, Variable variable) {
        if (!VariableUtils.isNameLegal(variableName)) {
            // TODO throw illegal variable name
        } else if(!canGetVariable(variable)) {
            // TODO throw type mismatch
        } else if (!variable.isInitialized()) {
            // TODO throw illegal shit exception
        }
        initialized = true;
        varName = variableName;
    }

    /**
     * create a new variable, initialized with a value given as a string.
     * @param value string representation of the new value.
     * @param variableName the name of the variable
     */
    public Variable(String variableName, String value){
        if (!VariableUtils.isNameLegal(variableName)) {
            // TODO throw illegal variable name
        } else if(!isValueLegal(value)) {
            // TODO throw type mismatch
        }
        initialized = true;
        varName = variableName;
    }

    /**
     * create a new variable, not initialized with a value.
     * @param variableName
     */
    public Variable(String variableName){
        if (!VariableUtils.isNameLegal(variableName)) {
            // TODO throw illegal variable name
        }
        initialized = false;
        varName = variableName;

    }

    /**
     * check whether the variable has been initialized with a value yet (in the constructor or
     * by setValue method)
     * @return true if the variable was initialized, false otherwise.
     */
    public boolean isInitialized() {
        return initialized;
    };

    public void setInitialized(){
        initialized = true;
    }

    /**
     * initalize the variable with a value.
     * @param value the new value as a string.
     */
    public void setValue(String value){
        if (isValueLegal(value)) {
            initialized = true;
        } else {
            // TODO throw exception
        }
    }

    public void setValue(Variable variable){
        if (canGetVariable(variable)) {
            initialized = true;
        } else {
            // TODO throw exception
        }
    }

    @Override
    public String toString() {
        return varName;
    }

    /**
     * determines whether a string is legal as the value of the specific variable type.
     * @param value
     */
    abstract protected boolean isValueLegal(String value);

    /**
     * determines whether this variable can get another variable as a value.
     * @param otherVariable the variable to determine whether it can be used as a value.
     * @return
     */
    abstract protected boolean canGetVariable(Variable otherVariable);

    /**
     * check which type of variable is a specific instance.
     * @return the type of this Variable instance.
     */
    abstract public VARIABLE_TYPES getVariableType();
}