package sample.controllers;

import javafx.fxml.FXML;

import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.models.Cell;
import sample.models.Pers;

import java.util.Random;


public class FieldController {

    private final Random random = new Random();

    private static final int TILE_SIZE = 45;
    public static final int SIZE = 10;

    private static final int LEFT_UP = 1;
    private static final int UP = 2;
    private static final int RIGHT_UP = 3;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;
    private static final int LEFT_DOWN = 6;
    private static final int DOWN = 7;
    private static final int RIGHT_DOWN = 8;

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
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                Pers pers = perses[j][i];
                if (pers != null && !pers.checked) {
                    Cell newCell;
                    Pers next;
                    switch (pers.howIs()) {
                        case Pers.RABBIT:
                            newCell = rabbitStep(new Cell(i, j));
                            if (newCell == null) {
                                pers.checked = true;
                                continue;
                            }
                            next = perses[newCell.y][newCell.x];
                            if (next != null && next.howIs() == Pers.RABBIT) {
                                pers.checked = true;
                                continue;
                            }
                            move(pers, next);
                            pers.check();
                            break;

                        case Pers.WOLF:
                            newCell = wolfStep(new Cell(i, j));
                            if (newCell == null) {
                                pers.checked = true;
                                continue;
                            }
                            next = perses[newCell.y][newCell.x];
                            if (next != null && next.howIs() == Pers.RABBIT) {
                                pers.eat();
                            } else {
                                pers.hungry();
                            }
                            if (!pers.isLive()) {
                                perses[j][i] = null;
                                continue;
                            }
                            move(pers, next);
                            break;
                    }
                }
            }
        }
    }

    private Cell _step(Cell cell, int dir) {
        Cell newPos = cell.add(new Cell(dir));
        return newPos.inField() ? newPos : null;
    }

    private Cell rabbitStep(Cell cell) {
        int dir = random.nextInt(8);
        return _step(cell, dir);
    }

    private Cell wolfStep(Cell cell) {
        int dir = 1 + random.nextInt(7);
        return _step(cell, dir);
    }

    private void move(Pers from, Pers to) {
        to = from;
        from = null;
    }

    private int getRandom() {
        return 0;
    }
}