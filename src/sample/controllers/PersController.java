package sample.controllers;

import sample.exceptions.PersIsDead;
import sample.models.Cell;
import sample.models.Pers;

import java.util.ArrayList;
import java.util.Random;

public class PersController implements Consts {

    Random random = new Random(0);
    Pers[][] perses;

    public PersController(Pers[][] perses) {
        this.perses = perses;
    }

    Pers getPers(Cell c) {
        return perses[c.y][c.x];
    }

    void addPers(Cell c, int type) {
        Pers p;
        if (type == Pers.WOLF) {
            if (random.nextBoolean()) {
                p = new Pers(Pers.WOLF);
            } else {
                p = new Pers(Pers.WOLFW);
            }
        } else {
            p = new Pers(type);
        }
        p.check();
        perses[c.y][c.x] = p;
        System.out.println("new on " + c + " is " + type);
    }

    public void step(Cell pos, int how) {
        Pers pers = getPers(pos);
        if (pers != null && !pers.checked) {
            Cell newPos;
            switch (how) {
                case Pers.RABBIT:
                    if (pers.howIs() != Pers.RABBIT) return;
                    break;
                case Pers.WOLF:
                    if (pers.howIs() != Pers.WOLF) return;
                    break;
                case Pers.WOLFW:
                    if (pers.howIs() != Pers.WOLFW) return;
                    break;
                default:
                    break;
            }
            try {
                if (pers.howIs() == Pers.RABBIT) {
                    newPos = rabbitStep(pers, pos);
                } else {
                    newPos = wolfStep(pers, pos);
                }
                if (newPos != null) {
                    perses[newPos.y][newPos.x] = pers;
                    perses[pos.y][pos.x] = null;
                }
            } catch (PersIsDead e) {
                perses[pos.y][pos.x] = null;
            }
        }
    }

    private Cell rabbitStep(Pers rabbit, Cell pos) {
        rabbit.check();
        if (random.nextInt(4) == 1) {
            Cell p = randomStep(pos);
            if (p != null) {
                addPers(p, Pers.RABBIT);
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
            }
        } else {
            if (wolf.pregnant) {
                wolf.pregnant = false;
                Cell p = randomStep(pos);
                if (p != null) {
                    addPers(p, Pers.WOLF);
                }
                if (!wolf.isLive()) throw new PersIsDead();
                return null;
            }
        }
        if (!wolf.isLive()) throw new PersIsDead();
        return randomStep(pos);
    }

    private Cell checkAround(Cell pos, int type) {
        ArrayList<Integer> steps = getPlaces(pos, type);
        if (steps.size() == 0) return null;
        steps.forEach(System.out::print);
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        return new Cell(steps.get(stepDir)).add(pos);
    }

    private Cell randomStep(Cell pos) {
        ArrayList<Integer> steps = getPlaces(pos, -1);
        if (steps.size() == 0) return null;
        steps.forEach(System.out::print);
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        return pos.and(new Cell(steps.get(stepDir)));
    }

    private Cell randomStepZ(Cell pos) {
        ArrayList<Integer> steps = getPlaces(pos, -1);
        if (steps.size() == 0) return null;
        steps.add(0);
        int stepDir = random.nextInt(steps.size());
        steps.forEach(System.out::print);
        System.out.println("->" + steps.get(stepDir));
        if (steps.get(stepDir) == 0) return null;
        return pos.and(new Cell(steps.get(stepDir)));
    }

    private ArrayList<Integer> getPlaces(Cell pos, int type) {
        ArrayList<Integer> steps = new ArrayList<>(7);
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(pos);
            if (c.inField()) {
                Pers p = getPers(c);
                if (type >= 0) {
                    if (p != null && p.howIs() == type) {
                        System.out.println("find on " + i + ": " + type);
                        steps.add(i);
                    }
                } else {
                    if (p == null) {
                        steps.add(i);
                    }
                }
            }
        }
        return steps;
    }

    void uncheck() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (perses[i][j] != null) perses[i][j].checked = false;
            }
        }
    }
}
