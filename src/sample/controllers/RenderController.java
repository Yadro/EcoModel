package sample.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.models.Pers;

public class RenderController implements Tile {

    GraphicsContext gc;

    public RenderController(GraphicsContext gc) {
        this.gc = gc;
    }

    void render(Pers[][] perses) {
        gc.clearRect(0, 0, 1024, 900);
        renderTiles(perses);
    }

    void renderPos(int x, int y) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(1 + x * TILE_SIZE, 1 + y * TILE_SIZE, TILE_SIZE - 2, TILE_SIZE - 2);
        gc.setFill(Color.BLACK);
        gc.fillText(x + ":" + y, TILE_SIZE * (x + 1), TILE_SIZE * y + 30);
    }

    void renderButton() {
        gc.drawImage(wolf, TILE_SIZE * 21, TILE_SIZE * 25, TILE_SIZE, TILE_SIZE);
    }

    void renderDebug(Pers p, int x, int y) {
        gc.setFill(Color.BLACK);
        if (p.howIs() != 3) {
            gc.fillText(String.valueOf(p.half), TILE_SIZE * x, TILE_SIZE * (y + 1));
        }
        if (p.howIs() == 2) {
            if (p.pregnant) {
                gc.fillText("W", TILE_SIZE * x + 38, TILE_SIZE * (y + 1));
            } else {
                gc.fillText("w", TILE_SIZE * x + 38, TILE_SIZE * (y + 1));
            }
        }
        gc.fillText((p.checked) ? "1" : "0", TILE_SIZE * x, TILE_SIZE * y + 10);
    }

    void renderTile(Pers p, int x, int y) {
        gc.setStroke(Color.GREY);
        gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        if (p == null) {
            return;
        }
        switch (p.howIs()) {
            case Pers.WOLF:
                gc.drawImage(wolf, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
                break;
            case Pers.WOLFW:
                gc.drawImage(wolf, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
                break;
            case Pers.RABBIT:
                gc.drawImage(rabbit, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
                break;
            default:
        }
        renderDebug(p, x, y);
    }

    void renderTiles(Pers[][] perses) {
        int lengthY = perses.length;
        int lengthX = perses[0].length;
        for (int y = 0; y < lengthY; y++) {
            for (int x = 0; x < lengthX; x++) {
                if (perses[y][x] != null) {
                    renderTile(perses[y][x], x, y);
                } else {
                    renderTile(null, x, y);
                }
            }
        }
    }

    void renderButtons(int status) {
        switch (status) {
            case MainController.CREATE:
                gc.drawImage(play, 20 * TILE_SIZE, 0);
                break;
            case MainController.PLAYED:
                gc.drawImage(next, 20 * TILE_SIZE, 0);
                gc.drawImage(update, 20 * TILE_SIZE, 1);
                break;
            case MainController.END:
                gc.drawImage(update, 20 * TILE_SIZE, 0);
                break;
        }
    }
}
