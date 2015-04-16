package sample.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.models.Character;

public class RenderController implements Tile, Consts {

    GraphicsContext gc;

    public RenderController(GraphicsContext gc) {
        this.gc = gc;
    }

    void render(Character[][] perses, int status) {
        gc.clearRect(0, 0, 1024, 900);
        renderTiles(perses);
        renderButtons(status);
    }

    void renderPos(int x, int y) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(1 + x * TILE_SIZE, 1 + y * TILE_SIZE, TILE_SIZE - 2, TILE_SIZE - 2);
        gc.setFill(Color.BLACK);
        gc.fillText(x + ":" + y, TILE_SIZE * (x + 1), TILE_SIZE * y + 30);
    }

    void drawImage(Image i, int x, int y) {
        gc.drawImage(i, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
    }

    void renderDebug(Character p, int x, int y) {
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

    void renderTile(Character p, int x, int y) {
        gc.setStroke(Color.GREY);
        gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        if (p == null) {
            return;
        }
        switch (p.howIs()) {
            case Character.WOLF:
                drawImage(wolf, x, y);
                break;
            case Character.WOLFW:
                drawImage(wolf, x, y);
                break;
            case Character.RABBIT:
                drawImage(rabbit, x, y);
                break;
            default:
        }
        renderDebug(p, x, y);
    }

    void renderTiles(Character[][] perses) {
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
            case MainController.CREATION:
                drawImage(play, SIZE, 0);
                drawImage(clear, SIZE, 1);
                break;
            case MainController.PLAYING:
                drawImage(next, SIZE, 1);
                drawImage(update, SIZE, 2);
                break;
            case MainController.END:
                drawImage(update, SIZE, 2);
                break;
        }
    }
}
