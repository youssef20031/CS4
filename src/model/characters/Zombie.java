package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

import java.awt.*;

import static engine.Game.*;

public class Zombie extends Character{
    private static int ZOMBIES_COUNT = 1;
    public Zombie(){
        super("Zombie" + " " + (ZOMBIES_COUNT),40,10);
        ZOMBIES_COUNT++;
    }
    public boolean adjacent(int a, int b){
        /*return ((c.location.x == location.x)&(c.location.y+1 == location.y)) ||
                ((c.location.x == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y-1 == location.y)) ||
                ((c.location.x + 1 == location.x)&(c.location.y+1 == location.y)) ||
                ((c.location.x - 1 == location.x)&(c.location.y+1 == location.y));*/
        return Math.abs(getLocation().x - a) <= 1 & Math.abs(getLocation().y - b) <= 1;
    }


    public void setTargetToZombie(Character target) {

    }

    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        /*if (getTarget() instanceof Zombie){
            throw new InvalidTargetException("Cant fight ally");
        }
        for (int i = 0; i < heroes.size(); i++){
            Hero h = heroes.get(i);
            if (this.adjacent(h.getLocation().x,h.getLocation().y)){
                this.setTarget(h);
                super.attack();
                return;
            }
        }*/
        if (getTarget() instanceof Zombie) {
            throw new InvalidTargetException("Cant fight ally");
        }

        int x = this.getLocation().x;
        int y = this.getLocation().y;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && j >= 0 && i < 15 && j < 15) {
                    if (map[i][j] instanceof CharacterCell) {
                        CharacterCell c = (CharacterCell) map[i][j];
                        if (c.getCharacter() instanceof Hero) {
                            this.setTarget(c.getCharacter());
                            super.attack();
                            return;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void onCharacterDeath() {
        super.onCharacterDeath();

        zombies.remove(this);

        zombieAdder(1);
        //map[getLocation().x][getLocation().y] = new CharacterCell(null);

    }
}
