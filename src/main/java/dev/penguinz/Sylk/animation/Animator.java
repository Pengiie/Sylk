package dev.penguinz.Sylk.animation;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.animation.interpolators.ColorInterpolator;
import dev.penguinz.Sylk.animation.interpolators.FloatInterpolator;
import dev.penguinz.Sylk.animation.interpolators.Interpolator;
import dev.penguinz.Sylk.animation.interpolators.Vector2Interpolator;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Animator {

    private static final Map<Class<?>, Interpolator<?>> interpolators = new HashMap<>();

    static {
        interpolators.put(Float.class, new FloatInterpolator());
        interpolators.put(Color.class, new ColorInterpolator());
        interpolators.put(Vector2.class, new Vector2Interpolator());
    }

    private final HashMap<Animation, Float> runningAnimations = new HashMap<>();

    public void playAnimation(Animation animation) {
        playAnimation(animation, false);
    }

    public void playAnimation(Animation animation, boolean reversed) {
        if(runningAnimations.containsKey(animation)) {
            if(reversed) {
                this.runningAnimations.put(animation.getReversedAnimation(), animation.getTimeInSeconds() - runningAnimations.get(animation));
                this.runningAnimations.remove(animation);
            }
        } else if(runningAnimations.containsKey(animation.getReversedAnimation())){
            if(!reversed) {
                this.runningAnimations.put(animation,
                        runningAnimations.containsKey(animation.getReversedAnimation()) ? animation.getTimeInSeconds() - runningAnimations.get(animation.getReversedAnimation()) : 0f);
                this.runningAnimations.remove(animation.getReversedAnimation());
            }
        } else {
            this.runningAnimations.put(reversed ? animation.getReversedAnimation() : animation, 0f);
        }

    }

    public void stopAnimation(Animation animation) {
        stopAnimation(animation, false);
    }

    public void stopAnimation(Animation animation, boolean reversed) {
        runningAnimations.remove(reversed ? animation.getReversedAnimation() : animation);
    }

    public void update() {
        Set<Animation> toRemove = new HashSet<>();
        for (Animation animation : runningAnimations.keySet()) {
            float newTime = runningAnimations.get(animation) + Time.deltaTime();
            float progress = Math.min(newTime, animation.getTimeInSeconds()) / animation.getTimeInSeconds();

            animation.getAnimationValues().forEach(animationData -> updateAnimatableValue(progress, animationData));

            runningAnimations.put(animation, newTime);
            if(newTime >= animation.getTimeInSeconds())
                toRemove.add(animation);
        }
        toRemove.forEach(animation -> {
            runningAnimations.remove(animation);
            if(animation.hasCallback())
                animation.getCallback().run();
        });
    }

    private <T> void updateAnimatableValue(float progress, Animation.AnimationData<T> animationData) {
        if(interpolators.containsKey(animationData.value.getClassType())) {
            @SuppressWarnings("unchecked")
            Interpolator<T> interpolator = ((Interpolator<T>) interpolators.get(animationData.value.getClassType()));
            animationData.value.value = interpolator.interpolate(animationData.from, animationData.to, progress);
        } else
            animationData.value.value = progress >= 1 ? animationData.from : animationData.to;
    }

}
