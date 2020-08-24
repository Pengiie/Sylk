package dev.penguinz.Sylk.ui.constraints;

public class AbsoluteConstraint extends AnimatableConstraint {

    public AbsoluteConstraint(float absolutePixel) {
        super(absolutePixel);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return animatableValue.value;
    }

}
