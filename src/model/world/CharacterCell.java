package model.world;
import model.characters.Character;

public class CharacterCell extends Cell{
    private Character character;
    private boolean isSafe;

    public CharacterCell(Character character){
        super();
        this.character = character;
    }
    public CharacterCell(Character character, boolean isSafe) {
        super();
        this.character = character;
        this.isSafe = isSafe;
    }
    public CharacterCell(boolean isSafe) {
        super();
        this.isSafe = isSafe;
    }


    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }
}
