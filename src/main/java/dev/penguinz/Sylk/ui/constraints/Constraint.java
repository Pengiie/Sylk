package dev.penguinz.Sylk.ui.constraints;

public abstract class Constraint {

    private UIConstraints constraints;
    protected ConstraintType type;
    private float value;

    protected void setType(ConstraintType type) {
        this.type = type;
    }

    protected void setConstraints(UIConstraints constraints) {
        this.constraints = constraints;
    }

    protected UIConstraints getConstraints() {
        return this.constraints;
    }

    protected void update(UIConstraints parentConstraints) {
        this.value = calculateValue(parentConstraints);
    }

    protected abstract float calculateValue(UIConstraints parentConstraints);

    protected float getValue() {
        return value;
    }
}
