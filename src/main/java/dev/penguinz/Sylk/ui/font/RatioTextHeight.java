package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.ui.constraints.UIConstraints;

public class RatioTextHeight implements TextHeight {

    private float ratio;

    public RatioTextHeight(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public int getPixelHeight(UIConstraints constraints) {
        return (int) (constraints.getHeightConstraintValue() * ratio);
    }
}
