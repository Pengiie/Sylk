package dev.penguinz.Sylk.ui.constraints;

public class RelativeConstraint extends Constraint {

    private final float factor;

    public RelativeConstraint(float factor) {
        this.factor = factor;
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return parentConstraints.getRelativeLengthConstraint(this.type) * factor;
    }
}
