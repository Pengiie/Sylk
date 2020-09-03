package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.animation.Animation;
import dev.penguinz.Sylk.animation.Animator;
import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.animation.values.AnimatableVector2;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter {

    private final List<ParticleInstance> particleInstances = new ArrayList<>();

    private final Animator animator = new Animator();

    public Vector2 position;
    public float initialRotation = 0;
    public Vector2 initialSize = new Vector2();

    public ParticleEmitter(Vector2 position) {
        this.position = position;
    }

    public void emit(Particle particle, Vector2 velocity, float lifetime) {
        emit(1, particle, velocity, lifetime);
    }

    public void emit(int count, Particle particle, Vector2 velocity, float lifetime) {
        AnimatableVector2 size = new AnimatableVector2(new Vector2(Vector2.add(particle.startSize, initialSize)));
        AnimatableColor color = new AnimatableColor(new Color(particle.startColor));
        AnimatableFloat rotation = new AnimatableFloat(particle.startRotation + initialRotation);
        if(particle.isSizeAnimated || particle.isColorAnimated || particle.isRotationAnimated) {
            Animation animation = new Animation(lifetime);
            if(particle.isSizeAnimated) animation.addValue(size, Vector2.add(particle.startSize, initialSize), Vector2.add(particle.endSize, initialSize));
            if(particle.isColorAnimated) animation.addValue(color, particle.startColor, particle.endColor);
            if(particle.isRotationAnimated) animation.addValue(rotation, particle.startRotation + initialRotation, particle.endRotation + initialRotation);
            animator.playAnimation(animation);
        }
        for (int i = 0; i < count; i++) {
            particleInstances.add(new ParticleInstance(Vector2.sub(this.position, new Vector2(size.value.x/2, size.value.y/2)), size, rotation, velocity, color, particle.friction, lifetime));
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
