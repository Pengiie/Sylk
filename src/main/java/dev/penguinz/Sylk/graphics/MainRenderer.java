package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.graphics.shader.BlendShader;
import dev.penguinz.Sylk.graphics.shader.BlurShader;
import dev.penguinz.Sylk.graphics.shader.MainShader;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.MatrixUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_NEAREST;

/**
 * The main renderer that handles rendering and post processing effects.
 */
public class MainRenderer extends Renderer {

    private static final VAO screenQuad = new VAO(new VBO(new float[] {
            -1, 1,
            1, -1,
            -1, -1,
            -1, 1,
            1, -1,
            1, 1
    }, 2, VBOType.VERTICES), new VBO(new float[] {
            0, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 0,
            1, 1
    }, 2, VBOType.TEXTURE_COORDS));

    private final Shader blendShader;

    public boolean useGlow = true;
    public float glowIntensity = 1f;

    private int frameBuffer;
    private int baseColorTexture, glowColorTexture;

    private final Shader blurShader;
    private int[] blurFrameBuffers;
    private int[] blurTextures;

    /**
     * Creates a new renderer instance
     */
    public MainRenderer() {
        super(MainShader.create());
        this.blendShader = BlendShader.create();
        this.blurShader = BlurShader.create();

        createFrameBuffer();
        createBlurBuffers();
    }

    private void createFrameBuffer() {
        this.frameBuffer = GL30.glGenFramebuffers();
        bindFrameBuffer();
        int[] colorBuffers = new int[] {glGenTextures(), glGenTextures()};
        for (int i = 0; i < colorBuffers.length; i++) {
            glBindTexture(GL_TEXTURE_2D, colorBuffers[i]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGBA,
                    (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                    0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

            setTextureParams();

            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, colorBuffers[i], 0);
        }
        this.baseColorTexture = colorBuffers[0];
        this.glowColorTexture = colorBuffers[1];
    }

    private void createBlurBuffers() {
        this.blurFrameBuffers = new int[] { GL30.glGenFramebuffers(), GL30.glGenFramebuffers() };
        this.blurTextures = new int[] { glGenTextures(), glGenTextures() };
        for (int i = 0; i < this.blurFrameBuffers.length; i++) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[i]);
            glBindTexture(GL_TEXTURE_2D, blurTextures[i]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGBA,
                    (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                    0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

            setTextureParams();

            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, blurTextures[i], 0);
        }
    }

    private void setTextureParams() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    private void bindFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
    }

    @Override
    public void begin(Camera camera) {
        super.begin(camera);
        bindFrameBuffer();
        GL30.glDrawBuffers(new int[] {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1});
        GL30.glClear(GL_COLOR_BUFFER_BIT);
        shader.loadUniform(UniformConstants.projViewMatrix, camera.getProjViewMatrix());
    }

    @Override
    protected void flush() {
        currentVao.bind();
        currentVao.enableVertexAttribArrays();

        for (Item renderItem : renderItems) {
            shader.loadUniform(UniformConstants.transformationMatrix, MatrixUtils.createTransformMatrix(renderItem.transform));

            shader.loadUniform(UniformConstants.color, renderItem.material.color.toVector());
            shader.loadUniform(UniformConstants.glows, renderItem.material.glows);
            boolean hasTexture = renderItem.material.texture != null;
            shader.loadUniform(UniformConstants.hasTexture, hasTexture);
            if(hasTexture)
                shader.loadUniform(UniformConstants.texture0, renderItem.material.texture);

            glDrawArrays(GL11.GL_TRIANGLES, 0, currentVao.getVertexCount());
        }

        currentVao.disableVertexAttribArrays();
        currentVao.unbind();

        renderItems.clear();
    }

    @Override
    public void finish() {
        super.finish();

        GL30.glDrawBuffers(0);

        if(useGlow)
            glowEffect();
        combineAndRender();
    }

    private boolean horizontal;

    private void glowEffect() {
        horizontal = true;
        boolean firstIteration = true;
        int blurAmount = (int) (glowIntensity * 10);

        blurShader.use();

        screenQuad.bind();
        screenQuad.enableVertexAttribArrays();

        for (int i = 0; i < blurAmount; i++) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[horizontal ? 1 : 0]);
            blurShader.loadUniform(BlurShader.horizontal, horizontal);
            GL30.glActiveTexture(GL13.GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, firstIteration ? glowColorTexture : blurTextures[horizontal ? 0 : 1]);

            glDrawArrays(GL_TRIANGLES, 0, screenQuad.getVertexCount());

            horizontal = !horizontal;
            if(firstIteration)
                firstIteration = false;
        }

        screenQuad.disableVertexAttribArrays();
        screenQuad.unbind();
    }

    private void combineAndRender() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        blendShader.use();

        screenQuad.bind();
        screenQuad.enableVertexAttribArrays();

        GL30.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, baseColorTexture);
        GL30.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, useGlow ? blurTextures[horizontal ? 0 : 1] : glowColorTexture);

        glDrawArrays(GL_TRIANGLES, 0, screenQuad.getVertexCount());

        screenQuad.disableVertexAttribArrays();
        screenQuad.unbind();
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            disposeFramebuffer();
            createFrameBuffer();

            disposeBlurBuffers();
            createBlurBuffers();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        blendShader.dispose();
        blurShader.dispose();
        screenQuad.dispose();
        disposeFramebuffer();
        disposeBlurBuffers();
    }

    private void disposeFramebuffer() {
        GL30.glDeleteFramebuffers(frameBuffer);
        GL11.glDeleteTextures(baseColorTexture);
        GL11.glDeleteTextures(glowColorTexture);
    }

    private void disposeBlurBuffers() {
        for (int i = 0; i < blurFrameBuffers.length; i++) {
            GL30.glDeleteFramebuffers(blurFrameBuffers[i]);
            GL11.glDeleteTextures(blurTextures[i]);
        }
    }
}
