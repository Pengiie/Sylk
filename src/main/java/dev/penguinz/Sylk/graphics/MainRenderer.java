package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.shader.MainShader;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniform;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniformVec4;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.maths.Transform;
import org.lwjgl.opengl.GL11;

public class MainRenderer extends Renderer {

    public MainRenderer() {
        super(MainShader.create());
    }

    @Override
    public void begin(Camera camera) {
        super.begin(camera);
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
                shader.loadUniform(UniformConstants.texture, renderItem.material.texture);

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, currentVao.getVertexCount());
        }

        currentVao.disableVertexAttribArrays();
        currentVao.unbind();

        renderItems.clear();
    }
}
