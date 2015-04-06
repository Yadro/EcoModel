package sample.controllers;

import sample.exceptions.PersIsDead;
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

    public void step(Cell pos, boolean first) {
        int i = pos.x, j = pos.y;
        pos = new Cell(pos);
        Pers pers = getPers(pos);

        if (pers != null && !pers.checked) {
            if (first) {
                if (pers.howIs() == Pers.RABBIT) return;
            } else {
                if (pers.howIs() == Pers.WOLF) return;
            }
            try {
                Cell newPos;
                if (pers.howIs() == Pers.RABBIT) {
                    newPos = rabbitStep(pers, pos);
                } else {
                    newPos = wolfStep(pers, pos);
                }
                if (newPos != null) {
                    perses[newPos.y][newPos.x] = pers;
                    perses[j][i] = null;
                }
            } catch (PersIsDead e) {
                perses[j][i] = null;
            }
        }
    }

    private Cell rabbitStep(Pers rabbit, Cell pos) {
        rabbit.check();
        if (random.nextInt(4) == 1) {
            Cell l = randomStep(pos);
            if (l != null) {
                perses[l.y][l.x] = new Pers(Pers.RABBIT);
            }
            return null;
        }
        return randomStepZ(pos);
    }

    private Cell wolfStep(Pers wolf, Cell pos) throws PersIsDead {
        wolf.check();
        Cell c = checkAround(pos, Pers.RABBIT);
        if (c != null) {
            wolf.eat();
            return c;
        }
        wolf.hungry();

        if (wolf.howIs() == Pers.WOLF) {
            c = checkAround(pos, Pers.WOLFW);
            if (c != null) {
                getPers(c).pregnant = true;
                return null;
            }
        } else {
            if (wolf.pregnant) {
                wolf.pregnant = false;
                Cell l = randomStep(pos);
                if (l != null) {
                    if (random.nextBoolean()) {
                        perses[l.y][l.x] = new Pers(Pers.WOLF);
                    } else {
                        perses[l.y][l.x] = new Pers(Pers.WOLFW);
                    }
                }
                if (!wolf.isLive()) throw new PersIsDead();
                return null;
            }
        }
        if (!wolf.isLive()) throw new PersIsDead();
        return randomStep(pos);
    }

    private Cell checkAround(Cell cell, int type) {
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(cell);
            if (c.inField()) {
                Pers p = getPers(c);
                if (p != null && p.howIs() == type) {
                    System.out.println("find on " + i + ": " + type);
                    return c;
                }
            }
        }
        return null;
    }

    private Cell randomStep(Cell pos) {
        ArrayList<Integer> steps = new ArrayList<>(9);
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(pos);
            if (c.inField()) {
                if (getPers(c) == null) {
                    steps.add(i);
                }
            }
        }
        if (steps.size() == 0) return null;
        steps.forEach(System.out::print);
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        return pos.add(new Cell(steps.get(stepDir)));
    }

    private Cell randomStepZ(Cell pos) {
        ArrayList<Integer> steps = new ArrayList<>(9);
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(pos);
            if (c.inField()) {
                if (getPers(c) == null) {
                    steps.add(i);
                }
            }
        }
        steps.forEach(System.out::print);
        if (steps.size() == 0) return null;
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        if (stepDir == 0) return null;
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
