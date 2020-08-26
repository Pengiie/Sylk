package dev.penguinz.Sylk.ui.constraints;

public class PixelConstraint extends AnimatableConstraint {

    public PixelConstraint(float pixels) {
        super(pixels);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        if(this.type == ConstraintType.WIDTH || this.type == ConstraintType.HEIGHT)
            return animatableValue.value;
        else
            return parentConstraints.getRelativeConstraint(this.type) + animatableValue.value;
    }

}
