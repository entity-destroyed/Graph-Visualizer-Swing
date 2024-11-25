package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.StaticVariableSet;
import com.graphVisualizer.utils.ConfigLoader;

import java.awt.*;
import java.awt.geom.Line2D;

public class Graph {
    private final double step = ConfigLoader.getDouble("g.step");
    private final double scale = ConfigLoader.getDouble("g.scale");
    private final int begin = ConfigLoader.getInt("g.begin");
    private final int end = ConfigLoader.getInt("g.end");
    private final double[] values;
    private final Line2D[] lines;
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

    public boolean isVisible(){
        return visible;
    }

    public Line2D[] getLines(){
        return lines;
    }

    public void setVisible(boolean v){
        visible = v;
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
