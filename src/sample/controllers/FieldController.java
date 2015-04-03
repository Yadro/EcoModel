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

    GraphicsContext gc;
    private final Random random = new Random();

    private static final int TILE_SIZE = 45;
    public static final int SIZE = 20;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EventHandler handler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent event) {
                System.out.println(event.getEventType());
            }
        };

        Canvas canvas = new Canvas(1024, 900);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);
        root.addEventHandler(KeyEvent.ANY, handler);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.show();

        initialize();
    }

    private void initialize() {
        initRandomPers();
        renderTiles();
        gc.restore();

//        gameLoop();
    }

    private void gameLoop() {
        while (true) {
            step();
        }
    }

    private void render() {
//        tilePane.getChildren().clear();
        renderTiles();
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
    }

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
                    render();
                    Scanner scanner = new Scanner(System.in);
                    while (scanner.nextLine().equals("q")) {
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