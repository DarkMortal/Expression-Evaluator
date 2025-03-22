package evaluator;

import java.util.*;
import complex.Complex;

/**
 * Implementation of the Evaluator interface
 * @author Saptarshi Dey
 * @since March 2025
 * @version 1.0
 */

public class ExpressionEvaluator implements Evaluator {
    private Map<String, Complex> variables;
    
    /**
     * Default constructor
     */
    public ExpressionEvaluator() {
    	this.variables = new HashMap<>();
    }
    /** Parameterised constructor
     * @param variables_ Map Object representing variables to be used while evaluating any function
     */
    public ExpressionEvaluator(Map<String, Complex> variables_){
        this.variables = variables_;
    }
    /**
     * Setter for the <b>variables</b> Map Object for Complex values
     * @param variable Name of the variable
     * @param value Value of th variable
     */
    public void setVariable(String variable, Complex value) {
    	this.variables.put(variable, value);
    }
    /**
     * Setter for the <b>variables</b> Map Object for real values
     * @param variable Name of the variable
     * @param value Value of the variable
     */
    public void setVariable(String variable, double value) {
    	this.variables.put(variable, new Complex(value, 0.0));
    }
    /**
     * Setter to set the whole <b>variables</b> Map Object at once
     * @param variables_ Map object of all the variables
     */
    public void setVariables(Map<String, Complex> variables_){
        this.variables = variables_;
    }
	/**
	 * Getter to get individual variables
	 * @param variable Name of the variable
	 * @return Complex number representing the value of the variable
	 */
    public Complex getVariable(String variable){
        return this.variables.get(variable);
    }
    /**
     * Remove a variable from the <b>variables</b> Map Object
     * @param variable Name of the variable
     */
    public void removeVariable(String variable){
    	this.variables.remove(variable);
    }
    /**
     * Computes a binary operation between 2 operands and returns a Complex number as the result
     * @param operand1 First operand
     * @param operand2 Second operand
     * @param operator The operator ot be used
     * @param verbose Whether or not to show the computation
     * @return Resultant Complex number
     * @throws Exception
     */
    private Complex compute(Complex operand1, Complex operand2, char operator, boolean verbose) throws Exception{
        if(verbose)
            System.out.println("Evaluating : " + operand1 + " " + operator + " " + operand2);
        if(operator == '/' && operand2.equals(new Complex(0.0, 0.0)))
            throw new ArithmeticException("Division by zero");
        switch (operator) {
            case '+': return operand1.add(operand2);
            case '-': return operand1.subtract(operand2);
            case '*': return operand1.multiply(operand2);
            case '/': return operand1.divide(operand2);
            case '^': return operand1.pow(operand2);
            default: throw new ArithmeticException("Invalid operator: " + operator);
        }
    }
    /**
     * Computes a Complex operand from a given String
     * @param variable String representation of the variable
     * @return Complex number representing the value of the variable
     * @throws Exception
     */
    private Complex getOperand(String variable) throws Exception {
        if(variable.isEmpty()) return  new Complex(0.0, 0.0);
        if(variable.equals("pi")) return new Complex(Math.PI, 0.0);
        if(variable.equals("e")) return new Complex(Math.E, 0.0);
        if(variable.charAt(variable.length() - 1) == 'i'){
            if(variable.length() == 1) return new Complex(0.0, 1.0);
            String t = variable.substring(0, variable.length() - 1);
            if(t.equals("pi")) return new Complex(0.0, Math.PI);
            if(t.equals("e")) return new Complex(0.0, Math.E);
            return new Complex(0.0, Double.parseDouble(t));
        }
        try{
            return new Complex(Double.parseDouble(variable), 0.0);
        }catch (Exception e){
            if(variables != null && variables.containsKey(variable))
                return variables.get(variable);
            else throw new Exception("Symbol not found: " + variable);
        }
    }
    /**
     * The function that connects all the modules together and computes the result 
     * @param equation String repesentation of the equation
     * @param verbose Whether or not to show the computation
     * @return Resultant Complex number
     * @throws Exception
     */
    public Complex complexEvaluator(String equation, boolean verbose) throws Exception{
        List<Complex> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean isCurrentOperandNegative = false;

        for(int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);

            if(terminalSymbols.contains(c)){
                String variable = sb.toString().trim();

                if(!variable.isEmpty()){
                    operands.add(isCurrentOperandNegative ?
                            getOperand(variable).multiply(-1.0) :
                            getOperand(variable));
                    sb = new StringBuilder();
                    isCurrentOperandNegative = false;
                }else{
                    if(c == '-') isCurrentOperandNegative = true;
                    else throw new Exception("Invalid equation");
                    continue;
                }
                operators.add(c);
            } else sb.append(c);
            if(i == equation.length() - 1) {
                String variable = sb.toString().trim();
                if(variable.isEmpty()) continue;
                operands.add(isCurrentOperandNegative ?
                        getOperand(variable).multiply(-1.0) :
                        getOperand(variable));
                isCurrentOperandNegative = false;
            }

            if(c == '('){
                i++;
                int count = 0;
                String function = sb.toString().trim();
                function = function.substring(0, function.length() - 1);
                StringBuilder subEquation = new StringBuilder();
                
                while(equation.charAt(i) != ')' || count != 0){
                    if(equation.charAt(i) == '(') count++;
                    else if(equation.charAt(i) == ')') count--;
                    subEquation.append(equation.charAt(i));
                    i++;
                }
                if(verbose) System.out.println("SubEquation: " + subEquation);
                
                Complex operand = complexEvaluator(subEquation.toString(), verbose)
                		.multiply(isCurrentOperandNegative ? -1.0 : 1.0);
                switch(function) {
	            	case "": operands.add(operand); break;
	                case "sin": {
	                	if(verbose) System.out.println("Evaluating : sin("+operand+")");
	                	operands.add(operand.sin()); break;
	                }
	                case "cos": {
	                	if(verbose) System.out.println("Evaluating : cos("+operand+")");
	                	operands.add(operand.cos()); break;
	                }
	                case "tan": {
	                	if(verbose) System.out.println("Evaluating : tan("+operand+")");
	                	operands.add(operand.sin().divide(operand.cos())); break;
	                }
	                case "log": {
	                	if(verbose) System.out.println("Evaluating : log("+operand+")");
	                	operands.add(operand.log(Math.E)); break;
	                }
	                default: throw new Exception("Function not supported: " + function);
	            } i++;
                if(i < equation.length())
                    operators.add(equation.charAt(i));
                sb = new StringBuilder();
                isCurrentOperandNegative = false;
            }
        }
        // TODO: implement operator precedence
        for(char operator: terminalSymbols){
            while(!operators.isEmpty() && operators.contains(operator)){
                int index = operators.indexOf(operator);
                Complex operand1 = operands.get(index);
                Complex operand2 = operands.get(index + 1);
                operands.set(index, compute(operand1, operand2, operator, verbose));

                operands.remove(index + 1);
                operators.remove(index);
            }
        }

        return operands.getFirst();
    }
    /**
     * Wrapper for <b>complexEvaluator</b> function
     * @param equation String repesentation of the equation
     * @param precision Precision of fractional part of the real and imaginary values
     * @param verbose Whether or not to show the computation
     * @return String value representing the result
     * @throws Exception
     */
    public String evaluateEquation(String equation,int precision, boolean verbose) throws Exception {
        return this.complexEvaluator(equation, verbose).toString(precision);
    }
}