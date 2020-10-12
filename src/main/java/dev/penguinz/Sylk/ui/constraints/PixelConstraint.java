package dev.penguinz.Sylk.ui.constraints;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.util.Alignment;

/**
 * Positions the UI Element with pixels relative to the alignment.
 */
public class PixelConstraint extends AnimatableConstraint {

    private Alignment alignment;

    /**
     * Creates a new pixel constraint with a set pixel offset from left or top.
     * @param pixels the pixel offset.
     */
    public PixelConstraint(float pixels) {
        this(pixels, Alignment.LEFT);
    }

    /**
     * Creates a new pixel constraint with a set pixel offset from the given alignment.
     * @param pixels the pixel offset.
     * @param alignment the alignment from where to offset.
     */
    public PixelConstraint(float pixels, Alignment alignment) {
        super(pixels);
        this.alignment = alignment;
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        if(this.type == ConstraintType.WIDTH || this.type == ConstraintType.HEIGHT) {
            Application.getInstance().getLogger().logWarning("Pixel Constraint should not be used with lengths, See Absolute Constraint");
            return animatableValue.value;
        } else {
            switch (alignment) {
                case LEFT: case TOP: return parentConstraints.getRelativeConstraint(this.type) + animatableValue.value;
                case RIGHT: case BOTTOM: return parentConstraints.getRelativeLengthConstraint(this.type) - animatableValue.value - this.getConstraints().getRelativeLengthConstraint(this.type);
                case CENTER: return parentConstraints.getRelativeLengthConstraint(this.type)/2 - this.getConstraints().getRelativeLengthConstraint(this.type)/2 + animatableValue.value;
            }
        }
        Application.getInstance().getLogger().logError("Pixel Constraint could not return a value");
        return 0;
    }

    @Override
    Constraint copy() {
        return new PixelConstraint(animatableValue.value, alignment);
    }
}
