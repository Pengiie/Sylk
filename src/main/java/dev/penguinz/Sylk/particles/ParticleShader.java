package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniform;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniformMat4;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;

import java.util.ArrayList;
import java.util.List;

public class ParticleShader {

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformMat4(UniformConstants.projViewMatrix));
    }

    public static Shader create() {
        return new Shader(
                "#version 400 core\n" +
                        "layout (location = 0) in vec2 in_position;\n" +
                        "layout (location = 1) in vec2 in_texCoord;\n" +
                        "layout (location = 2) in vec4 in_color;\n" +
                        "layout (location = 2) in float in_rotation;\n" +
                        "out vec2 pass_texCoord;\n"+
                        "out vec4 pass_color;\n"+
                        "uniform mat4 "+UniformConstants.projViewMatrix+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  pass_texCoord = in_texCoord;\n"+
                        "  pass_color = in_color;\n"+
                        "  gl_Position = "+ UniformConstants.projViewMatrix+" * vec4(in_position, 0, 1);\n" +
                        "}\n"
                ,
                "#version 400 core\n" +
                        "in vec2 pass_texCoord;\n"+
                        "in vec4 pass_color;\n"+
                        "out vec4 fragColor;\n" +
                        "uniform sampler2D "+UniformConstants.texture0+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  fragColor = pass_color;\n" +
                        "}\n", uniforms);
    }
}
