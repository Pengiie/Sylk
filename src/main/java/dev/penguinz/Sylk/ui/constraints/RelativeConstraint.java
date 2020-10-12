package dev.penguinz.Sylk.ui.constraints;

/**
 * Positions and scales the UI Element with a relative factor to the parents scale. Most useful constraint for flexible UIs.
 */
public class RelativeConstraint extends AnimatableConstraint {

    /**
     * Creates a relative constraint using the given scaling factor.
     * @param factor the value to scale from.
     */
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
