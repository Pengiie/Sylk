package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmitter {

    private final List<Particle> particles = new ArrayList<>();

    private Vector2 position;

    public ParticleEmitter(Vector2 position) {
        this.position = position;
    }

    public void emit(int count, Vector2 size, Vector2 velocity, Color color, float lifetime) {
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(new Transform(position, new Vector2(size)), velocity, color, 1, 1));
        }
    }

    public void update() {
        Particle[] particlesToRemove = new Particle[particles.size()];
        for (int i = 0; i < particles.size(); i++) {
            if(particles.get(i).update())
                particlesToRemove[i] = particles.get(i);
        }
        for (Particle particle : particlesToRemove) {
            if (particle != null)
                particles.remove(particle);
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }
}
