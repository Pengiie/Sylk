package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.*;

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
                        "out vec2 pass_texCoord;\n"+
                        "uniform mat4 "+UniformConstants.projViewMatrix+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  pass_texCoord = in_texCoord;\n"+
                        "  gl_Position = "+ UniformConstants.projViewMatrix+" * vec4(in_position.x, in_position.y, 0, 1);\n" +
                        "}\n"
                ,
                "#version 400 core\n" +
                        "in vec2 pass_texCoord;\n"+
                        "out vec4 fragColor;\n" +
                        "uniform sampler2D "+UniformConstants.texture0+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  vec4 textureColor = vec4(1, 1, 1, 1);\n"+
                        "  if("+UniformConstants.hasTexture+") {\n"+
                        "    textureColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n"+
                        "  }\n"+
                        "  if(textureColor.a < 0.1)\n"+
                        "    discard;\n"+
                        "  fragColor = "+UniformConstants.color+";\n" +
                        "}\n", uniforms);
    }
}
