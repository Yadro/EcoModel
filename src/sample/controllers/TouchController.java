package sample.controllers;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import sample.models.*;
import sample.models.Character;

public class TouchController implements Tile, Consts {

    /**
     * Handling mouse events.
     * @param e mouse event
     * @param characters array of the characters
     */
    void click(MouseEvent e, Character[][] characters) {
        Cell c = new Cell((int) e.getX() / TILE_SIZE,(int) e.getY() / TILE_SIZE);
        if (c.inField()) {
            if (characters[c.y][c.x] != null) {
                characters[c.y][c.x] = null;
            } else {
                characters[c.y][c.x] = createCharacterByButton(e.getButton());
            }
        }
    }

    Character createCharacterByButton(MouseButton button) {
        switch (button) {
            case PRIMARY: return new Rabbit();
            case SECONDARY: return new Wolf();
            case MIDDLE: return new WolfW();
            default: return new Character();
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
