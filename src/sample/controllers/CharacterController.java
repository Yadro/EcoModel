package sample.controllers;

import sample.exceptions.CharacterIsDead;
import sample.models.Cell;
import sample.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class CharacterController implements Consts {

    Random random = new Random(0);
    Character[][] characters;

    public CharacterController(Character[][] characters) {
        this.characters = characters;
    }

    Character getCharacter(Cell c) {
        return characters[c.y][c.x];
    }

    void addCharacter(Cell c, int type) {
        Character p;
        if (type == Character.WOLF) {
            if (random.nextBoolean()) {
                p = new Character(Character.WOLF);
            } else {
                p = new Character(Character.WOLFW);
            }
        } else {
            p = new Character(type);
        }
        p.check();
        characters[c.y][c.x] = p;
        System.out.println("new on " + c + " is " + type);
    }

    public void step(Cell pos, int how) {
        Character character = getCharacter(pos);
        if (character != null && !character.checked) {
            Cell newPos;
            switch (how) {
                case Character.RABBIT:
                    if (character.howIs() != Character.RABBIT) return;
                    break;
                case Character.WOLF:
                    if (character.howIs() != Character.WOLF) return;
                    break;
                case Character.WOLFW:
                    if (character.howIs() != Character.WOLFW) return;
                    break;
                default:
                    break;
            }
            try {
                if (character.howIs() == Character.RABBIT) {
                    newPos = rabbitStep(character, pos);
                } else {
                    newPos = wolfStep(character, pos);
                }
                if (newPos != null) {
                    characters[newPos.y][newPos.x] = character;
                    characters[pos.y][pos.x] = null;
                }
            } catch (CharacterIsDead e) {
                characters[pos.y][pos.x] = null;
            }
        }
    }

    private Cell rabbitStep(Character rabbit, Cell pos) {
        rabbit.check();
        if (random.nextInt(4) == 1) {
            Cell p = randomStep(pos);
            if (p != null) {
                addCharacter(p, Character.RABBIT);
            }
            return null;
        }
        return randomStepZ(pos);
    }

    private Cell wolfStep(Character wolf, Cell pos) throws CharacterIsDead {
        wolf.check();
        Cell c = checkAround(pos, Character.RABBIT);
        if (c != null) {
            wolf.eat();
            return c;
        }
        wolf.hungry();

        if (wolf.howIs() == Character.WOLF) {
            c = checkAround(pos, Character.WOLFW);
            if (c != null) {
                getCharacter(c).pregnant = true;
            }
        } else {
            if (wolf.pregnant) {
                wolf.pregnant = false;
                Cell p = randomStep(pos);
                if (p != null) {
                    addCharacter(p, Character.WOLF);
                }
                if (!wolf.isLive()) throw new CharacterIsDead();
                return null;
            }
        }
        if (!wolf.isLive()) throw new CharacterIsDead();
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
                Character p = getCharacter(c);
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
                if (characters[i][j] != null) characters[i][j].checked = false;
            }
        }
    }
}
