package com.graphVisualizer.customNodes;

import com.graphVisualizer.math.Graph;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class FunctionTextInputComponent extends GridPane {

    private Graph graph;
    private TextField textField;
    private ToggleButton visibilityButton;
    private Button deleteButton;
    public FunctionTextInputComponent(FunctionInputsPanel panel){
        textField = new TextField();
        visibilityButton = new ToggleButton("Hide");
        deleteButton = new Button("Delete");
        textField.setPromptText("=2x+3");
        graph = new Graph();

        add(textField,0,0,2,1);
        add(visibilityButton, 0,1);
        add(deleteButton, 1,1);

        // Hitting enter will exit the TextField
        textField.setOnAction(event -> textField.getParent().requestFocus());
        //upon exiting the TextField evaluation occurs
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                graph.setValuesFromExpression(textField.getText());
                panel.updateInput(this);
            }
        });
        deleteButton.setOnAction(event -> panel.deleteInput(this));
        visibilityButton.setOnAction(event -> {
            graph.setVisible(!visibilityButton.isSelected());
            panel.updateInput(this);
            if(visibilityButton.isSelected()){
                visibilityButton.setText("Show");
            }else{
                visibilityButton.setText("Hide");
            }
        });

    }

    public Graph getGraph(){
        return graph;
    }
}
