package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public class ShaderUniformMat4 extends ShaderUniform<Matrix4f> {

    private static final float[] buffer = new float[16];

    public ShaderUniformMat4(String location) {
        super(location, "mat4");
    }

    @Override
    public void loadUniform(Shader shader) {
        value.get(buffer);
        GL20.glUniformMatrix4fv(shaderLocation, false, buffer);
    }
}
