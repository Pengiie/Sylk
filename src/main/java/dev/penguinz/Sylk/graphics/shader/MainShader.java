package dev.penguinz.Sylk.graphics.shader;

import dev.penguinz.Sylk.graphics.shader.uniforms.*;

import java.util.ArrayList;
import java.util.List;

public class MainShader {

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformVec4(UniformConstants.color));
        uniforms.add(new ShaderUniformBool(UniformConstants.glows));
        uniforms.add(new ShaderUniformBool(UniformConstants.hasTexture));
        uniforms.add(new ShaderUniformSampler2D(UniformConstants.texture0));

        uniforms.add(new ShaderUniformMat4(UniformConstants.projViewMatrix));
        uniforms.add(new ShaderUniformMat4(UniformConstants.transformationMatrix));
    }

    public static Shader create() {
        return new Shader(
                "#version 400 core\n" +
                "layout (location = 0) in vec2 in_position;\n" +
                "layout (location = 1) in vec2 in_texCoord;\n" +
                "out vec2 pass_texCoord;\n"+
                "uniform mat4 "+UniformConstants.projViewMatrix+";\n"+
                "uniform mat4 "+UniformConstants.transformationMatrix+";\n"+
                "void main()\n"+
                "{\n" +
                "  pass_texCoord = in_texCoord;\n"+
                "  gl_Position = "+UniformConstants.projViewMatrix+" * "+UniformConstants.transformationMatrix+" * vec4(in_position.x, in_position.y, 0, 1);\n" +
                "}\n"
                ,
                "#version 400 core\n" +
                "in vec2 pass_texCoord;\n"+
                "layout (location = 0) out vec4 fragColor;\n" +
                "layout (location = 1) out vec4 glowColor;\n" +
                "uniform bool "+UniformConstants.glows+";\n"+
                "uniform vec4 "+UniformConstants.color+";\n"+
                "uniform bool "+UniformConstants.hasTexture+";\n"+
                "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                "void main()\n"+
                "{\n" +
                "  vec4 textureColor = vec4(1, 1, 1, 1);\n"+
                "  if("+UniformConstants.hasTexture+") {\n"+
                "    textureColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n"+
                "  }\n"+
                "  fragColor = textureColor * "+UniformConstants.color+";\n" +
                "  if("+UniformConstants.glows+")\n"+
                "    glowColor = vec4(fragColor.rgb, 1);\n"+
                "  else\n"+
                "    glowColor = vec4(0, 0, 0, 1);\n"+
                "}\n", uniforms);
    }

}
