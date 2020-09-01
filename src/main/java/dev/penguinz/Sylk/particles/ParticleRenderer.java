package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Disposable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ParticleRenderer implements Disposable {

    private static final int MAX_PARTICLES = 1000;

    private int particleVao;
    private int particleVbo;

    private Shader shader;

    private FloatBuffer data;

    private List<ParticleEmitter> emitters = new ArrayList<>();
    private Camera camera;

    public ParticleRenderer() {
        // 2 vertices, 2 texture coords
        this.data = MemoryUtil.memAllocFloat(MAX_PARTICLES * 2 * 2);

        this.particleVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.particleVao);
        this.particleVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.particleVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.data.limit(), GL15.GL_DYNAMIC_DRAW);

        GL30.glEnableVertexAttribArray(0);
        GL30.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Float.BYTES, 0);

        this.shader = ParticleShader.create();
    }

    public void begin(Camera camera) {
        this.camera = camera;
    }

    public void renderEmitter(ParticleEmitter emitter) {
        emitters.add(emitter);
    }

    public void finish() {
        data.clear();
        int count = 0;
        for (ParticleEmitter emitter : this.emitters) {
            for (Particle particle : emitter.getParticles()) {
                float posX0 = particle.getTransform().position.x;
                float posX1 = posX0 + particle.getTransform().getScale().x;
                float posY0 = particle.getTransform().position.y;
                float posY1 = posY0 + particle.getTransform().getScale().y;
                data.put(new float[] {
                        posX0, posY1, 0, 1,
                        posX1, posY0, 1, 0,
                        posX0, posY0, 0, 0,
                        posX0, posY1, 0, 1,
                        posX1, posY0, 1, 0,
                        posX1, posY1, 1, 1,
                });
                count += 4;
            }
        }
        data.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.particleVbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);

        this.shader.use();
        this.shader.loadUniform(UniformConstants.projViewMatrix, camera.getProjViewMatrix());

        GL30.glBindVertexArray(this.particleVao);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, count);

        emitters.clear();
    }

    @Override
    public void dispose() {
        this.shader.dispose();
        GL30.glDeleteVertexArrays(particleVao);
        GL15.glDeleteBuffers(particleVbo);
    }
}
