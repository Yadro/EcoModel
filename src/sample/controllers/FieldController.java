package sample.controllers;

import com.sun.javafx.property.PropertyReference;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.fxml.FXML;

import javafx.geometry.Orientation;
import javafx.scene.image.*;
import javafx.scene.layout.TilePane;
import sample.models.Pers;


public class FieldController {

    private static final int SIZE = 20;
    private IntegerProperty[][] integerProperties = new IntegerProperty[SIZE][SIZE];
    private Property<Pers>[][] properties = new Property[SIZE][SIZE];

    @FXML
    public TilePane tile;

    @FXML
    private void initialize() {
        initView();
    }

    private void initView() {
        Image image = new Image("Party_god.png");
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                ImageView imv = new ImageView(image);
                imv.setFitWidth(45);
                imv.setPreserveRatio(true);
                imv.setX(i);
                imv.setY(j);
                tile.getChildren().add(imv);
            }
        }
    }
}