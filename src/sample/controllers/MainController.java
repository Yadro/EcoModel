package sample.controllers;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.models.Cell;
import sample.models.Pers;


public class MainController extends Application {

    public static final int SIZE = 10;

    Pers[][] perses = new Pers[SIZE][SIZE];

    RenderController rc;
    GraphicsContext gc;
    PersController pc;
    TouchController tc;

    Cell now = new Cell();

    int how = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EventHandler<KeyEvent> handler = event -> debugLoop();

        EventHandler<MouseEvent> mouseEventHandler = e -> {
            int type = 0;
            switch (e.getButton()) {
                case PRIMARY:
                    type = Pers.WOLF;
                    break;
                case MIDDLE:
                    type = Pers.WOLFW;
                    break;
                case SECONDARY:
                    type = Pers.RABBIT;
                    break;
            }
            touchEvent((int) e.getSceneX(), (int) e.getSceneY(), type);
        };

        now = new Cell(0, 0);

        Canvas canvas = new Canvas(1024, 900);
        canvas.setOnKeyReleased(handler);
        canvas.setOnMouseClicked(mouseEventHandler);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();
        rc = new RenderController(gc);
        tc = new TouchController(perses);

        Group root = new Group();
        root.getChildren().add(canvas);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.show();

        //initialize();
    }

    private void initialize() {
        initRandomPers();
        pc = new PersController(SIZE, perses);
        rc.render(perses);
    }

    void debugLoop() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                pc.step(new Cell(i, j), how);
            }
        }
        rc.render(perses);
        if (++how > 3) {
            pc.uncheck();
            how = 1;
        }
        System.out.println("---------");
    }

    /*void debugStep() {
        pc.step(now, firstFor);
        rc.render(perses);

        rc.renderPos(now.x, now.y);

        now.x++;
        if (now.x >= SIZE) {
            now.x = 0;
            now.y++;
            if (now.y >= SIZE) {
                now.y = 0;
                if (firstFor) {
                    firstFor = false;
                } else {
                    pc.uncheck();
                    firstFor = true;
                }
            }
        }
    }*/

    private void initRandomPers() {
        int[][] _perses = {
                {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1, 0, 3, 0, 1, 0, 0, 0},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0},
        };
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (_perses[i][j] != 0) {
                    perses[i][j] = new Pers(_perses[i][j]);
                }
            }
        }
    }

    private void touchEvent(int x, int y, int type) {
        tc.click(x, y, type);
        rc.render(perses);
    }
}