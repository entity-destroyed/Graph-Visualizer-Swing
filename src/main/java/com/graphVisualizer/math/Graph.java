package com.graphVisualizer.math;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    private double step = 1;
    private double scale = 20;
    private int begin = -100;
    private int end = 100;
    private double[] values;
    private Line[] lines;
    private boolean visible = true;

    public Graph(){
        values = new double[(int) Math.ceil((end-begin)/step)];
        lines = new Line[values.length-1];
        for(int i = 0; i < values.length-1; i++){
            lines[i] = new Line(0.0,0.0,0.0,0.0);
        }
    }

    public void calculateGraphCurve(int centerX, int centerY){
        for(int i = 1; i < values.length - 1; i++){
            lines[i].setStartX(begin*scale + (i-1)*scale + centerX);
            lines[i].setStartY(values[i-1]*(-1)*scale + centerY);
            lines[i].setEndX(begin*scale + (i)*scale + centerX);
            lines[i].setEndY(values[i]*(-1)*scale + centerY);
            lines[i].setVisible(visible);
        }
    }

    public void setValuesFromExpression(String expression){
        DoubleEvaluator evaluator = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<>();

        for(double x = begin, i = 0; i < values.length; x+= step/scale, i+=1){
            variables.set("x", x == 0 ? 0.01 : x);
            try {
                values[(int) i] = evaluator.evaluate(expression, variables);
            } catch (Throwable e) {
                System.out.println("\n------------------nemjoooooooo------------\n");
                e.printStackTrace();
            }
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

    public Line[] getLines(){
        return lines;
    }

    public void setVisible(boolean v){
        visible = v;
    }
    public double getScale(){
        return scale;
    }

}
