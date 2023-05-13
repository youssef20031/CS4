package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

import java.awt.*;

import static engine.Game.heroes;
import static engine.Game.map;

public abstract class Character {
    private String name;
    private Point location;
    private int maxHp;
    private int currentHp;
    private int attackDmg;
    private Character target;

    public Character(){
        super();
    }
    public Character(String name, int maxHp, int attackDmg){
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attackDmg = attackDmg;
    }
    public Character(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        if(currentHp > maxHp){
            this.currentHp = maxHp;
        } else if (currentHp <= 0) {
            this.currentHp = 0;
            onCharacterDeath();
        }else {
            this.currentHp = currentHp;
        }
    }

    public Character getTarget() {
        return target;
    }

    public void setTarget(Character target) {
        this.target = target;
    }
    public void onCharacterDeath(){
        if(map[location.x][location.y] instanceof CharacterCell) {
            CharacterCell a = (CharacterCell) map[location.x][location.y];
            a.setCharacter(null);
        }
    }
    public boolean adjacent(Character c){
        /*return ((c.location.x == location.x)&(c.location.y+1 == location.y)) ||
                ((c.location.x == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y+1 == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y+1 == location.y));*/
        return Math.abs(location.x - c.location.x) <= 1 & Math.abs(location.y - c.location.y) <= 1;
    }
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        if(adjacent(target)) {
            target.setCurrentHp(target.getCurrentHp() - this.attackDmg);
            this.defend(target);
        }
        else {
            throw new InvalidTargetException("Target is not adjacent");
        }
    }
    public void defend(Character c) {
        c.setTarget(this);
        this.setCurrentHp(this.getCurrentHp()-(c.getAttackDmg()/2));
    }
}
