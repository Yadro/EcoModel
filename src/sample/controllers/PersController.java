package sample.controllers;

import sample.models.Cell;
import sample.models.Pers;
import java.util.Random;

public class PersController {

    Random random = new Random();
    public final int SIZE;
    Pers[][] perses;

    public PersController(int SIZE, Pers[][] perses) {
        this.SIZE = SIZE;
        this.perses = perses;
    }

    Pers getPers(Cell c) {
        return perses[c.x][c.y];
    }

    public void steps() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                Pers pers = perses[j][i];
                if (pers != null && !pers.checked) {
                    Cell newCell;
                    Pers next;
                    switch (pers.howIs()) {
                        case Pers.RABBIT:
                            newCell = rabbitStep(new Cell(i, j));
                            if (newCell == null) {
                                pers.checked = true;
                                continue;
                            }
                            next = perses[newCell.y][newCell.x];
                            if (next != null && next.howIs() == Pers.RABBIT) {
                                pers.checked = true;
                                continue;
                            }
                            pers.check();
                            perses[newCell.y][newCell.x] = pers;
                            perses[j][i] = null;
                            break;

                        case Pers.WOLF:
                            newCell = wolfStep(pers, new Cell(i, j));
                            if (newCell == null) {
                                pers.checked = true;
                                continue;
                            }
                            next = perses[newCell.y][newCell.x];
                            if (next != null && next.howIs() == Pers.RABBIT) {
                                pers.eat();
                            } else {
                                pers.hungry();
                            }
                            if (!pers.isLive()) {
                                perses[j][i] = null;
                                continue;
                            }
                            pers.check();
                            perses[newCell.y][newCell.x] = pers;
                            perses[j][i] = null;
                            break;
                        default:
                            System.out.println("wat");
                    }
                }
            }
        }
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[j][i] != null) perses[j][i].checked = false;
            }
        }
    }

    public void step(Cell pos) {
        int i = pos.x, j = pos.y;

        Pers pers = getPers(pos);
        if (pers != null && !pers.checked) {
            Cell newPos;
            Pers next;
            switch (pers.howIs()) {
                case Pers.RABBIT:
                    newPos = rabbitStep(pos);
                    if (newPos == null) {
                        pers.checked = true;
                        return;
                    }
                    next = perses[newPos.y][newPos.x];
                    if (next != null && next.howIs() == Pers.RABBIT) {
                        pers.checked = true;
                        return;
                    }
                    pers.check();
                    perses[newPos.y][newPos.x] = pers;
                    perses[j][i] = null;
                    break;

                case Pers.WOLF:
                    newPos = wolfStep(pers, pos);
                    if (newPos == null) {
                        pers.checked = true;
                        return;
                    }
                    next = perses[newPos.y][newPos.x];
                    if (next != null && next.howIs() == Pers.RABBIT) {
                        pers.eat();
                    } else {
                        pers.hungry();
                    }
                    if (!pers.isLive()) {
                        perses[j][i] = null;
                        return;
                    }
                    pers.check();
                    perses[newPos.y][newPos.x] = pers;
                    perses[j][i] = null;
                    break;
                default:
                    System.out.println("wat");
            }
        }
    }

    private Cell checkAround(Cell cell, int type) {
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(cell);
            if (c.inField()) {
                Pers p = getPers(c);
                if (p != null && p.howIs() == type) {
                    return c;
                }
            }
        }
        return null;
    }

    private Cell _step(Cell cell, int dir) {
        Cell newPos = cell.add(new Cell(dir));
        return newPos.inField() ? newPos : null;
    }

    private Cell rabbitStep(Cell cell) {
        int dir = random.nextInt(8);
        return _step(cell, dir);
    }

    private Cell wolfStep(Pers wolf, Cell pos) {
        Cell c = checkAround(pos, Pers.RABBIT);
        if (c != null) {
            wolf.eat();
            return c;
        }
        wolf.hungry();

        if (wolf.howIs() == Pers.WOLF) {
            c = checkAround(pos, Pers.WOLFW);
        } else {
            c = checkAround(pos, Pers.WOLF);
        }
        if (c != null) {
            return pos;
        }
        int dir = 1 + random.nextInt(7);
        return _step(pos, dir);
    }
}
