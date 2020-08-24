package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.lwjgl.opengl.GL20;

public class ShaderUniformInt extends ShaderUniform<Integer> {

    public ShaderUniformInt(String location) {
        super(location, "int");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL20.glUniform1i(shaderLocation, value);
    }
}
