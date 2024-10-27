package com.graphVisualizer.customNodes;

import com.graphVisualizer.math.Graph;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DrawingPane extends Pane {

    private final int width = 400;
    private final int height = 400;
    private final int centerX;
    private final int centerY;

    public DrawingPane(){
        setPrefSize(width,height);
        setMaxSize(width, height);
        centerX = width/2;
        centerY = height/2;
        Background bg = new Background(new BackgroundFill(
                Color.rgb(1,1,1,0.09),
                new CornerRadii(4),
                Insets.EMPTY
        ));
        setBackground(bg);
        drawGrid();

    }

    private void drawGrid(){

        Line line = new Line(centerX, 0, centerX, height);
        line.setStroke(Color.BLACK);
        getChildren().add(line);
        line = new Line(0, centerY, width, centerY);
        getChildren().add(line);
    }

    public void addGraph(Graph graph){
        for(int i = 0; i < graph.getLines().length; i++){
            getChildren().add(graph.getLines()[i]);
        }
    }

    public void updateGraph(Graph graph){
        graph.calculateGraphCurve(centerX, centerY);
    }

    public void removeGraph(Graph graph){
        for(int i = 0; i < graph.getLines().length; i++){
            getChildren().remove(graph.getLines()[i]);
        }
    }



}
