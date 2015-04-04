package sample.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.models.Pers;

public class RenderController {

    GraphicsContext gc;

    static final int TILE_SIZE = 45;

    final Image wolf = new Image("wolf_vec2.png");
    final Image rabbit = new Image("rabbit.png");

    public RenderController(GraphicsContext gc) {
        this.gc = gc;
    }

    void render(Pers[][] perses) {
        gc.clearRect(0, 0, 1024, 900);
        renderTiles(perses);
//        gc.restore();
//        gc.stroke();
    }

    void renderRect(int x, int y) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(1 + x * TILE_SIZE, 1 + y * TILE_SIZE, TILE_SIZE - 2, TILE_SIZE - 2);
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
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(p.half), TILE_SIZE * x, TILE_SIZE * (y + 1));
                break;
            case Pers.WOLFW:
                gc.drawImage(wolf, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(p.half), TILE_SIZE * x, TILE_SIZE * (y + 1));
                break;
            case Pers.RABBIT:
                gc.drawImage(rabbit, TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE);
                break;
            default:
        }
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
}
