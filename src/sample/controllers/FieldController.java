package sample.controllers;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.models.Cell;
import sample.models.Pers;

import java.util.Random;
import java.util.Scanner;


public class FieldController extends Application {

    public static final int SIZE = 20;

    Pers[][] perses = new Pers[SIZE][SIZE];
    Random random = new Random();
    RenderController rc;
    GraphicsContext gc;
    Cell now = new Cell();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent event) {
                step(now);
                rc.render(perses);

                /* debug */
                rc.renderRect(now.x, now.y);

                now.x++;
                if (now.x >= SIZE) {
                    now.x = 0;
                    now.y++;
                    if (now.y >= SIZE) {
                        now.y = 0;
                    }
                }
            }
        };

        now = new Cell(0, 0);

        Canvas canvas = new Canvas(1024, 900);
        canvas.setOnKeyReleased(handler);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
        rc = new RenderController(gc);

        Group root = new Group();
        root.getChildren().add(canvas);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.show();

        initialize();
    }

    private void initialize() {
        initRandomPers();
        rc.renderTiles(perses);
        gc.restore(); // â rc

//        gameLoop();
    }

    /*    private void render() {
        gc.clearRect(0, 0, 1024, 900);
        renderTiles();
//        gc.restore();
//        gc.stroke();
    }

    private void renderTiles() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[j][i] != null) {
                    renderTile(TILE_SIZE * i, TILE_SIZE * j, perses[j][i].howIs());
                } else {
                    renderTile(TILE_SIZE * i, TILE_SIZE * j, 0);
                }
            }
        }
    }

    private void renderTile(int x, int y, int type) {
        gc.setFill(Color.BLACK);
        gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
        if (type == 0) {
            return;
        }
        switch (type) {
            case Pers.WOLF:
                gc.drawImage(wolf, x, y, TILE_SIZE, TILE_SIZE);
                break;
            case Pers.WOLFW:
                gc.drawImage(wolf, x, y, TILE_SIZE, TILE_SIZE);
                break;
            case Pers.RABBIT:
                gc.drawImage(rabbit, x, y, TILE_SIZE, TILE_SIZE);
                break;
            default:
        }
    }*/

    private void initRandomPers() {
        int[][] _perses = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 1, 0, 0, 0, 3, 0, 0, 3, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 1, 0, 0, 0, 3, 0, 0, 3, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
        };
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (_perses[j][i] != 0) {
                    perses[j][i] = new Pers(_perses[j][i]);
                }
            }
        }
    }

    /* Logic */
    public void steps() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                Pers pers = perses[j][i];
                if (pers != null && !pers.checked) {
                    print(i, j);
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
                            pers.check();
                            perses[newCell.y][newCell.x] = pers;
                            perses[j][i] = null;
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
                            pers.check();
                            perses[newCell.y][newCell.x] = pers;
                            perses[j][i] = null;
                            break;
                        default:
                            System.out.println("wat");
                    }
                }
            }
        }
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[j][i] != null) perses[j][i].checked = false;
            }
        }
    }

    public void step(Cell now) {
        int i = now.x, j = now.y;

        Pers pers = perses[j][i];
        if (pers != null && !pers.checked) {
            Cell newCell;
            Pers next;
            switch (pers.howIs()) {
                case Pers.RABBIT:
                    newCell = rabbitStep(new Cell(i, j));
                    if (newCell == null) {
                        pers.checked = true;
                        return;
                    }
                    next = perses[newCell.y][newCell.x];
                    if (next != null && next.howIs() == Pers.RABBIT) {
                        pers.checked = true;
                        return;
                    }
                    pers.check();
                    perses[newCell.y][newCell.x] = pers;
                    perses[j][i] = null;
                    break;

                case Pers.WOLF:
                    newCell = wolfStep(new Cell(i, j));
                    if (newCell == null) {
                        pers.checked = true;
                        return;
                    }
                    next = perses[newCell.y][newCell.x];
                    if (next != null && next.howIs() == Pers.RABBIT) {
                        pers.eat();
                    } else {
                        pers.hungry();
                    }
                    if (!pers.isLive()) {
                        perses[j][i] = null;
                        return;
                    }
                    pers.check();
                    perses[newCell.y][newCell.x] = pers;
                    perses[j][i] = null;
                    break;
                default:
                    System.out.println("wat");
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

    private void print(int x, int y) {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[j][i] != null) {
                    String p = " ";
                    switch (perses[j][i].howIs()) {
                        case Pers.RABBIT:
                            p = "R";
                            break;
                        case Pers.WOLF:
                            p = "M";
                            break;
                        case Pers.WOLFW:
                            p = "W";
                            break;
                    }
                    String half = (perses[j][i].half < 10) ? "0" + perses[j][i].half : "" + perses[j][i].half;
                    if (i == x && j == y) {
                        System.out.print(p + half + "<");
                    } else if (perses[j][i].checked) {
                        System.out.print(p + half + "v");
                    } else
                        System.out.print(p + half + "|");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}