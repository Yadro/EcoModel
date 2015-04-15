package sample.controllers;

import sample.models.Cell;
import sample.models.Pers;

public class TouchController implements Tile {

    Pers[][] perses;

    public TouchController(Pers[][] perses) {
        this.perses = perses;
    }

    void click(int x, int y, int type) {
        Cell c = new Cell(x / TILE_SIZE, y / TILE_SIZE);
        if (c.inField()) {
            if (perses[c.y][c.x] != null) {
                perses[c.y][c.x] = null;
            } else {
                perses[c.y][c.x] = new Pers(type);
            }
        }
    }

    void clickButton(Cell click) {
        if (click.x == 20) {
            if (click.y == 0) {

            }
            if (click.y == 1) {

            }
            if (click.y == 2) {

            }
        }
    }
}
