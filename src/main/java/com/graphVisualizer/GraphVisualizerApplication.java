package com.graphVisualizer;
import com.graphVisualizer.customNodes.DrawingPane;
import com.graphVisualizer.customNodes.FunctionInputsPanel;
import com.graphVisualizer.style.Style;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.graphVisualizer.style.Style.defaultBackground;

public class GraphVisualizerApplication extends Application{

    private int windowWidth = 600;
    private int windowHeight = 420;

    @Override
    public void start(Stage stage) throws Exception {
        HBox hBox = new HBox();
        DrawingPane drawingPane = new DrawingPane();
        FunctionInputsPanel functionPanel = new FunctionInputsPanel(drawingPane);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        hBox.getChildren().add(functionPanel);
        hBox.getChildren().add(drawingPane);
        Scene mainScene = new Scene(hBox, windowWidth,windowHeight);
        stage.setScene(mainScene);
        stage.setTitle("Graph Visualizer");
        stage.show();
    }

    public static void main(String [] args){
        launch();
    }
}