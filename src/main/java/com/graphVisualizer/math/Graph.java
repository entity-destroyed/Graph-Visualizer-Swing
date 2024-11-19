package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import java.awt.*;
import java.awt.geom.Line2D;

public class Graph {
    private double step = 0.1;
    private double scale = 20;
    private int begin = -100;
    private int end = 100;
    private double[] values;
    private Line2D[] lines;
    private boolean visible = true;
    private Color color;
    private String expression;

    public Graph(){
        values = new double[(int) Math.ceil((end-begin)/step)];
        lines = new Line2D[values.length-1];
        for(int i = 0; i < values.length-1; i++){
            lines[i] = new Line2D.Double(0.0,0.0,0.0,0.0);
        }
    }

    public void calculateGraphCurve(int centerX, int centerY){
        for(int i = 1; i < values.length - 1; i++){
            lines[i].setLine(
                    begin*scale + (i-1)*scale*step + centerX,
                    values[i-1]*(-1)*scale + centerY,
                    begin*scale + (i)*scale*step + centerX,
                    values[i]*(-1)*scale + centerY);
        }
    }

    public void setValuesFromExpression(String expression) throws IllegalArgumentException{
        this.expression = expression;
        CustomEvaluator evaluator = new CustomEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<>();

        for(double x = begin, i = 0; i < values.length; x+= step, i+=1){
            variables.set("x", x);
            values[(int) i] = evaluator.evaluate(expression, variables);
        }
    }

    public int getBegin(){
        return begin;
    }
    public int getEnd(){
        return end;
    }
    public double getStep(){
        return step;
    }
    public boolean isVisible(){
        return visible;
    }


    public double[] getValues() {
        return values;
    }

    public Line2D[] getLines(){
        return lines;
    }

    public void setVisible(boolean v){
        visible = v;
    }
    public double getScale(){
        return scale;
    }
    public Color getColor(){
        return color;
    }
    public void setColor(Color c){
        color = c;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression){
        this.expression = expression;
    }
}
