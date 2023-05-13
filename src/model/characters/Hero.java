package model.characters;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

import java.awt.*;
import java.util.ArrayList;

import static engine.Game.*;

public abstract class Hero extends Character{
    private int actionsAvailable;
    private int maxActions;
    private boolean specialAction;
    private ArrayList<Vaccine> vaccineInventory;
    private ArrayList<Supply> supplyInventory;

    public Hero(String name, int maxHp, int attackDmg, int maxActions){
        super(name, maxHp, attackDmg);
        this.maxActions = maxActions;
        this.actionsAvailable = maxActions;
        this.vaccineInventory = new ArrayList<>();
        this.supplyInventory = new ArrayList<>();
    }
    public Hero(){
        super();
    }

    public int getMaxActions() {
        return maxActions;
    }

    public int getActionsAvailable() {
        return actionsAvailable;
    }

    public void setActionsAvailable(int actionsAvailable) {
        this.actionsAvailable = actionsAvailable;
    }

    public boolean isSpecialAction() {
        return specialAction;
    }

    public void setSpecialAction(boolean specialAction) {
        this.specialAction = specialAction;
    }

    public ArrayList<Vaccine> getVaccineInventory() {
        return vaccineInventory;
    }

    public ArrayList<Supply> getSupplyInventory() {
        return supplyInventory;
    }
    public void addVaccineInventory(Vaccine v) {
        vaccineInventory.add(v);
    }
    public void addSupplyInventory(Supply s) {
        supplyInventory.add(s);
    }
    public void removeVaccineInventory(Vaccine v) throws NoAvailableResourcesException {
        if (vaccineInventory.size() > 0) {
            vaccineInventory.remove(v);
        }
        else {
            throw new NoAvailableResourcesException("You ran out of vaccine");
        }
    }
    public void removeSupplyInventory(Supply s) throws NoAvailableResourcesException {
        if (supplyInventory.size() > 0) {
            supplyInventory.remove(s);
        }
        else {
            throw new NoAvailableResourcesException("You ran out of supply");
        }
    }
    public void move(Direction d) throws MovementException, NoAvailableResourcesException, NotEnoughActionsException {
        /*if (d == Direction.UP && getLocation().y > 0){
            Point p = new Point(getLocation().x,getLocation().y-1);
            setLocation(p);
        }
        if (d == Direction.DOWN && getLocation().y < map[0].length){
            Point p = new Point(getLocation().x,getLocation().y+1);
            setLocation(p);
        }
        if (d == Direction.LEFT && getLocation().x > 0){
            Point p = new Point(getLocation().x-1,getLocation().y);
            setLocation(p);
        }
        if (d == Direction.RIGHT && getLocation().x < map.length){
            Point p = new Point(getLocation().x+1,getLocation().y);
            setLocation(p);
        }*/
        if (getCurrentHp() <= 0){
            this.onCharacterDeath();
            return;
        }
        if (actionsAvailable <= 0){
            throw new NotEnoughActionsException("out of actions");
        }
        int x = getLocation().x;
        int y = getLocation().y;
        int newx;
        int newy;
        if (d == Direction.LEFT){
            newx = x;
            newy = y-1;
        } else if (d == Direction.RIGHT){
            newx = x;
            newy = y+1;
        } else if (d == Direction.UP) {
            newx = x+1;
            newy = y;
        } else {
            newx = x-1;
            newy = y;
        }
        if (newx < 0 || newy < 0 || newx > map.length-1 || newy > map.length-1){
            throw new MovementException("Out of bounds");
        }
        if(map[newx][newy] instanceof CharacterCell) {
            if(((CharacterCell) map[newx][newy]).getCharacter() != null){
                throw new MovementException("Cell not empty");
            }
        }
        if(map[newx][newy] instanceof CollectibleCell) {
            ((CollectibleCell) map[newx][newy]).getCollectible().pickUp(this);
        }
        if (map[newx][newy] instanceof TrapCell){
            TrapCell t = (TrapCell)(map[newx][newy]);
            super.setCurrentHp(getCurrentHp()-t.getTrapDamage());
        }
        map[newx][newy] = new CharacterCell(this);
        this.setLocation(new Point(newx,newy));
        CharacterCell k = (CharacterCell) map[x][y];
        k.setCharacter(null);
        if (getCurrentHp() <= 0){
            this.onCharacterDeath();
        }
        else {
            illuminate(newx, newy);
        }
        actionsAvailable--;
    }
    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        if (getTarget() instanceof Hero){
            throw new  InvalidTargetException("Cant fight ally");
        }
        if (getTarget() == null){
            throw new  InvalidTargetException("No target");
        }
        if (actionsAvailable <= 0) {
            throw new NotEnoughActionsException("Not enough actions available");
        }
        super.attack();
    }
    public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
        if (supplyInventory.size() <= 0){
            throw new NoAvailableResourcesException("You ran out supply");
        }
        getSupplyInventory().get(getSupplyInventory().size()-1).use(this);
    }

    @Override
    public void onCharacterDeath() {
        heroes.remove(this);
        super.onCharacterDeath();
    }
    public void cure() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
        if (actionsAvailable <= 0){
            throw new NotEnoughActionsException("Out of Actions");
        }
        if (vaccineInventory.size() == 0){
            throw new NoAvailableResourcesException("Out of Vaccines");
        }
        if (!(getTarget() instanceof Zombie)){
            throw new InvalidTargetException("Target not zombie");
        }
        if (this.adjacent(getTarget())){
            CharacterCell ss = (CharacterCell) map[getTarget().getLocation().x][getTarget().getLocation().y];
            if (ss.getCharacter() instanceof Zombie){
                int k = (int)(Math.random()* heroes.size());
                zombies.remove((Zombie) ss.getCharacter());
                Hero hk = availableHeroes.remove(k);
                heroes.add(hk);
                hk.setLocation(new Point(getTarget().getLocation().x,getTarget().getLocation().y));
                map[getTarget().getLocation().x][getTarget().getLocation().y] = new CharacterCell(hk);
                illuminate(getTarget().getLocation().x,getTarget().getLocation().y);
            }
        }
        else {
            throw new InvalidTargetException("Target Out of bounds");
        }
        vaccineInventory.get(0).use(this);
        actionsAvailable--;
    }
}
