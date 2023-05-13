package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public class Medic extends Hero{
    public Medic(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    @Override
    public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
        if (getTarget() instanceof Zombie){
            throw new InvalidTargetException("Can not heal Zombie!");
        }
        else {
            super.useSpecial();
            if (this.adjacent(getTarget())){
                getTarget().setCurrentHp(getTarget().getMaxHp());
                //getSupplyInventory().get(getSupplyInventory().size()-1).use(this);
            }
            else {
                throw new InvalidTargetException("Out of Range!");
            }
            setSpecialAction(true);
        }
    }

    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        super.attack();
        setActionsAvailable(getActionsAvailable()-1);
    }
}
