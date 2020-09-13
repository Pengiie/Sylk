package dev.penguinz.Sylk.ui.constraints;

public class RelativeConstraint extends AnimatableConstraint {

    public RelativeConstraint(float factor) {
        super(factor);
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        float factoredParentValue = parentConstraints.getRelativeLengthConstraint(this.type) * animatableValue.value;
        if(this.type == ConstraintType.X || this.type == ConstraintType.Y)
            return parentConstraints.getRelativeConstraint(this.type) + factoredParentValue;
        return factoredParentValue;
    }

    @Override
    Constraint copy() {
        return new RelativeConstraint(animatableValue.value);
    }
}
