package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.*;

import java.util.ArrayList;
import java.util.List;

public class UIShader {

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformVec4(UniformConstants.color));
        uniforms.add(new ShaderUniformBool(UniformConstants.hasTexture));
        uniforms.add(new ShaderUniformSampler2D(UniformConstants.texture0));

        uniforms.add(new ShaderUniformMat4(UniformConstants.projectionMatrix));
        uniforms.add(new ShaderUniformMat4(UniformConstants.transformationMatrix));
    }

    public static Shader create() {
        return new Shader(
                "#version 400 core\n" +
                        "layout (location = 0) in vec2 in_position;\n" +
                        "layout (location = 1) in vec2 in_texCoord;\n" +
                        "out vec2 pass_texCoord;\n"+
                        "uniform mat4 "+UniformConstants.projectionMatrix+";\n"+
                        "uniform mat4 "+UniformConstants.transformationMatrix+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  pass_texCoord = in_texCoord;\n"+
                        "  gl_Position = "+ UniformConstants.projectionMatrix+" * "+UniformConstants.transformationMatrix+" * vec4(in_position.x, in_position.y, 0, 1);\n" +
                        "}\n"
                ,
                "#version 400 core\n" +
                        "in vec2 pass_texCoord;\n"+
                        "out vec4 fragColor;\n" +
                        "uniform vec4 "+UniformConstants.color+";\n"+
                        "uniform bool "+UniformConstants.hasTexture+";\n"+
                        "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  vec4 textureColor = vec4(1, 1, 1, 1);\n"+
                        "  if("+UniformConstants.hasTexture+") {\n"+
                        "    textureColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n"+
                        "    if(textureColor.a < 0.1)\n"+
                        "        discard;\n"+
                        "  }\n"+
                        "  fragColor = textureColor * "+UniformConstants.color+";\n" +
                        "}\n", uniforms);
    }

}
