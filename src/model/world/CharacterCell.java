package model.world;

public class CharacterCell extends Cell{
    private Character character;
    private boolean isSafe;

    public CharacterCell(Character character, boolean isSafe) {
        super();
        this.character = character;
        this.isSafe = isSafe;
    }
}
