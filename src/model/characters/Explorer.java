package model.characters;


import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

import static engine.Game.map;

public class Explorer extends Hero{
    public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    @Override
    public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
        super.useSpecial();
        if (!isSpecialAction()) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    map[i][j].setVisible(true);
                }
            }
        }
        setSpecialAction(true);
        //getSupplyInventory().get(getSupplyInventory().size()-1).use(this);

    }

    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        super.attack();
        setActionsAvailable(getActionsAvailable()-1);
    }
}
