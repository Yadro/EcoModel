package sample.controllers;

import javafx.fxml.FXML;

import javafx.scene.image.*;
import javafx.scene.layout.TilePane;
import sample.models.Pers;


public class FieldController {

    private static final int SIZE = 20;
    private Pers[][] perses = new Pers[SIZE][SIZE];
    private Image rabbit = new Image("rabbit.png");
    private Image wolf = new Image("wolf.png");


    @FXML
    public TilePane tile;

    @FXML
    private void initialize() {
        renderTiles();
    }

    private void renderTiles() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                ImageView imv = new ImageView(wolf);
                imv.setFitWidth(45);
                imv.setPreserveRatio(true);
                tile.getChildren().add(imv);
            }
        }
    }
}