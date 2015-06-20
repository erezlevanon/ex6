package oop.ex6.parser;

import oop.ex6.main.exceptions.SjavaException;
import oop.ex6.main.exceptions.methodExceptions.IllegalArgumentTypeException;
import oop.ex6.main.exceptions.methodExceptions.IllegalArgumentValueException;
import oop.ex6.main.exceptions.methodExceptions.MethodException;
import oop.ex6.main.exceptions.methodExceptions.WrongArgumentsNumberException;
import oop.ex6.main.exceptions.parserExceptions.IllegalLineException;
import oop.ex6.main.exceptions.parserExceptions.UnbalancedScopeException;
import oop.ex6.main.exceptions.variableExceptions.VariableException;
import oop.ex6.methods.Method;
import oop.ex6.scopes.Scope;
import oop.ex6.variables.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a parser class that parses a java-s file.
 */
public class Parser{


    //TODO some sort of a collection of the available methods

    /**
     * a method that goes through each line of the java-s file, translates the lines to commands and executes
     * the wanted actions on Scopes.
     * @param fileScanner - a scanner that runs on a legal file
     */
    public static void parseFile(Scanner fileScanner, Scope globalScope) throws SjavaException {
        int balancedBracketCounter = 0;
        int curLineNumber = 0;
        while (fileScanner.hasNext()) {
            curLineNumber++;
            if(balancedBracketCounter < 0){ //there is a closing bracket in the file that does not correspond to a
                //valid opening bracket
                throw new UnbalancedScopeException();
            }
            String currentLine = fileScanner.nextLine();

            //find opening and closing brackets, and update their counter accordingly
            if (currentLine.contains("{")){balancedBracketCounter++;}
            if (currentLine.contains("}")){balancedBracketCounter--;}
            if (balancedBracketCounter == 0) { //i.e in global scope: read lines

                if (currentLine == JavaSPatterns.EMPTY_LINE || currentLine == JavaSPatterns.COMMENT_LINE) {
                    continue; // ignore empty and comment lines
                }
                if (currentLine == JavaSPatterns.VARIABLE_LINE ||
                        currentLine == JavaSPatterns.METHOD_SIGNATURE) { // i.e line is legal
                    try {
                        LegalLineParser.parseLine(currentLine, curLineNumber ,globalScope);
                    } catch (SjavaException e){
                        e.addLineNumber(curLineNumber);
                        throw e;
                    }
                }
            } // line is not empty, comment or legal - i.e illegal line
            throw new IllegalLineException();
        }
        if(balancedBracketCounter != 0){ // reached end of file, number of opening and closing brackets does not match
            throw new UnbalancedScopeException();
        }
    }

    public static void ParseBlock(Scanner fileScanner, Scope scope, int lineNumber) throws SjavaException{
        String line;
        while (fileScanner.hasNext()) {
            lineNumber++;
            line = fileScanner.nextLine();

            try {
                if (line.matches(JavaSPatterns.COMMENT_LINE)||line.matches(JavaSPatterns.EMPTY_LINE)) {
                    continue;
                } else if (line.matches(JavaSPatterns.VARIABLE_LINE)) {
                    dealWithVariableLine(line, scope);
                } else if (line.matches(JavaSPatterns.METHOD_CALL)) {
                    dealWithMethodCall(line, scope);
                } else if (line.matches(JavaSPatterns.CONDITION_BLOCK_STARTERS)) {
                    Scope innerScope = new Scope(lineNumber, scope);
                    ParseBlock(fileScanner, innerScope, lineNumber);
                } else if (line.matches(JavaSPatterns.RETURN)) {
                    dealWithReturnStatement(line, scope);
                } else if (line.matches(JavaSPatterns.END_BLOCK)) {
                    return;
                }
            } catch (SjavaException e) {
                e.addLineNumber(lineNumber);
                throw e;
            }

        }
    }

