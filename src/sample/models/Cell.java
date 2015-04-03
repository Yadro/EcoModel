package sample.models;

import sample.controllers.MainController;

public class Cell {

    public static final int SIZE = MainController.SIZE;

    private static final int LEFT_UP = 1;
    private static final int UP = 2;
    private static final int RIGHT_UP = 3;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;
    private static final int LEFT_DOWN = 6;
    private static final int DOWN = 7;
    private static final int RIGHT_DOWN = 8;

    public int x;
    public int y;

    public Cell() {
        this.x = 0;
        this.y = 0;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(Cell c) {
        this.x = c.x;
        this.y = c.y;
    }

    public Cell(int dir) {
        switch (dir) {
            case LEFT_UP:
                this.x = -1;
                this.y = -1;
                break;
            case UP:
                this.x = 0;
                this.y = -1;
                break;
            case RIGHT_UP:
                this.x = 1;
                this.y = -1;
                break;
            case LEFT:
                this.x = -1;
                this.y = 0;
                break;
            case RIGHT:
                this.x = 1;
                this.y = 0;
                break;
            case LEFT_DOWN:
                this.x = -1;
                this.y = 1;
                break;
            case DOWN:
                this.x = 0;
                this.y = 1;
                break;
            case RIGHT_DOWN:
                this.x = 1;
                this.y = 1;
                break;
            default:
                this.x = 0;
                this.y = 0;
                System.out.println("error dir");
        }
    }

    public Cell add(Cell c) {
        this.x += c.x;
        this.y += c.y;
        return this;
    }

    /**
     * Клетка за пределами поля?
     * @return
     */
    public boolean inField() {
        return !(y < 0 || x < 0 || x > SIZE - 1 || y > SIZE - 1);
    }
}
