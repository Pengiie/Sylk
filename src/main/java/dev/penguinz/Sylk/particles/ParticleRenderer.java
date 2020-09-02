package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.RenderLayer;
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

    private static final int STRIDE = Float.BYTES * 8;

    private int particleVao;
    private int particleVbo;

    private Shader shader;

    private FloatBuffer data;

    private List<ParticleEmitter> emitters = new ArrayList<>();
    private Camera camera;

    private RenderLayer renderLayer;
    
    public ParticleRenderer(RenderLayer renderLayer) {
        this.renderLayer = renderLayer;
        // 2 vertices, 2 texture coords
        this.data = MemoryUtil.memAllocFloat(MAX_PARTICLES * 2 * 2);

        this.particleVao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(this.particleVao);
        this.particleVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.particleVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, this.data, GL15.GL_DYNAMIC_DRAW);

        GL30.glEnableVertexAttribArray(0);
        GL30.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, STRIDE, 0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, STRIDE, Float.BYTES * 2);
        GL30.glEnableVertexAttribArray(2);
        GL30.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, STRIDE, Float.BYTES * 4);

        this.shader = ParticleShader.create();
    }

    public void begin(Camera camera) {
        this.camera = camera;
    }

    public void renderEmitter(ParticleEmitter emitter) {
        emitters.add(emitter);
    }

    public void finish() {
        int count = 0;
        data.clear();
        for (ParticleEmitter emitter : this.emitters) {
            if(data.remaining() < 6 * 4)
                break;
            for (ParticleInstance particleInstance : emitter.getParticles()) {
                if(data.remaining() < 6 * 4)
                    break;
                float posX0 = particleInstance.getPosition().x;
                float posX1 = posX0 + particleInstance.getSize().x;
                float posY0 = particleInstance.getPosition().y;
                float posY1 = posY0 + particleInstance.getSize().y;
                float r = particleInstance.getColor().r, g = particleInstance.getColor().g, b = particleInstance.getColor().b,  a = particleInstance.getColor().a;
                data.put(new float[] {
                        posX0, posY1, 0, 1, r, g, b, a,
                        posX1, posY0, 1, 0, r, g, b, a,
                        posX0, posY0, 0, 0, r, g, b, a,
                        posX0, posY1, 0, 1, r, g, b, a,
                        posX1, posY0, 1, 0, r, g, b, a,
                        posX1, posY1, 1, 1, r, g, b, a,
                });
                count += 6;
            }
        }
        data.flip();

        this.shader.use();

        Application.getInstance().bindRenderLayer(this.renderLayer);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.shader.loadUniform(UniformConstants.projViewMatrix, camera.getProjViewMatrix());

        GL30.glBindVertexArray(this.particleVao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.particleVbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);

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
