package sample.controllers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.models.Cell;
import sample.models.Character;

/**
 * Main class.
 */
public class MainController extends Application implements Consts {

    /* Application status */
    public int status = CREATION;

    /* Characters array */
    Character[][] characters = new Character[SIZE][SIZE];
    /* Saved characters */
    Character[][] save = new Character[SIZE][SIZE];

    RenderController rc;
    GraphicsContext gc;
    CharacterController pc;
    TouchController tc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EventHandler<MouseEvent> mouseEventHandler = this::loop;
        Canvas canvas = new Canvas(1024, 900);
        canvas.setOnMouseClicked(mouseEventHandler);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
        rc = new RenderController(gc);
        tc = new TouchController();
        pc = new CharacterController(characters);
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setTitle("EcoModel");
        stage.setScene(new Scene(root));
        stage.show();
        rc.render(characters, status);
    }

    /**
     * Application loop.
     * @param e mouse event
     */
    void loop(MouseEvent e) {
        switch (tc.clickButton(e, status)) {
            case NEW_GAME: startGame(); break;
            case NEXT_STEP: makeStep(); break;
            case AGAIN: beginNewGame(); break;
            case CLEAR:   clearField(); break;
        }
        switch (status) {
            case CREATION: tc.click(e, characters); break;
            case PLAYING: break;
            case END: break;
        }
        rc.render(characters, status);
    }

    /**
     * Move all the characters.
     */
    void step() {
        for (int k = 1; k < 4; k++) {
            for (int j = 0; j < SIZE; j++) {
                for (int i = 0; i < SIZE; i++) {
                    pc.step(new Cell(i, j), k);
                }
            }
        }
        pc.uncheck();
    }

    // todo не работает
    void restore() {
        characters = new Character[SIZE][SIZE];
        System.arraycopy(save, 0, characters, 0, save.length);
        pc.characters = characters;
    }

    /**
     * Start processing steps.
     */
    void startGame() {
        status = PLAYING;
        System.arraycopy(characters, 0, save, 0, characters.length);
    }

    /**
     * A step of all the characters.
     */
    void makeStep() {
        step();
        if (characterIsDead()) {
            status = END;
        }
    }

    /**
     * Begin new game.
     */
    void beginNewGame() {
        status = CREATION;
        restore();
    }

    /**
     * Clear field.
     */
    void clearField() {
        characters = new Character[SIZE][SIZE];
        pc.characters = characters;
    }

    /**
     * Checks whether characters left.
     * @return answer
     */
    boolean characterIsDead() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (characters[j][i] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}