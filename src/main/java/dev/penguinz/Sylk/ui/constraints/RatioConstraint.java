package dev.penguinz.Sylk.ui.constraints;

/**
 * Scales or positions the UI Element based off of the opposite value. Used mainly for rendering images with the correct aspect ratio.
 */
public class RatioConstraint extends AnimatableConstraint {

    /**
     * Creates a new ratio constraint with the given ratio.
     * @param ratio the ratio factor to use.
     */
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
