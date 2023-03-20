package model.characters;

public class Zombie extends Character{
    private static int ZOMBIES_COUNT;
    public Zombie(){
        super("Zombie" + " " + (ZOMBIES_COUNT + 1),40,10);
        ZOMBIES_COUNT++;
    }

}
