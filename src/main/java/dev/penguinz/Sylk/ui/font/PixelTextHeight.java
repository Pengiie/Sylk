package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.ui.constraints.UIConstraints;

public class PixelTextHeight implements TextHeight {

    private final int pixelHeight;

    public PixelTextHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    @Override
    public int getPixelHeight(UIConstraints constraints) {
        return this.pixelHeight;
    }
}
