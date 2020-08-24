package dev.penguinz.Sylk.ui.constraints;

public class RelativeConstraint extends AnimatableConstraint {

    public RelativeConstraint(float factor) {
        super(factor);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return parentConstraints.getRelativeLengthConstraint(this.type) * animatableValue.value;
    }

}
