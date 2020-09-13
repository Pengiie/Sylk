package dev.penguinz.Sylk.ui.constraints;

public class RatioConstraint extends AnimatableConstraint {

    public RatioConstraint(float ratio) {
        super(ratio);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return this.getConstraints().getOppositeConstraint(this.type) * animatableValue.value;
    }

    @Override
    Constraint copy() {
        return new RatioConstraint(animatableValue.value);
    }
}
