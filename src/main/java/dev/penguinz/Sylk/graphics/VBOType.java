package dev.penguinz.Sylk.graphics;

public enum VBOType {

    VERTICES(0), TEXTURE_COORDS(1);

    private final int index;

    VBOType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
