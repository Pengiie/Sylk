package dev.penguinz.Sylk.graphics.post.effects;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.ApplicationRenderer;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.*;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class VignetteEffect implements PostEffect {

    private AnimatableFloat radius;
    private AnimatableFloat softness;

    private final Shader vignetteShader;
    private int finalBuffer, finalTexture;

    public VignetteEffect(float radius, float softness) {
        this.radius = new AnimatableFloat(radius);
        this.softness = new AnimatableFloat(softness);

        this.vignetteShader = VignetteShader.create();

        createBuffers();
    }

    private void createBuffers() {
        this.finalBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);

        this.finalTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, finalTexture);
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA,
                (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, finalTexture, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    private void disposeBuffers() {
        GL30.glDeleteFramebuffers(finalBuffer);
        glDeleteTextures(finalTexture);
    }

    @Override
    public int processEffect(int workingTexture) {
        vignetteShader.use();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);
        GL30.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, workingTexture);
        vignetteShader.loadUniform(UniformConstants.resolution, new Vector2(Application.getInstance().getWindowWidth(), Application.getInstance().getWindowHeight()));
        vignetteShader.loadUniform(VignetteShader.radius, radius.value);
        vignetteShader.loadUniform(VignetteShader.softness, softness.value);
        vignetteShader.loadUniform(VignetteShader.opacity, 1f);
        GL11.glDrawArrays(GL_TRIANGLES, 0, ApplicationRenderer.screenQuad.getVertexCount());
        System.out.println(GL30.glGetInteger(GL30.GL_READ_FRAMEBUFFER_BINDING) +" drawing "+finalBuffer);
        return finalTexture;
    }

    @Override
    public void clearBuffers() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);
        GL30.glClearBufferfv(GL_COLOR, GL30.GL_COLOR_ATTACHMENT0, new float[] {0,0,0,0});
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    @Override
    public void onEvent(Event event) {
        disposeBuffers();
        createBuffers();
    }

    @Override
    public void dispose() {
        disposeBuffers();
        this.vignetteShader.dispose();
    }

    private static class VignetteShader {

        public static final String radius = "u_radius";
        public static final String softness = "u_softness";
        public static final String opacity = "u_opacity";

        private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

        static {
            uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
            uniforms.add(new ShaderUniformVec2(UniformConstants.resolution));
            uniforms.add(new ShaderUniformFloat(VignetteShader.radius));
            uniforms.add(new ShaderUniformFloat(VignetteShader.softness));
            uniforms.add(new ShaderUniformFloat(VignetteShader.opacity));
        }

        public static Shader create() {
            return new Shader(
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
                            "uniform sampler2D "+ UniformConstants.texture0 +";\n"+
                            "uniform vec2 "+ UniformConstants.resolution  +";\n"+
                            "uniform float "+VignetteShader.radius +";\n"+
                            "uniform float "+VignetteShader.softness +";\n"+
                            "uniform float "+VignetteShader.opacity +";\n"+
                            "void main()\n"+
                            "{\n" +
                            "  vec4 texColor = texture("+UniformConstants.texture0+", pass_texCoord);\n" +
                            "  vec2 position = (gl_FragCoord.xy / "+UniformConstants.resolution+") - vec2(0.5);\n"+
                            "  float length = length(position);\n"+
                            "  float vignette = smoothstep("+VignetteShader.radius+", "+VignetteShader.softness+", length);\n"+
                            "  fragColor = vec4(mix(texColor.rgb, texColor.rgb * vignette, "+VignetteShader.opacity+"), texColor.a);\n"+
                            "}\n", uniforms);
        }

    }
}
