package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class ParticleRenderer implements Disposable {

    private static final int MAX_PARTICLES = 1000;

    private static final int ATTRIBUTES = 8;
    private static final int STRIDE = Float.BYTES * ATTRIBUTES;
    private static final int VERTICES = 6;

    private int particleVao;
    private int particleVbo;

    private Shader shader;

    private FloatBuffer data;

    private Camera camera;

    private RenderLayer renderLayer;

    private int vertexCount = 0;

    public ParticleRenderer(RenderLayer renderLayer) {
        this.renderLayer = renderLayer;
        // 2 vertices, 2 texture coords, 4 color components
        this.data = MemoryUtil.memAllocFloat(MAX_PARTICLES * ATTRIBUTES * VERTICES);

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
        for (ParticleInstance particleInstance : emitter.getParticles()) {
            if(data.remaining() < VERTICES * ATTRIBUTES)
                flush();
            Matrix4f transformMatrix =
                    MatrixUtils.createTransformMatrix(
                            new Transform(
                                    particleInstance.getPosition(),
                                    particleInstance.getRotation(),
                                    new Vector2(particleInstance.getSize().x / 2, particleInstance.getSize().y / 2),
                                    particleInstance.getSize()));
            Vector4f pos0 = new Vector4f(0, 0, 0, 1).mul(transformMatrix);
            Vector4f pos1 = new Vector4f(0, 1, 0, 1).mul(transformMatrix);
            Vector4f pos2 = new Vector4f(1, 0, 0, 1).mul(transformMatrix);
            Vector4f pos3 = new Vector4f(1, 1, 0, 1).mul(transformMatrix);
            float   r = particleInstance.getColor().r,
                    g = particleInstance.getColor().g,
                    b = particleInstance.getColor().b,
                    a = particleInstance.getColor().a;
            data.put(new float[] {
                    pos1.x, pos1.y, 0, 1, r, g, b, a,
                    pos2.x, pos2.y, 1, 0, r, g, b, a,
                    pos0.x, pos0.y, 0, 0, r, g, b, a,
                    pos1.x, pos1.y, 0, 1, r, g, b, a,
                    pos2.x, pos2.y, 1, 0, r, g, b, a,
                    pos3.x, pos3.y, 1, 1, r, g, b, a,
            });
            vertexCount += 6;
        }
    }

    public void finish() {
        if(vertexCount > 0)
            flush();
    }

    public void flush() {
        data.flip();
        this.shader.use();

        Application.getInstance().bindRenderLayer(this.renderLayer);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.shader.loadUniform(UniformConstants.projViewMatrix, camera.getProjViewMatrix());

        GL30.glBindVertexArray(this.particleVao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.particleVbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

        data.clear();
        vertexCount = 0;
    }

    @Override
    public void dispose() {
        this.shader.dispose();
        GL30.glDeleteVertexArrays(particleVao);
        GL15.glDeleteBuffers(particleVbo);
    }
}
