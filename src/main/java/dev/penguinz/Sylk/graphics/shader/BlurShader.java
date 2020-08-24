package dev.penguinz.Sylk.graphics.shader;

import dev.penguinz.Sylk.graphics.shader.uniforms.*;

import java.util.ArrayList;
import java.util.List;

public class BlurShader {

    public static final String horizontal = "u_horizontal";

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
        uniforms.add(new ShaderUniformBool(horizontal));
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
                        "uniform bool "+BlurShader.horizontal +";\n"+
                        "uniform float weight[5] = float [] (0.277027, 0.1945946, 0.1216216, 0.054054, 0.016216);\n"+
                        "void main()\n"+
                        "{\n" +
                        "  vec2 texOffset = 1.0 / textureSize("+UniformConstants.texture0+", 0);\n"+
                        "  vec3 result = texture("+UniformConstants.texture0+", pass_texCoord).rgb * weight[0];\n"+
                        "  if("+BlurShader.horizontal+") {\n"+
                        "    for(int i = 1; i < 5; ++i) {\n"+
                        "      result += texture("+UniformConstants.texture0+", pass_texCoord + vec2(texOffset.x * i, 0.0)).rgb * weight[i];\n"+
                        "      result += texture("+UniformConstants.texture0+", pass_texCoord - vec2(texOffset.x * i, 0.0)).rgb * weight[i];\n"+
                        "    }\n"+
                        "  } else {\n"+
                        "    for(int i = 1; i < 5; ++i) {\n"+
                        "      result += texture("+UniformConstants.texture0+", pass_texCoord + vec2(0.0, texOffset.y * i)).rgb * weight[i];\n"+
                        "      result += texture("+UniformConstants.texture0+", pass_texCoord - vec2(0.0, texOffset.y * i)).rgb * weight[i];\n"+
                        "    }\n"+
                        "  }\n"+
                        "  fragColor = vec4(result.rgb, 1.0);\n" +
                        "}\n", uniforms);
        shader.use();
        shader.loadUniform(UniformConstants.texture0, 0);
        return shader;
    }

}
