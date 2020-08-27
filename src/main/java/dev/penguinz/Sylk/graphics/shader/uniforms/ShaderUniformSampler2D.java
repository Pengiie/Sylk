package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.shader.Shader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class ShaderUniformSampler2D extends ShaderUniform<Texture> {

    public ShaderUniformSampler2D(String location) {
        super(location, "sampler2D");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL30.glActiveTexture(GL13.GL_TEXTURE0);
        if(value == null) GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        else value.bind();
    }
}
