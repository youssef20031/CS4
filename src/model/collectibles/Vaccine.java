package model.collectibles;

import exceptions.NoAvailableResourcesException;
import model.characters.Character;
import model.characters.Hero;
import model.characters.Zombie;
import model.world.CharacterCell;

import static engine.Game.*;

public class Vaccine implements Collectible {
    public Vaccine(){
        super();
    }

    @Override
    public void pickUp(Hero h) {
        h.getVaccineInventory().add(this);
    }

    @Override
    public void use(Hero h) throws NoAvailableResourcesException {
        if (h.getVaccineInventory().size() <= 0){
            throw new NoAvailableResourcesException("Out of vaccine");
        }
        h.getVaccineInventory().remove(this);
        if (h.getTarget() instanceof Zombie) {
            zombies.remove((Zombie) h.getTarget());
            int j = (int) (Math.random()* availableHeroes.size());
            Hero c = availableHeroes.remove(j);
            heroes.add(c);
            c.setLocation(h.getTarget().getLocation());
            int x = h.getTarget().getLocation().x;
            int y = h.getTarget().getLocation().y;
            map[x][y] = new CharacterCell(c);
        }

    }
}
