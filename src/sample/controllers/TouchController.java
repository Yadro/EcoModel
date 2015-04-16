package sample.controllers;

import javafx.scene.input.MouseEvent;

import sample.models.Cell;
import sample.models.Character;

public class TouchController implements Tile, Consts {

    /**
     * Handling mouse events.
     * @param e mouse event
     * @param characters array of the characters
     */
    void click(MouseEvent e, Character[][] characters) {
        Cell c = new Cell((int) e.getX() / TILE_SIZE,(int) e.getY() / TILE_SIZE);
        int type = 0;
        switch (e.getButton()) {
            case PRIMARY: type = Character.RABBIT; break;
            case MIDDLE: type = Character.WOLFW; break;
            case SECONDARY: type = Character.WOLF; break;
        }
        if (c.inField()) {
            if (characters[c.y][c.x] != null) {
                characters[c.y][c.x] = null;
            } else {
                characters[c.y][c.x] = new Character(type);
            }
        }
    }

    /**
     * Handling mouse events
     * @param e mouse event
     * @param status application status
     * @return event
     */
    int clickButton(MouseEvent e, int status) {
        int x = (int) e.getX() / TILE_SIZE,
            y = (int) e.getY() / TILE_SIZE;
        if (x == 20) {
            if (status == CREATION) {
                if (y == 0) {
                    return NEW_GAME;
                }
                if (y == 1) {
                    return CLEAR;
                }
            }
            if (y == 1 && status == PLAYING) {
                return NEXT_STEP;
            }
            if (y == 2 && status != CREATION) {
                return AGAIN;
            }
        }
        return 0;
    }
}
