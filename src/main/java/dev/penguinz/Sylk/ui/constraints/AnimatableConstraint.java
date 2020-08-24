package dev.penguinz.Sylk.ui.constraints;

import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.animation.values.AnimatableValue;

public abstract class AnimatableConstraint extends Constraint {

    protected AnimatableValue<Float> animatableValue;

    public AnimatableConstraint(float value) {
        this.animatableValue = new AnimatableFloat(value);
    }

    public AnimatableValue<Float> getAnimatableValue() {
        return animatableValue;
    }
}
