package model.world;

import java.util.Random;

public class TrapCell extends Cell{
    private int trapDamage;

    public TrapCell() {
        super();
        int numb [] = {10,20,30};
        int choice= (int) (Math.random() * numb.length);
        this.trapDamage = numb[choice];
    }

    public int getTrapDamage() {
        return trapDamage;
    }
}
