package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.lwjgl.opengl.GL20;

public class ShaderUniformBool extends ShaderUniform<Boolean> {

    public ShaderUniformBool(String location) {
        super(location, "bool");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL20.glUniform1i(shaderLocation, value ? 1 : 0);
    }
}
