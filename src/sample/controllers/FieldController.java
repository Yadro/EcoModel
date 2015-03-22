package sample.controllers;

import javafx.fxml.FXML;

import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.models.Pers;


public class FieldController {

    private static final int TILE_SIZE = 45;
    private static final int SIZE = 10;
    private final Image wolf = new Image("wolf.png");
    private final Image rabbit = new Image("rabbit.png");
    private Pers[][] perses = new Pers[SIZE][SIZE];

    @FXML
    public TilePane tilePane;

    @FXML
    private void initialize() {
        tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
        randomPers();
        renderTiles();
    }

    private void renderTiles() {
        for (int j = 0; j < SIZE; j++) {
            HBox hBox = new HBox();
            for (int i = 0; i < SIZE; i++) {
                if (perses[j][i] != null) {
                    createTile(hBox, perses[j][i].howIs());
                } else {
                    createTile(hBox, 0);
                }
            }
            tilePane.getChildren().add(hBox);
        }
    }

    private void createTile(HBox hBox, int type) {
        if (type == 0) {
            Rectangle rectangle = new Rectangle(TILE_SIZE-1, TILE_SIZE-1);
            rectangle.setStroke(Color.ORANGE);
            rectangle.setFill(Color.STEELBLUE);
            hBox.getChildren().add(rectangle);
            return;
        }
        ImageView imv;
        switch (type) {
            case Pers.WOLF:
                imv = new ImageView(wolf);
                break;
            case Pers.WOLFW:
                imv = new ImageView(wolf);
                break;
            case Pers.RABBIT:
                imv = new ImageView(rabbit);
                break;
            default:
                imv = new ImageView();
        }
        imv.setFitWidth(TILE_SIZE);
        imv.setPreserveRatio(true);
        hBox.getChildren().add(imv);
    }

    private void randomPers() {
        int[][] _perses = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
        };
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (_perses[j][i] != 0) {
                    perses[j][i] = new Pers(_perses[j][i]);
                }
            }
        }
    }

    private void step() {

    }
}