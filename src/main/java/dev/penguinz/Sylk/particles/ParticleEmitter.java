package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.animation.Animation;
import dev.penguinz.Sylk.animation.Animator;
import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.animation.values.AnimatableVector2;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter {

    private final List<ParticleInstance> particleInstances = new ArrayList<>();

    private final Animator animator = new Animator();

    public Vector2 position;

    public ParticleEmitter(Vector2 position) {
        this.position = position;
    }

    public void emit(int count, Particle particle, Vector2 velocity, float lifetime) {
        AnimatableVector2 size = new AnimatableVector2(new Vector2(particle.startSize));
        AnimatableColor color = new AnimatableColor(new Color(particle.startColor));
        if(particle.isSizeAnimated || particle.isColorAnimated) {
            Animation animation = new Animation(lifetime);
            if(particle.isSizeAnimated) animation.addValue(size, particle.startSize, particle.endSize);
            if(particle.isColorAnimated) animation.addValue(color, particle.startColor, particle.endColor);
            animator.playAnimation(animation);
        }
        for (int i = 0; i < count; i++) {
            particleInstances.add(new ParticleInstance(this.position, size, new AnimatableFloat(0), velocity, color, particle.friction, lifetime));
        }
    }

    public void update() {
        ParticleInstance[] particlesToRemove = new ParticleInstance[particleInstances.size()];
        for (int i = 0; i < particleInstances.size(); i++) {
            if(particleInstances.get(i).update())
                particlesToRemove[i] = particleInstances.get(i);
        }
        for (ParticleInstance particleInstance : particlesToRemove) {
            if (particleInstance != null)
                particleInstances.remove(particleInstance);
        }

        animator.update();
    }

    public List<ParticleInstance> getParticles() {
        return particleInstances;
    }
}
