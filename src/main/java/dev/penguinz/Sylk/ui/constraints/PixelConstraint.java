package dev.penguinz.Sylk.ui.constraints;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.util.Alignment;

public class PixelConstraint extends AnimatableConstraint {

    private Alignment alignment;

    public PixelConstraint(float pixels) {
        this(pixels, Alignment.LEFT);
    }

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

}
