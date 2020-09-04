package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.lighting.AmbientLight;
import dev.penguinz.Sylk.graphics.lighting.DirectionalLight;
import dev.penguinz.Sylk.graphics.lighting.Light;
import dev.penguinz.Sylk.graphics.lighting.PointLight;
import dev.penguinz.Sylk.graphics.shader.MainShader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.MatrixUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDrawArrays;

/**
 * The main renderer that handles rendering and post processing effects.
 */
public class MainRenderer extends Renderer {

    private RenderLayer renderLayer;

    private List<Light> lights = new ArrayList<>();

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

    public void addLight(Light light) {
        lights.add(light);
    }

    @Override
    protected void flush() {
        currentVao.bind();
        currentVao.enableVertexAttribArrays();

        for (Item renderItem : renderItems) {
            shader.loadUniform(UniformConstants.transformationMatrix, MatrixUtils.createTransformMatrix(renderItem.transform));

            shader.loadUniform(UniformConstants.color, renderItem.material.color.toVector());
            shader.loadUniform(UniformConstants.hasTexture, renderItem.material.hasTexture());
            if(renderItem.material.hasTexture())
                shader.loadUniform(UniformConstants.texture0, renderItem.material.getTexture());

            int ambientCounter = 0, pointCounter = 0, directionalCounter = 0;
            for (Light light : lights) {
                if(light instanceof AmbientLight) {
                    shader.loadUniform(MainShader.ambientLight+"["+ambientCounter+"].color", light.color.toVector());
                    shader.loadUniform(MainShader.ambientLight+"["+ambientCounter+"].intensity", ((AmbientLight) light).intensity);
                    ambientCounter++;
                } else if(light instanceof PointLight) {
                    shader.loadUniform(MainShader.pointLight+"["+pointCounter+"].color", light.color.toVector());
                    shader.loadUniform(MainShader.pointLight+"["+pointCounter+"].position", ((PointLight) light).position);
                    shader.loadUniform(MainShader.pointLight+"["+pointCounter+"].intensity", ((PointLight) light).intensity);
                    pointCounter++;
                } else if(light instanceof DirectionalLight) {
                    shader.loadUniform(MainShader.directionalLight+"["+pointCounter+"].color", light.color.toVector());
                    shader.loadUniform(MainShader.directionalLight+"["+pointCounter+"].position", ((DirectionalLight) light).position);
                    shader.loadUniform(MainShader.directionalLight+"["+pointCounter+"].direction", ((DirectionalLight) light).direction);
                    shader.loadUniform(MainShader.directionalLight+"["+pointCounter+"].intensity", ((DirectionalLight) light).intensity);
                    shader.loadUniform(MainShader.directionalLight+"["+pointCounter+"].angle", ((DirectionalLight) light).angle);
                    directionalCounter++;
                }
            }

            glDrawArrays(GL11.GL_TRIANGLES, 0, currentVao.getVertexCount());
        }

        currentVao.disableVertexAttribArrays();
        currentVao.unbind();

        renderItems.clear();
    }

    @Override
    public void finish() {
        super.finish();
        lights.clear();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
