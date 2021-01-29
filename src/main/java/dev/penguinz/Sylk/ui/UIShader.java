package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.*;
import dev.penguinz.Sylk.ui.font.FontShader;

import java.util.ArrayList;
import java.util.List;

public class UIShader {

    public static final String roundness = "u_roundness";
    public static final String overflow = "u_overflow";
    public static final String overflowBounds = "u_overflowBounds";

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformVec4(UniformConstants.color));
        uniforms.add(new ShaderUniformFloat(UIShader.roundness));
        uniforms.add(new ShaderUniformBool(UniformConstants.hasTexture));
        uniforms.add(new ShaderUniformSampler2D(UniformConstants.texture0));

        uniforms.add(new ShaderUniformVec2(UniformConstants.resolution));
        uniforms.add(new ShaderUniformMat4(UniformConstants.projectionMatrix));
        uniforms.add(new ShaderUniformMat4(UniformConstants.transformationMatrix));

        uniforms.add(new ShaderUniformBool(UIShader.overflow));
        uniforms.add(new ShaderUniformVec4(UIShader.overflowBounds));
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
                        "  gl_Position = "+UniformConstants.projectionMatrix+" * "+UniformConstants.transformationMatrix+" * vec4(in_position, 0, 1);\n"+
                        "}\n"
                ,
                "#version 400 core\n" +
                        "in vec2 pass_texCoord;\n"+
                        "out vec4 fragColor;\n" +
                        "uniform vec2 "+UniformConstants.resolution+";\n"+
                        "uniform float "+UIShader.roundness+";\n"+
                        "uniform vec4 "+UniformConstants.color+";\n"+
                        "uniform bool "+UniformConstants.hasTexture+";\n"+
                        "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                        "uniform bool "+UIShader.overflow+";\n"+
                        "uniform vec4 "+ UIShader.overflowBounds+";\n"+
                        "void main()\n"+
                        "{\n" +
                        "  vec4 fragCoord = gl_FragCoord;\n"+
                        "  float inBounds = step("+ UIShader.overflowBounds+".x, fragCoord.x) * step(fragCoord.x, "+UIShader.overflowBounds+".z) * " +
                        "step(fragCoord.y, "+UIShader.overflowBounds+".w) * step("+UIShader.overflowBounds+".y, fragCoord.y);\n "+
                        "  if(!"+UIShader.overflow+" && inBounds == 0)\n"+
                        "    discard;\n"+
                        "  vec4 textureColor = vec4(1, 1, 1, 1);\n"+
                        "  if("+UniformConstants.hasTexture+") {\n"+
                        "    textureColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n"+
                        "    if(textureColor.a < 0.1)\n"+
                        "        discard;\n"+
                        "  }\n"+
                        "  vec2 pos = pass_texCoord * "+UniformConstants.resolution+";\n"+
                        "  vec2 minPoint = vec2("+UIShader.roundness+");\n"+
                        "  vec2 maxPoint = vec2("+UniformConstants.resolution+".x - minPoint.x, "+UniformConstants.resolution+".y - minPoint.y);\n"+
                        "  if ((pos.x < minPoint.x || pos.x > maxPoint.x) && (pos.y < minPoint.y || pos.y > maxPoint.y)) {\n" +
                        "    vec2 cornerPoint = clamp(pos, minPoint, maxPoint);\n" +
                        "    textureColor.a *= "+UIShader.roundness+" - distance(pos, cornerPoint);\n"+
                        "  }\n"+
                        "  fragColor = textureColor * "+UniformConstants.color+";\n" +
                        "}\n", uniforms);
    }

}
