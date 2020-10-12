package dev.penguinz.Sylk.ui.constraints;

/**
 * A final value for the constraint, doesn't scale.
 */
public class AbsoluteConstraint extends AnimatableConstraint {

    public AbsoluteConstraint(float absolutePixel) {
        super(absolutePixel);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return animatableValue.value;
    }

    @Override
    Constraint copy() {
        return new AbsoluteConstraint(animatableValue.value);
    }
}
