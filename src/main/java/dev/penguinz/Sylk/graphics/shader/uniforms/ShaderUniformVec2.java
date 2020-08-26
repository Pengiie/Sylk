package dev.penguinz.Sylk.graphics.shader.uniforms;

import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class ShaderUniformVec2 extends ShaderUniform<Vector2> {

    public ShaderUniformVec2(String location) {
        super(location, "vec2");
    }

    @Override
    public void loadUniform(Shader shader) {
        GL20.glUniform2f(shaderLocation, value.x, value.y);
    }
}
