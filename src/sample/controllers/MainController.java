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

public class MainController extends Application implements Consts {

    public int status = CREATION;
    public int how = 1;
    public Cell now = new Cell();

    Pers[][] perses = new Pers[SIZE][SIZE];
    Pers[][] save;

    RenderController rc;
    GraphicsContext gc;
    PersController pc;
    TouchController tc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EventHandler<KeyEvent> handler = event -> debugLoop();

        EventHandler<MouseEvent> mouseEventHandler = e -> {
            /*int type = 0;
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
            createMap((int) e.getSceneX(), (int) e.getSceneY(), type);*/
            loop(e);
        };

        now = new Cell(0, 0);

        Canvas canvas = new Canvas(1024, 900);
        canvas.setOnMouseClicked(mouseEventHandler);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();
        rc = new RenderController(gc);
        tc = new TouchController();
        pc = new PersController(perses);

        Group root = new Group();
        root.getChildren().add(canvas);

        stage.setTitle("EcoModel");
        stage.setScene(new Scene(root));
        stage.show();

        rc.render(perses, status);
    }

    private void initialize() {
        initRandomPers();
        rc.render(perses, status);
    }

    void loop(MouseEvent e) {
        switch (tc.clickButton(e, status)) {
            case NEW_GAME:
                status = PLAYING;
                System.arraycopy(perses, 0, save, 0, perses.length);
                break;
            case NEXT_STEP:
                step();
                // проверка на кол во персов ->
                // status = END;
                break;
            case AGAIN:
                status = CREATION;
                // clear
                perses = new Pers[SIZE][SIZE];
                pc.perses = perses;
                break;
        }
        switch (status) {
            case CREATION:
                tc.click(e, perses);
                break;
            case PLAYING:
                break;
            case END:
                return;
        }
        rc.render(perses, status);
        System.out.println("render");
    }

    void step() {
        for (int k = 1; k < 4; k++) {
            for (int j = 0; j < SIZE; j++) {
                for (int i = 0; i < SIZE; i++) {
                    pc.step(new Cell(i, j), k);
                }
            }
        }
        pc.uncheck();
        System.out.println("step");
    }

    void debugLoop() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                pc.step(new Cell(i, j), how);
            }
        }
        rc.render(perses, status);
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

    private void createMap(MouseEvent e) {
        tc.click(e, perses);
    }
}