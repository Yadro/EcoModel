package sample.controllers;

import sample.models.Cell;
import sample.models.Pers;

import java.util.ArrayList;
import java.util.Random;

public class PersController {

    Random random = new Random(0);
    public final int SIZE;
    Pers[][] perses;

    public PersController(int SIZE, Pers[][] perses) {
        this.SIZE = SIZE;
        this.perses = perses;
    }

    Pers getPers(Cell c) {
        return perses[c.y][c.x];
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
                            newCell = rabbitStep(pers, new Cell(i, j));
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

    public void step(Cell pos, boolean first) {
        int i = pos.x, j = pos.y;
        pos = new Cell(pos);
        Pers pers = getPers(pos);

        if (pers != null && !pers.checked) {
            System.out.println(getPers(pos).howIs());
            if (first) {
                if (pers.howIs() == Pers.RABBIT) return;
            } else {
                if (pers.howIs() == Pers.WOLF) return;
            }
            Cell newPos;
//            Pers next;
            switch (pers.howIs()) {
                case Pers.RABBIT:
                    newPos = rabbitStep(pers, pos);
                    /*if (newPos == null) {
                        pers.checked = true;
                        return;
                    }
                    next = perses[newPos.y][newPos.x];
                    if (next != null && next.howIs() == Pers.RABBIT) {
                        pers.checked = true;
                        return;
                    }
                    pers.check();*/
                    perses[newPos.y][newPos.x] = pers;
                    perses[j][i] = null;
                    break;

                case Pers.WOLF:
                    newPos = wolfStep(pers, pos);
                    /*if (newPos == null) {
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
                    pers.check();*/
                    perses[newPos.y][newPos.x] = pers;
                    perses[j][i] = null;
                    break;
                default:
                    System.out.println("wat");
            }
        }
    }

    private Cell rabbitStep(Pers rabbit, Cell pos) {
        rabbit.check();
        if (random.nextInt(4) == 0) {
            Cell l = randomStep(pos, false);
            perses[l.y][l.x] = new Pers(Pers.RABBIT);
            return pos;
        }
        return randomStep(pos, true);
    }

    private Cell wolfStep(Pers wolf, Cell pos) {
        wolf.check();
        Cell c = checkAround(pos, Pers.RABBIT);
        if (c != null) {
            wolf.eat();
            return c;
        }
        wolf.hungry();

        if (wolf.howIs() == Pers.WOLF) {
            c = checkAround(pos, Pers.WOLFW);
        }
        if (c != null) {
            getPers(c).pregnant = true;
            return pos;
        }
        return randomStep(pos, false);
    }

    private Cell checkAround(Cell cell, int type) {
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(cell);
            if (c.inField()) {
                Pers p = getPers(c);
                if (p != null && p.howIs() == type) {
                    System.out.println("find: " + type);
                    return c;
                }
            }
        }
        return null;
    }

    private Cell randomStep(Cell pos, boolean stay) {
        ArrayList<Integer> steps = new ArrayList<>(9);
        if (stay) {
            steps.add(0);
        }
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(pos);
            if (c.inField()) {
                if (getPers(c) == null) {
                    steps.add(i);
                }
            }
        }
        if (steps.size() <= 1) {
            return pos;
        }

        int stepDir;
        if (stay) {
            stepDir = random.nextInt(steps.size() - 1);
        } else {
            stepDir = 1 + random.nextInt(steps.size() - 2);
        }
        return pos.add(new Cell(steps.get(stepDir)));
    }

    void uncheck() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[i][j] != null) perses[i][j].checked = false;
            }
        }
    }
}
