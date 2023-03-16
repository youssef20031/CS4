package model.world;

public abstract class Cell {
    boolean isVisible;
    public Cell(){

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
