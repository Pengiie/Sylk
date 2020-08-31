package dev.penguinz.Sylk.ui.constraints;

import dev.penguinz.Sylk.ui.UIContainer;

public class UIConstraints {

    private Constraint xConstraint;
    private Constraint yConstraint;
    private Constraint widthConstraint;
    private Constraint heightConstraint;

    public UIConstraints setXConstraint(Constraint constraint) {
        this.xConstraint = constraint;
        this.xConstraint.setType(ConstraintType.X);
        this.xConstraint.setConstraints(this);
        return this;
    }

    public UIConstraints setYConstraint(Constraint constraint) {
        this.yConstraint = constraint;
        this.yConstraint.setType(ConstraintType.Y);
        this.yConstraint.setConstraints(this);
        return this;
    }

    public UIConstraints setWidthConstraint(Constraint constraint) {
        this.widthConstraint = constraint;
        this.widthConstraint.setType(ConstraintType.WIDTH);
        this.widthConstraint.setConstraints(this);
        return this;
    }

    public UIConstraints setHeightConstraint(Constraint constraint) {
        this.heightConstraint = constraint;
        this.heightConstraint.setType(ConstraintType.HEIGHT);
        this.heightConstraint.setConstraints(this);
        return this;
    }

    public void update(UIConstraints parent) {
        this.widthConstraint.update(parent);
        this.heightConstraint.update(parent);
        this.xConstraint.update(parent);
        this.yConstraint.update(parent);
    }

    public AnimatableConstraint getXAnimatableConstraint() {
        return (AnimatableConstraint) xConstraint;
    }

    public AnimatableConstraint getYAnimatableConstraint() {
        return (AnimatableConstraint) yConstraint;
    }

    public AnimatableConstraint getWidthAnimatableConstraint() {
        return (AnimatableConstraint) widthConstraint;
    }

    public AnimatableConstraint getHeightAnimatableConstraint() {
        return (AnimatableConstraint) heightConstraint;
    }

    public float getXConstraintValue() {
        return xConstraint.getValue();
    }

    public float getYConstraintValue() {
        return yConstraint.getValue();
    }

    public float getWidthConstraintValue() {
        return widthConstraint.getValue();
    }

    public float getHeightConstraintValue() {
        return heightConstraint.getValue();
    }

    public float getRelativeConstraint(ConstraintType type) {
        switch (type) {
            case X: return getXConstraintValue();
            case Y: return getYConstraintValue();
            case WIDTH: return getWidthConstraintValue();
            case HEIGHT: return getHeightConstraintValue();
        }
        return 0;
    }

    public float getOppositeConstraint(ConstraintType type) {
        switch (type) {
            case X: return getYConstraintValue();
            case Y: return getXConstraintValue();
            case WIDTH: return getHeightConstraintValue();
            case HEIGHT: return getWidthConstraintValue();
        }
        return 0;
    }

    public float getRelativeLengthConstraint(ConstraintType type) {
        switch (type) {
            case X: case WIDTH: return getWidthConstraintValue();
            case Y: case HEIGHT: return getHeightConstraintValue();
        }
        return 0;
    }

    public static UIConstraints getFullConstraints() {
        return new UIConstraints().
                setXConstraint(new PixelConstraint(0)).
                setYConstraint(new PixelConstraint(0)).
                setWidthConstraint(new RelativeConstraint(1)).
                setHeightConstraint(new RelativeConstraint(1));
    }
}
