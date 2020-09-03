package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.shader.MainShader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.MatrixUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glDrawArrays;

/**
 * The main renderer that handles rendering and post processing effects.
 */
public class MainRenderer extends Renderer {

    private RenderLayer renderLayer;

    /**
     * Creates a new renderer instance on the default render layer.
     */
    public MainRenderer() {
        this(RenderLayer.RENDER0);
    }

    /**
     * Creates a new renderer instance that renders to the given layer.
     * @param layer the layer to render to.
     */
    public MainRenderer(RenderLayer layer) {
        super(MainShader.create());
        this.renderLayer = layer;
    }
    @Override
    public void begin(Camera camera) {
        super.begin(camera);
        Application.getInstance().bindRenderLayer(this.renderLayer);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        shader.loadUniform(UniformConstants.projViewMatrix, camera.getProjViewMatrix());
    }

    @Override
    protected void flush() {
        currentVao.bind();
        currentVao.enableVertexAttribArrays();

        for (Item renderItem : renderItems) {
            shader.loadUniform(UniformConstants.transformationMatrix, MatrixUtils.createTransformMatrix(renderItem.transform));

            shader.loadUniform(UniformConstants.color, renderItem.material.color.toVector());
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
    public void dispose() {
        super.dispose();
    }
}
