package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.animation.values.AnimatableValue;
import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Color;
import org.lwjgl.opengl.GL11;

public class UIImage extends UIBlock  {

    public AnimatableValue<Texture> texture;

    public UIImage(Color color, Texture texture) {
        super(color);
        this.texture = new AnimatableValue<>(texture, Texture.class);
    }

    @Override
    public void render(Shader shader) {
        loadMainShaderData(shader);
        shader.loadUniform(UniformConstants.hasTexture, true);
        shader.loadUniform(UniformConstants.texture0, texture);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VAO.quad.getVertexCount());
    }
}
