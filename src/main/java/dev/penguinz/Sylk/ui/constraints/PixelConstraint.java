package dev.penguinz.Sylk.ui.constraints;

public class PixelConstraint extends Constraint {

    private final float pixels;

    public PixelConstraint(float pixels) {
        this.pixels = pixels;
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return parentConstraints.getRelativeConstraint(this.type) + pixels;
    }

}
