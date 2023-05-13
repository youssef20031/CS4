package model.collectibles;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;
import model.characters.Zombie;

public class Supply implements Collectible{
    public Supply(){
        super();
    }

    @Override
    public void pickUp(Hero h) {
        h.addSupplyInventory(this);
    }

    @Override
    public void use(Hero h) throws NoAvailableResourcesException {
        if (h.getSupplyInventory().size() <= 0){
            throw new NoAvailableResourcesException("Out of Supply");
        }
        h.getSupplyInventory().remove(this);
    }
}
