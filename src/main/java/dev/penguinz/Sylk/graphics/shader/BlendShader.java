package dev.penguinz.Sylk.graphics.shader;

import dev.penguinz.Sylk.graphics.shader.uniforms.*;

import java.util.ArrayList;
import java.util.List;

public class BlendShader {

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
        uniforms.add(new ShaderUniformInt(UniformConstants.texture1));
    }

    public static Shader create() {
        Shader shader = new Shader(
                "#version 400 core\n" +
                        "layout (location = 0) in vec2 in_position;\n" +
                        "layout (location = 1) in vec2 in_texCoord;\n" +
                        "out vec2 pass_texCoord;\n"+
                        "void main()\n"+
                        "{\n" +
                        "  pass_texCoord = in_texCoord;\n"+
                        "  gl_Position = vec4(in_position.x, in_position.y, 0, 1);\n" +
                        "}\n"
                ,
                "#version 400 core\n" +
                        "in vec2 pass_texCoord;\n"+
                        "out vec4 fragColor;\n" +
                        "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                        "uniform sampler2D "+UniformConstants.texture1 +";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  fragColor = texture("+UniformConstants.texture0+", pass_texCoord) + texture("+UniformConstants.texture1+", pass_texCoord);\n" +
                        "}\n", uniforms);
        shader.use();
        shader.loadUniform(UniformConstants.texture0, 0);
        shader.loadUniform(UniformConstants.texture1, 1);
        return shader;
    }

}
