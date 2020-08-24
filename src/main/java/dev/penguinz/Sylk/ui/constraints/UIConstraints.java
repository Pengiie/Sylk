package dev.penguinz.Sylk.ui.constraints;

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
        this.xConstraint.update(parent);
        this.yConstraint.update(parent);
        this.widthConstraint.update(parent);
        this.heightConstraint.update(parent);
    }

    public float getXConstraint() {
        return xConstraint.getValue();
    }

    public float getYConstraint() {
        return yConstraint.getValue();
    }

    public float getWidthConstraint() {
        return widthConstraint.getValue();
    }

    public float getHeightConstraint() {
        return heightConstraint.getValue();
    }

    public float getRelativeConstraint(ConstraintType type) {
        switch (type) {
            case X: return getXConstraint();
            case Y: return getYConstraint();
            case WIDTH: return getWidthConstraint();
            case HEIGHT: return getHeightConstraint();
        }
        return 0;
    }

    public float getRelativeLengthConstraint(ConstraintType type) {
        switch (type) {
            case X: case WIDTH: return getWidthConstraint();
            case Y: case HEIGHT: return getHeightConstraint();
        }
        return 0;
    }
}
