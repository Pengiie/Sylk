package dev.penguinz.Sylk.animation;

import dev.penguinz.Sylk.animation.values.AnimatableValue;
import dev.penguinz.Sylk.ui.constraints.AnimatableConstraint;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Animation {

    private Set<AnimationData<?>> animationValues = new HashSet<>();

    private final float timeInSeconds;

    private Runnable callback = null;

    private Animation reversedAnimation;

    public Animation(float timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public Animation(Animation animation) {
        this.timeInSeconds = animation.timeInSeconds;
        this.animationValues = new HashSet<>(animation.animationValues);
        this.reversedAnimation = animation;
    }

    public Animation addValue(AnimatableConstraint value, float from, float to) {
        return addValue(value.getAnimatableValue(), from, to);
    }

    public <T> Animation addValue(AnimatableValue<T> value, T from, T to) {
        animationValues.add(new AnimationData<>(value, from, to));
        updateReversedAnimation();
        return this;
    }

    public Animation setCompletionCallback(Runnable callback) {
        this.callback = callback;
        if(this.reversedAnimation != null)
            this.reversedAnimation.callback = this.callback;
        return this;
    }

    private void updateReversedAnimation() {
        this.reversedAnimation = new Animation(this);
        this.reversedAnimation.animationValues = this.reversedAnimation.animationValues.stream().map(AnimationData::getReverse).collect(Collectors.toSet());
    }

    public Animation getReversedAnimation() {
        if(this.reversedAnimation == null)
            updateReversedAnimation();
        return this.reversedAnimation;
    }

    protected float getTimeInSeconds() {
        return timeInSeconds;
    }

    protected Set<AnimationData<?>> getAnimationValues() {
        return animationValues;
    }

    protected Runnable getCallback() {
        return callback;
    }

    protected boolean hasCallback() {
        return callback != null;
    }

    static class AnimationData<T> {
        public final AnimatableValue<T> value;
        public final T from, to;

        public AnimationData(AnimatableValue<T> value, T from, T to) {
            this.value = value;
            this.from = from;
            this.to = to;
        }

        public AnimationData<T> getReverse() {
            return new AnimationData<>(value, to, from);
        }
    }

}
