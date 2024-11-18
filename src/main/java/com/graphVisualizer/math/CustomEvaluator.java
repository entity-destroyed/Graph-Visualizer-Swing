package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import java.util.Iterator;

public class CustomEvaluator extends DoubleEvaluator {
    private static final Function SQRT = new Function("sqrt", 1);
    private static final Parameters PARAMS;

    static {
        // Gets the default DoubleEvaluator's parameters
        PARAMS = DoubleEvaluator.getDefaultParameters();
        // add the new sqrt function to these parameters
        PARAMS.add(SQRT);
    }

    public CustomEvaluator() {
        super(PARAMS);
    }

    @Override
    protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
        if(operator == DIVIDE){
            Double operand1 = operands.next();
            Double operand2 = operands.next();
            if(operand2 == 0){
                if(operand1 > 0){
                    return Double.POSITIVE_INFINITY;
                }else if(operand1 < 0){
                    return Double.NEGATIVE_INFINITY;
                }else{
                    return 0.0;
                }
            }else{
                return operand1 / operand2;
            }
        }
        return super.evaluate(operator, operands, evaluationContext);
    }

    @Override
    protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
        if (function == SQRT) {
            // Implements the new function
            Double argument = arguments.next();
            if(argument == 0) return 0.0;
            return Math.sqrt(argument);
        } else if(function == LOG || function == LN) {
            Double argument = arguments.next();
            if (argument == 0) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return function == LOG ? Math.log10(argument) : Math.log(argument);
            }
        }else{
            // If it's another function, pass it to DoubleEvaluator
            return super.evaluate(function, arguments, evaluationContext);
        }
    }
}
