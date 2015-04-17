package sample.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import sample.models.Character;
import sample.models.Rabbit;
import sample.models.Wolf;
import sample.models.WolfW;

public class RenderController implements Tile, Consts {

    GraphicsContext gc;

    public RenderController(GraphicsContext gc) {
        this.gc = gc;
    }

    void render(Character[][] characters, int status) {
        gc.clearRect(0, 0, 1024, 900);
        renderTiles(characters);
        renderButtons(status);
        renderCount(characters);
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

    void drawText(String str, int x, int y) {
        gc.setFill(Color.BLACK);
        gc.fillText(str, TILE_SIZE * x, TILE_SIZE * y);
    }

    void renderDebug(Character p, int x, int y) {
        gc.setFill(Color.BLACK);
        if (p instanceof Wolf) {
            gc.fillText(String.valueOf(((Wolf) p).half), TILE_SIZE * x, TILE_SIZE * (y + 1));
        }
        if (p instanceof WolfW) {
            if (((WolfW) p).pregnant) {
                gc.fillText("W", TILE_SIZE * x + 35, TILE_SIZE * (y + 1));
            } else {
                gc.fillText("w", TILE_SIZE * x + 35, TILE_SIZE * (y + 1));
            }
        }
    }

    void renderTile(Character character, int x, int y) {
        gc.setStroke(Color.GREY);
        gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        if (character == null) {
            return;
        }
        if (character instanceof Wolf) {
            drawImage(wolf, x, y);
        } else if (character instanceof Rabbit) {
            drawImage(rabbit, x, y);
        }
        renderDebug(character, x, y);
    }

    void renderTiles(Character[][] characters) {
        int lengthY = characters.length;
        int lengthX = characters[0].length;
        for (int y = 0; y < lengthY; y++) {
            for (int x = 0; x < lengthX; x++) {
                if (characters[y][x] != null) {
                    renderTile(characters[y][x], x, y);
                } else {
                    renderTile(null, x, y);
                }
            }
        }
    }

    void renderButtons(int status) {
        switch (status) {
            case CREATION:
                drawImage(play, SIZE, 0);
                drawImage(clear, SIZE, 1);
                break;
            case PLAYING:
                drawImage(next, SIZE, 1);
                drawImage(update, SIZE, 2);
                break;
            case END:
                drawImage(update, SIZE, 2);
                break;
        }
    }

    void renderCount(Character[][] characters) {
        int wolf = 0, wolfw = 0, sWolf = 0, rabbit = 0;
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (characters[j][i] instanceof Rabbit) rabbit++;
                else if (characters[j][i] instanceof WolfW) {
                    wolfw++;
                    if (((WolfW) characters[j][i]).isPregnant()) {
                        sWolf++;
                    }
                } else if (characters[j][i] instanceof Wolf) wolf++;
            }
        }
        drawText("rabbit: " + rabbit, 20, 4);
        drawText("wolf: " + wolf + ((sWolf > 0) ? " + " + sWolf : ""), 20, 5);
        drawText("wolfw: " + wolfw, 20, 6);
    }
}
