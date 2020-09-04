package dev.penguinz.Sylk.graphics.shader;

import dev.penguinz.Sylk.graphics.shader.uniforms.*;

import java.util.ArrayList;
import java.util.List;

public class MainShader {

    public static final String textureOffset = "u_texOffset";
    public static final String textureSize = "u_texSize";

    public static final String ambientLight = "u_ambientLight";
    public static final String pointLight = "u_pointLight";
    public static final String directionalLight = "u_directionalList";

    private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

    static {
        uniforms.add(new ShaderUniformVec4(UniformConstants.color));
        uniforms.add(new ShaderUniformBool(UniformConstants.hasTexture));
        uniforms.add(new ShaderUniformSampler2D(UniformConstants.texture0));
        uniforms.add(new ShaderUniformVec2(textureOffset));
        uniforms.add(new ShaderUniformVec2(textureSize));

        uniforms.add(new ShaderUniformMat4(UniformConstants.projViewMatrix));
        uniforms.add(new ShaderUniformMat4(UniformConstants.transformationMatrix));

        for (int i = 0; i < 10; i++) {
            uniforms.add(new ShaderUniformVec4(ambientLight+"["+i+"].color"));
            uniforms.add(new ShaderUniformFloat(ambientLight+"["+i+"].intensity"));
        }

        for (int i = 0; i < 5; i++) {
            uniforms.add(new ShaderUniformVec4(pointLight+"["+i+"].color"));
            uniforms.add(new ShaderUniformVec2(pointLight+"["+i+"].position"));
            uniforms.add(new ShaderUniformFloat(pointLight+"["+i+"].intensity"));
        }

        for (int i = 0; i < 5; i++) {
            uniforms.add(new ShaderUniformVec4(directionalLight+"["+i+"].color"));
            uniforms.add(new ShaderUniformVec2(directionalLight+"["+i+"].position"));
            uniforms.add(new ShaderUniformVec2(directionalLight+"["+i+"].direction"));
            uniforms.add(new ShaderUniformFloat(directionalLight+"["+i+"].intensity"));
            uniforms.add(new ShaderUniformFloat(directionalLight+"["+i+"].angle"));
        }
    }

    public static Shader create() {
        return new Shader(
                "#version 400 core\n" +
                "layout (location = 0) in vec2 in_position;\n" +
                "layout (location = 1) in vec2 in_texCoord;\n" +
                "out vec2 pass_texCoord;\n"+
                "out vec4 pass_position;\n"+
                "uniform mat4 "+UniformConstants.projViewMatrix+";\n"+
                "uniform mat4 "+UniformConstants.transformationMatrix+";\n"+
                "uniform vec2 "+textureOffset+";\n"+
                "uniform vec2 "+textureSize+";\n"+
                "void main()\n"+
                "{\n" +
                "  pass_texCoord = "+textureOffset+" + in_texCoord * "+textureSize+";\n"+
                "  pass_position = "+UniformConstants.transformationMatrix+" * vec4(in_position.x, in_position.y, 0, 1);\n"+
                "  gl_Position = "+UniformConstants.projViewMatrix+" * pass_position;\n" +
                "}\n"
                ,
                "#version 400 core\n" +
                "in vec2 pass_texCoord;\n"+
                "in vec4 pass_position;\n"+
                "out vec4 fragColor;\n" +
                "uniform vec4 "+UniformConstants.color+";\n"+
                "uniform bool "+UniformConstants.hasTexture+";\n"+
                "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                "struct AmbientLight {\n"+
                "  vec4 color;\n"+
                "  float intensity;\n"+
                "};\n"+
                "uniform AmbientLight "+ambientLight+"[10];\n"+
                "struct PointLight {\n"+
                "  vec4 color;\n"+
                "  vec2 position;\n"+
                "  float intensity;\n"+
                "};\n"+
                "uniform PointLight "+pointLight+"[5];\n"+
                "struct DirectionalLight {\n"+
                "  vec4 color;\n"+
                "  vec2 position;\n"+
                "  vec2 direction;\n"+
                "  float intensity;\n"+
                "  float angle;\n"+
                "};\n"+
                "uniform DirectionalLight "+directionalLight+"[5];\n"+
                "vec4 CalculatePointLight(PointLight light, vec2 fragPos) {\n"+
                "  float distance = distance(light.position, fragPos);\n"+
                "  return light.color * smoothstep(0, step(distance, light.intensity), 1 - distance/light.intensity);\n"+
                "}\n"+
                "vec4 CalculateDirectionalLight(DirectionalLight light, vec2 fragPos) {\n"+
                "  float distance = distance(light.position, fragPos);\n"+
                "  float directionInfluence = min(max(dot(normalize(fragPos - light.position), normalize(light.direction)), 0)*light.angle, 1);\n"+
                "  return light.color * directionInfluence * smoothstep(0, step(distance, light.intensity), 1 - distance/light.intensity);\n"+
                "}\n"+
                "void main()\n"+
                "{\n" +
                "  vec4 textureColor = vec4(1, 1, 1, 1);\n"+
                "  if("+UniformConstants.hasTexture+") {\n"+
                "    textureColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n"+
                "  }\n"+
                "  if(textureColor.a < 0.1)\n"+
                "    discard;\n"+
                "  vec4 lightResult = vec4(0, 0, 0, 0);\n"+
                "  for(int i = 0; i < 5; i++)\n"+
                "    lightResult += CalculatePointLight("+pointLight+"[i], pass_position.xy);\n"+
                "  for(int i = 0; i < 5; i++)\n"+
                "    lightResult += CalculateDirectionalLight("+directionalLight+"[i], pass_position.xy);\n"+
                "  vec4 ambientResult = vec4(0, 0, 0, 0);\n"+
                "  for(int i = 0; i < 10; i++)\n"+
                "    ambientResult += "+ambientLight+"[i].color * "+ambientLight+"[i].intensity;\n"+
                "  ambientResult = min(ambientResult, 1);\n"+
                "  lightResult = max(lightResult, ambientResult);\n"+
                "  fragColor = lightResult * textureColor * "+UniformConstants.color+";\n" +
                "}\n", uniforms);
    }

}
