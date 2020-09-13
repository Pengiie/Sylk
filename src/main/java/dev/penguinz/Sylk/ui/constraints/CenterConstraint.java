package dev.penguinz.Sylk.ui.constraints;

public class CenterConstraint extends Constraint {

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        if(this.type == ConstraintType.WIDTH || this.type == ConstraintType.HEIGHT)
            throw new IllegalArgumentException("Center Constraint cannot be used for Width or Height");
        return parentConstraints.getRelativeConstraint(this.type) +
                parentConstraints.getRelativeLengthConstraint(this.type)/2 -
                getConstraints().getRelativeLengthConstraint(this.type)/2;
    }

    @Override
    Constraint copy() {
        return new CenterConstraint();
    }
}
