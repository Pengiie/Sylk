package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.lwjgl.opengl.GL20;

public class ShaderUniformFloat extends ShaderUniform<Float> {

    public ShaderUniformFloat(String location) {
        super(location, "float");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL20.glUniform1f(shaderLocation, value);
    }
}
