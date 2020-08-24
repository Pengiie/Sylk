package dev.penguinz.Sylk.ui.constraints;

public class AbsoluteConstraint extends Constraint {

    private final float absolutePixel;

    public AbsoluteConstraint(float absolutePixel) {
        this.absolutePixel = absolutePixel;
    }

    @Override
    protected float calculateValue(UIConstraints parentConstraints) {
        return absolutePixel;
    }
}
