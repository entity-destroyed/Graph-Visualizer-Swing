package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import java.util.Iterator;

/**
 * Evaluates mathematical expressions with custom functionalities such as handling square roots
 * and providing custom behavior for division by zero.
 * Extends the capabilities of {@code DoubleEvaluator} by adding a square root function and overriding the
 * evaluate methods to support custom functionality for division and logarithms.
 *
 * @see DoubleEvaluator
 */
public class CustomEvaluator extends DoubleEvaluator {
    /**
     * A custom function representing the square root operation.
     * This function is defined with the name "sqrt" and takes one argument.
     */
    private static final Function SQRT = new Function("sqrt", 1);

    /**
     * A set of parameters used by the {@code CustomEvaluator} to define the functions and operators
     * it supports. The parameters include the default parameters from {@code DoubleEvaluator}
     * along with additional custom functions like square root.
     */
    private static final Parameters PARAMS;

    static {
        // Gets the default DoubleEvaluator's parameters
        PARAMS = DoubleEvaluator.getDefaultParameters();
        // add the new sqrt function to these parameters
        PARAMS.add(SQRT);
    }

    /**
     * Constructs a new instance of {@code CustomEvaluator} with the predefined parameters that include custom functions
     * and behaviors. It extends the base functionality provided by {@code DoubleEvaluator}, incorporating additional
     * handling for operations such as square root and division by zero.
     *
     * @see DoubleEvaluator
     */
    public CustomEvaluator() {
        super(PARAMS);
    }

    /**
     * Evaluates mathematical operations, specifically handling the division operator with custom behavior
     * for division by zero, returning positive or negative infinity depending on the numerator's sign.
     *
     * @param operator          the operator to be evaluated, such as addition, subtraction, multiplication, or division
     * @param operands          an iterator over the operands used in the operation
     * @param evaluationContext an optional context object providing additional information for the evaluation
     * @return the result of the evaluated operation, or delegates to the superclass method for operations other than division
     */
    @Override
    protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
        if (operator == DIVIDE) {
            Double operand1 = operands.next();
            Double operand2 = operands.next();
            if (operand2 == 0) {
                if (operand1 > 0) {
                    return Double.POSITIVE_INFINITY;
                } else if (operand1 < 0) {
                    return Double.NEGATIVE_INFINITY;
                } else {
                    return 0.0;
                }
            } else {
                return operand1 / operand2;
            }
        }
        return super.evaluate(operator, operands, evaluationContext);
    }

    /**
     * Evaluates a mathematical function with given arguments and an optional evaluation context.
     * This method is overridden to handle specific functions such as square root and logarithms.
     *
     * @param function          the function to be evaluated, such as square root, logarithm (base 10), or natural logarithm
     * @param arguments         an iterator over the arguments to be used in the function
     * @param evaluationContext an optional context object providing additional information for the evaluation
     * @return the result of the evaluated function, specifically handling square root and logarithms,
     * or delegates to the superclass method for other functions
     */
    @Override
    protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
        if (function == SQRT) {
            // Implements the new function
            Double argument = arguments.next();
            if (argument == 0) return 0.0;
            return Math.sqrt(argument);
        } else if (function == LOG || function == LN) {
            Double argument = arguments.next();
            if (argument == 0) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return function == LOG ? Math.log10(argument) : Math.log(argument);
            }
        } else {
            // If it's another function, pass it to DoubleEvaluator
            return super.evaluate(function, arguments, evaluationContext);
        }
    }
}
