package com.graphVisualizer.style;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Style {
    public static Border defaultBorder = new Border(new BorderStroke(
            Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(1)));
    public static Background defaultBackground = new Background(new BackgroundFill(
            Color.rgb(41,164,20, 0.5),
            new CornerRadii(5),
            new Insets(0)));
}
