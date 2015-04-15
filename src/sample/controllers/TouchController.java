package sample.controllers;

import javafx.scene.input.MouseEvent;
import sample.models.Cell;
import sample.models.Pers;

public class TouchController implements Tile {

    static final int NEW_GAME = 1;
    static final int NEXT_STEP = 2;
    static final int AGAIN = 3;

    Pers[][] perses;

    public TouchController(Pers[][] perses) {
        this.perses = perses;
    }

    void click(MouseEvent e) {
        Cell c = new Cell((int) e.getX() / TILE_SIZE,(int) e.getY() / TILE_SIZE);
        int type = 0;
        switch (e.getButton()) {
            case PRIMARY: type = Pers.RABBIT; break;
            case MIDDLE: type = Pers.WOLFW; break;
            case SECONDARY: type = Pers.WOLF; break;
        }
        if (c.inField()) {
            if (perses[c.y][c.x] != null) {
                perses[c.y][c.x] = null;
            } else {
                perses[c.y][c.x] = new Pers(type);
            }
        }
    }

    int clickButton(MouseEvent e, int status) {
        int x = (int) e.getX() / TILE_SIZE,
            y = (int) e.getY() / TILE_SIZE;
        if (x == 20) {
            if (y == 0 && status == MainController.CREATION) {
                return NEW_GAME;
            }
            if (y == 1 && status == MainController.PLAYING) {
                return NEXT_STEP;
            }
            if (y == 2 && status != MainController.CREATION) {
                return AGAIN;
            }
        }
        return 0;
    }
}
