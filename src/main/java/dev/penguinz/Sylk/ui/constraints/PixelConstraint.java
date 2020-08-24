package dev.penguinz.Sylk.ui.constraints;

public class PixelConstraint extends AnimatableConstraint {

    public PixelConstraint(float pixels) {
        super(pixels);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return parentConstraints.getRelativeConstraint(this.type) + animatableValue.value;
    }

}
