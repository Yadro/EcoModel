package sample.controllers;

import sample.exceptions.CharacterIsDead;
import sample.models.Cell;
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
     * @param c cell
     * @param type type of character
     */
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

    /**
     * Step of character by type
     * @param pos character position
     * @param how type of character
     */
    public void step(Cell pos, int how) {
        Character character = getCharacter(pos);
        if (character != null && !character.checked) {
            Cell newPos;
            switch (how) {
                case Character.RABBIT: if (character.howIs() != Character.RABBIT) return; else break;
                case Character.WOLF:   if (character.howIs() != Character.WOLF)   return; else break;
                case Character.WOLFW:  if (character.howIs() != Character.WOLFW)  return; else break;
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

    /**
     * Handle step of rrabbit.
     * @param rabbit character
     * @param pos character position
     * @return new position
     */
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

    /**
     * Handle step of wolf.
     * @param wolf character
     * @param pos character position
     * @return new position
     * @throws CharacterIsDead
     */
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

    /**
     * Find characters around cell.
     */
    private Cell checkAround(Cell pos, int type) {
        ArrayList<Integer> steps = getPlaces(pos, type);
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
        ArrayList<Integer> steps = getPlaces(pos, -1);
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
        ArrayList<Integer> steps = getPlaces(pos, -1);
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
