package com.graphVisualizer.customNodes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static com.graphVisualizer.style.Style.defaultBackground;

public class FunctionInputsPanel extends VBox {
    private FunctionTextInputComponent [] inputs;
    private Button newButton;
    private DrawingPane drawingPane;

    public FunctionInputsPanel(DrawingPane drawingPane){
        this.drawingPane = drawingPane;
        setMinWidth(160);
        setSpacing(10);
        setBackground(defaultBackground);
        setPadding(new Insets(4));
        newButton = new Button("Add new");
        newButton.setOnAction(event -> addNewInput());
        getChildren().add(newButton);
    }

    private void addNewInput(){
        getChildren().add(getChildren().size()-1,new FunctionTextInputComponent(this));
        drawingPane.addGraph(((FunctionTextInputComponent)getChildren().get(getChildren().size()-2)).getGraph());
    }

    public void deleteInput(FunctionTextInputComponent input){
        drawingPane.removeGraph(input.getGraph());
        getChildren().remove(input);
    }

    public void updateAll(){
        for(int i = 0; i < getChildren().size() - 1; i++){
            FunctionTextInputComponent function = (FunctionTextInputComponent) getChildren().get(i);
            drawingPane.updateGraph(function.getGraph());
        }
    }
    public void updateInput(FunctionTextInputComponent input){
        drawingPane.updateGraph((input.getGraph()));
    }
}
