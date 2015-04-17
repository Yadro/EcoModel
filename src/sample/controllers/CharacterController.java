package sample.controllers;

import sample.exceptions.CharacterIsDead;
import sample.models.*;
import sample.models.Character;

import java.util.ArrayList;
import java.util.Random;

/**
 * Processing move characters.
 */
public class CharacterController implements Consts {

    Random random = new Random(0);
    /* Characters array */
    Character[][] characters;

    /**
     * Save characters array.
     * @param characters array
     */
    public CharacterController(Character[][] characters) {
        this.characters = characters;
    }

    /**
     * Return character of the cell.
     * @param c cell
     * @return character found
     */
    Character getCharacter(Cell c) {
        return characters[c.y][c.x];
    }

    /**
     * Add new character by type.
     * @param cell cell
     * @param type type of character
     */
    void addCharacter(Cell cell, int type) {
        Character character;
        if (type == WOLF) {
            if (random.nextBoolean()) {
                character = new Wolf();
            } else {
                character = new WolfW();
            }
        } else {
            character = new Rabbit();
        }
        character.check();
        characters[cell.y][cell.x] = character;
        System.out.println("new on " + character + " is " + type);
    }

    /**
     * Step of character by type
     * @param pos character position
     * @param how type of character
     */
    public void step(Cell pos, int how) {
        Character character = getCharacter(pos);
        if (character != null && !character.checked) {
            switch (how) {
                case RABBIT:
                    if (!(character instanceof Rabbit)) return;
                    else break;
                case WOLF:
                    if (!(character instanceof Wolf)) return;
                    else break;
                case WOLFW:
                    if (!(character instanceof WolfW)) return;
                    else break;
            }
            Cell newPos = null;
            try {
                if (character instanceof Rabbit) {
                    newPos = rabbitStep((Rabbit) character, pos);
                } else if (character instanceof Wolf) {
                    newPos = wolfStep((Wolf) character, pos);
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

    /**
     * Handle step of rrabbit.
     * @param rabbit character
     * @param pos character position
     * @return new position
     */
    private Cell rabbitStep(Rabbit rabbit, Cell pos) {
        rabbit.check();
        if (random.nextInt(4) == 1 && getCells(pos, RABBIT).size() == 0) {
            Cell p = randomStep(pos);
            if (p != null) {
                addCharacter(p, RABBIT);
            }
            return null;
        }
        return randomStepZ(pos);
    }

    /**
     * Handle step of wolf.
     * @param wolf character
     * @param pos character position
     * @return new position
     * @throws CharacterIsDead
     */
    private Cell wolfStep(Wolf wolf, Cell pos) throws CharacterIsDead {
        wolf.check();
        Cell c = checkAround(pos, RABBIT);
        if (c != null) {
            wolf.eat();
            return c;
        }
        wolf.hungry();
        if (wolf instanceof WolfW) {
            WolfW wolfw = (WolfW) wolf;
            if (wolfw.isPregnant()) {
                wolfw.pregnant = false;
                Cell p = randomStep(pos);
                if (p != null) {
                    addCharacter(p, WOLF);
                }
                if (!wolf.isLive()) throw new CharacterIsDead();
                return null;
            }
        } else {
            c = checkAround(pos, WOLFW);
            if (c != null) {
                ((WolfW) getCharacter(c)).pregnant();
            }
        }
        if (!wolf.isLive()) throw new CharacterIsDead();
        return randomStep(pos);
    }

    /**
     * Find characters around cell.
     */
    private Cell checkAround(Cell pos, int type) {
        ArrayList<Integer> steps = getCells(pos, type);
        if (steps.size() == 0) return null;
        steps.forEach(System.out::print);
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        return new Cell(steps.get(stepDir)).add(pos);
    }

    /**
     * Make random step.
     * @param pos cell
     * @return new position
     */
    private Cell randomStep(Cell pos) {
        ArrayList<Integer> steps = getCells(pos, 0);
        if (steps.size() == 0) return null;
        steps.forEach(System.out::print);
        int stepDir = random.nextInt(steps.size());
        System.out.println("->" + steps.get(stepDir));
        return pos.and(new Cell(steps.get(stepDir)));
    }

    /**
     * Make random step or stand still.
     * @param pos cell
     * @return new position
     */
    private Cell randomStepZ(Cell pos) {
        ArrayList<Integer> steps = getCells(pos, 0);
        if (steps.size() == 0) return null;
        steps.add(0);
        int stepDir = random.nextInt(steps.size());
        steps.forEach(System.out::print);
        System.out.println("->" + steps.get(stepDir));
        if (steps.get(stepDir) == 0) return null;
        return pos.and(new Cell(steps.get(stepDir)));
    }

    /**
     * Find all the fields around the cell containing the character.
     * @param pos cell
     * @param type type of character
     * @return all place containing the character.
     */
    private ArrayList<Integer> getCells(Cell pos, int type) {
        ArrayList<Integer> steps = new ArrayList<>(7);
        for (int i = 1; i < 9; i++) {
            Cell c = new Cell(i).add(pos);
            if (c.inField()) {
                Character character = getCharacter(c);
                if (type > 0) {
                    if (character != null) {
                        switch (type) {
                            case WOLF:
                                if (character instanceof Wolf) {
                                    System.out.println("find on " + i + ": " + type);
                                    steps.add(i);
                                }
                                break;
                            case WOLFW:
                                if (character instanceof WolfW) {
                                    System.out.println("find on " + i + ": " + type);
                                    steps.add(i);
                                }
                                break;
                            case RABBIT:
                                if (character instanceof Rabbit) {
                                    System.out.println("find on " + i + ": " + type);
                                    steps.add(i);
                                }
                                break;
                        }
                    }
                } else {
                    if (character == null) {
                        steps.add(i);
                    }
                }
            }
        }
        return steps;
    }

    /**
     * Remove the flag check.
     */
    void uncheck() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                if (characters[i][j] != null) characters[i][j].checked = false;
            }
        }
    }
}