    private static void dealWithVariableLine(String line, Scope scope) throws VariableException{
        final int FINAL_GROUP = 3, TYPE_GROUP = 5, NAME_AND_VALUES_GROUP = 7, NAME_SUBGROUP = 2, VALUE_SUBGROUP = 4;
        Matcher lineMatcher = Pattern.compile(JavaSPatterns.VARIABLE_LINE).matcher(line);
        if (lineMatcher.matches()) {
            Matcher variablesMatcher = Pattern.compile(JavaSPatterns.VARIABLE_OR_ASSIGNMENT).matcher(lineMatcher.group(NAME_AND_VALUES_GROUP));
            boolean isFinal = lineMatcher.group(FINAL_GROUP) != null;
            boolean isDeclaration = lineMatcher.group(TYPE_GROUP) != null;
            if (isDeclaration) {
                VARIABLE_TYPES type = VariableUtils.stringToType(lineMatcher.group(TYPE_GROUP));
                while(variablesMatcher.find()) {
                    String name = variablesMatcher.group(NAME_SUBGROUP);
                    String value = variablesMatcher.group(VALUE_SUBGROUP);
                    Variable newVariable = VariableFactory.produceVariable(type,name,value);
                    if (isFinal) {
                        newVariable = new FinalVariable(newVariable);
                    }
                    System.out.println(newVariable.getVariableType() + "\t" + newVariable.toString() + "\t" + newVariable.isInitialized());
                    scope.addVariable(newVariable);
                }
            } else {
                if (variablesMatcher.matches()) {
                    String name = variablesMatcher.group(NAME_SUBGROUP);
                    String value = variablesMatcher.group(VALUE_SUBGROUP);
                    scope.updateVariable(name, value);
                } else {
                    // TODO throw illegal variable assignment exception;
                }
            }
            }
    }

    private static void dealWithMethodCall(String line, Scope scope) throws MethodException{
        final int NAME_GROUP = 1, VALUES_GROUP = 2;
        Matcher lineMatcher = Pattern.compile(JavaSPatterns.METHOD_CALL).matcher(line);
        if(lineMatcher.matches()) {
            Matcher valuesMatcher = Pattern.compile(JavaSPatterns.VALUE).matcher(lineMatcher.group(VALUES_GROUP));
            String methodName = lineMatcher.group(NAME_GROUP);
            Method method = scope.searchMethod(methodName);
            if (method == null) {
                // TODO throw nonexistent method call;
            }
            int valueIndex = -1;
            while (valuesMatcher.find()) {
                valueIndex++;
                String value = valuesMatcher.group();
                Variable variable;
                if (VariableUtils.isNameLegal(value)) {
                    variable = scope.searchVariableUpwards(value);
                    if (variable != null) {
                        try {
                            method.checkArgumentInIndex(valueIndex, variable);
                        } catch (VariableException e) {
                            throw new IllegalArgumentTypeException(method, valueIndex, variable);
                        }
                        continue;
                    }
                }
                try {
                    method.checkArgumentInIndex(valueIndex,value);
                } catch (VariableException e) {
                    throw new IllegalArgumentValueException(method, valueIndex, value);
                }
            }
            if (valueIndex != method.getNumOfArguments() - 1) {
                throw new WrongArgumentsNumberException(method, valueIndex + 1);
            }
        }


    }

    private static void dealWithReturnStatement(String line, Scope scope) {

    }

    public static void main(String[] args) throws SjavaException{
        try {
            JavaSPatterns.compilePatterns();
            Scope scope = new Scope(0);
            Method method = new Method("shitsAndTits", new VARIABLE_TYPES[]{VARIABLE_TYPES.INTEGER,VARIABLE_TYPES.STRING},new String[]{"as","b"},0);
            scope.addMethod(method);
            String line = "shitsAndTits(3, \"dsad\", 5);";
            dealWithMethodCall(line, scope);
        } catch (SjavaException e) {
            System.err.println(e.getMessage());
        }
        System.out.println();
    }
}