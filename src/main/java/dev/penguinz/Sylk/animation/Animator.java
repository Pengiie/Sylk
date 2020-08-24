package dev.penguinz.Sylk.animation;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.animation.interpolators.ColorInterpolator;
import dev.penguinz.Sylk.animation.interpolators.FloatInterpolator;
import dev.penguinz.Sylk.animation.interpolators.Interpolator;
import dev.penguinz.Sylk.util.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Animator {

    private static final Map<Class<?>, Interpolator<?>> interpolators = new HashMap<>();

    static {
        interpolators.put(Float.class, new FloatInterpolator());
        interpolators.put(Color.class, new ColorInterpolator());
    }

    private final HashMap<Animation, Float> runningAnimations = new HashMap<>();

    public void playAnimation(Animation animation) {
        this.runningAnimations.put(animation, 0f);
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
        toRemove.forEach(runningAnimations::remove);
    }

    public <T> void updateAnimatableValue(float progress, Animation.AnimationData<T> animationData) {
        if(interpolators.containsKey(animationData.value.getClassType())) {
            @SuppressWarnings("unchecked")
            Interpolator<T> interpolator = ((Interpolator<T>) interpolators.get(animationData.value.getClassType()));
            animationData.value.value = interpolator.interpolate(animationData.from, animationData.to, progress);
        } else
            animationData.value.value = progress >= 1 ? animationData.from : animationData.to;
    }

}
