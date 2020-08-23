package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class ShaderUniformVec4 extends ShaderUniform<Vector4f> {

    public ShaderUniformVec4(String location) {
        super(location, "vec4");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL20.glUniform4f(shaderLocation, value.x, value.y, value.z, value.w);
    }
}
