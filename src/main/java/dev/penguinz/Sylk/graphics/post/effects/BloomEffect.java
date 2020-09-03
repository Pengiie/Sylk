package dev.penguinz.Sylk.graphics.post.effects;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.animation.values.AnimatableInt;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.ApplicationRenderer;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class BloomEffect implements PostEffect {

    private final AnimatableFloat size;
    private final AnimatableInt smoothness;
    private final AnimatableFloat strength;
    private final AnimatableFloat opacity;

    private final OrthographicCamera camera;
    private final float startWidth, startHeight;

    private final Shader splitShader;
    private int splitBuffer, splitTexture1, splitTexture2;

    private final Shader blurShader;
    private int[] blurFrameBuffers;
    private int[] blurTextures;

    private final Shader blendShader;
    private int finalBuffer, finalTexture;

    public BloomEffect(OrthographicCamera camera, float size, int smoothness, float strength, float opacity) {
        this.camera = camera;
        this.startWidth = Application.getInstance().getWindowWidth();
        this.startHeight = Application.getInstance().getWindowHeight();

        this.size = new AnimatableFloat(size);
        this.smoothness = new AnimatableInt(smoothness);
        this.strength = new AnimatableFloat(strength);
        this.opacity = new AnimatableFloat(opacity);

        this.splitShader = SplitShader.create();
        this.blurShader = BlurShader.create();
        this.blendShader = BlendShader.create();

        createBuffers();
    }

    private void createBuffers() {
        this.splitBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.splitBuffer);
        int[] colorBuffers = new int[] {this.splitTexture1 = GL11.glGenTextures(), this.splitTexture2 = GL11.glGenTextures()};
        for (int i = 0; i < 2; i++) {
            glBindTexture(GL_TEXTURE_2D, colorBuffers[i]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGBA,
                    (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                    0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

            setTextureParams();

            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, colorBuffers[i], 0);
        }

        this.blurFrameBuffers = new int[] { GL30.glGenFramebuffers(), GL30.glGenFramebuffers() };
        this.blurTextures = new int[] { glGenTextures(), glGenTextures() };
        for (int i = 0; i < this.blurFrameBuffers.length; i++) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[i]);
            glBindTexture(GL_TEXTURE_2D, blurTextures[i]);
            glTexImage2D(
                    GL_TEXTURE_2D, 0, GL_RGBA,
                    (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                    0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

            setTextureParams();

            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, blurTextures[i], 0);
        }

        this.finalBuffer = GL30.glGenFramebuffers();
        this.finalTexture = glGenTextures();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);
        glBindTexture(GL_TEXTURE_2D, finalTexture);
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA,
                (int) Application.getInstance().getWindowWidth(), (int) Application.getInstance().getWindowHeight(),
                0, GL_RGBA, GL_FLOAT, (ByteBuffer) null);

        setTextureParams();

        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, finalTexture, 0);
    }

    private void setTextureParams() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    public int processEffect(int workingTexture) {
        splitShader.use();
        splitShader.loadUniform(SplitShader.opacity, opacity.value);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, splitBuffer);
        GL30.glDrawBuffers(new int[] {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1});
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL11.glBindTexture(GL_TEXTURE_2D, workingTexture);
        GL11.glDrawArrays(GL_TRIANGLES, 0, ApplicationRenderer.screenQuad.getVertexCount());

        boolean horizontal = true;
        boolean firstIteration = true;
        int blurAmount = smoothness.value * 2;

        blurShader.use();
        blurShader.loadUniform(BlurShader.strength, strength.value);

        for (int i = 0; i < blurAmount; i++) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[horizontal ? 1 : 0]);
            GL30.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
            blurShader.loadUniform(BlurShader.size,
                    size.value * camera.zoom *
                            (horizontal ? Application.getInstance().getWindowWidth() / startWidth : Application.getInstance().getWindowHeight()/startHeight));
            blurShader.loadUniform(BlurShader.horizontal, horizontal);
            glBindTexture(GL_TEXTURE_2D, firstIteration ? splitTexture2 : blurTextures[horizontal ? 0 : 1]);
            glDrawArrays(GL_TRIANGLES, 0, ApplicationRenderer.screenQuad.getVertexCount());

            horizontal = !horizontal;
            if(firstIteration)
                firstIteration = false;
        }

        blendShader.use();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);
        GL30.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, splitTexture1);
        GL30.glActiveTexture(GL13.GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, blurTextures[horizontal ? 0 : 1]);
        glDrawArrays(GL_TRIANGLES, 0, ApplicationRenderer.screenQuad.getVertexCount());

        return finalTexture;
    }

    private void disposeBuffers() {
        GL30.glDeleteFramebuffers(splitBuffer);
        GL11.glDeleteTextures(splitTexture1);
        GL11.glDeleteTextures(splitTexture2);

        for (int i = 0; i < this.blurFrameBuffers.length; i++) {
            GL30.glDeleteFramebuffers(blurFrameBuffers[i]);
            GL11.glDeleteTextures(blurTextures[i]);
        }

        GL30.glDeleteFramebuffers(finalBuffer);
        glDeleteTextures(finalTexture);
    }

    @Override
    public void clearBuffers() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, splitBuffer);
        GL30.glDrawBuffers(new int[] {GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1});
        GL30.glClearBufferfv(GL_COLOR, 0, new float[] {0,0,0,0});
        GL30.glClearBufferfv(GL_COLOR, 1, new float[] {0,0,0,0});
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[0]);
        GL30.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glClearBufferfv(GL_COLOR, 0, new float[] {0,0,0,0});
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, blurFrameBuffers[1]);
        GL30.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glClearBufferfv(GL_COLOR, 0, new float[] {0,0,0,0});
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, finalBuffer);
        GL30.glDrawBuffers(GL30.GL_COLOR_ATTACHMENT0);
        GL30.glClearBufferfv(GL_COLOR, 0, new float[] {0,0,0,0});
    }

    @Override
    public void onEvent(Event event) {
        disposeBuffers();
        createBuffers();
    }

    public AnimatableFloat getSize() {
        return size;
    }

    public AnimatableInt getStrength() {
        return smoothness;
    }

    public AnimatableFloat getOpacity() {
        return opacity;
    }

    @Override
    public void dispose() {
        disposeBuffers();
        splitShader.dispose();
        blurShader.dispose();
        blendShader.dispose();
    }

    private static class SplitShader {

        public static final String opacity = "u_opacity";
        
        private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();
        
        static {
            uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
            uniforms.add(new ShaderUniformFloat(SplitShader.opacity));
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
                            "layout (location = 0) out vec4 fragColor;\n" +
                            "layout (location = 1) out vec4 bloomColor;\n" +
                            "uniform float "+SplitShader.opacity+";\n"+
                            "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                            "void main()\n"+
                            "{\n" +
                            "  fragColor = texture("+UniformConstants.texture0 +", pass_texCoord);\n" +
                            "  bloomColor = vec4(fragColor.xyz, "+SplitShader.opacity+" * fragColor.a);\n"+
                            "}\n", uniforms);
        }

    }

    private static class BlurShader {

        public static final String horizontal = "u_horizontal";
        public static final String size = "u_scale";
        public static final String strength = "u_strength";

        private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

        static {
            uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
            uniforms.add(new ShaderUniformBool(horizontal));
            uniforms.add(new ShaderUniformFloat(size));
            uniforms.add(new ShaderUniformFloat(strength));
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
                            "uniform sampler2D "+UniformConstants.texture0 +";\n"+
                            "uniform bool "+ BlurShader.horizontal +";\n"+
                            "uniform float "+BlurShader.size +";\n"+
                            "uniform float "+BlurShader.strength+";\n"+
                            "uniform float weights[6] = float [] (0.198596, 0.175713, 0.121703, 0.065984, 0.028002, 0.0093);\n"+
                            "uniform float offsets[6] = float [] (0, 0.1, 0.3, 0.8, 1.5, 3.0);\n"+
                            "void main()\n"+
                            "{\n" +
                            "  vec2 texSize = 1.0 / textureSize("+ UniformConstants.texture0+", 0);\n"+
                            "  vec4 result = texture("+UniformConstants.texture0+", pass_texCoord) * weights[0];\n"+
                            "  if("+ BlurShader.horizontal+") {\n"+
                            "    for(int i = 1; i < 6; ++i) {\n"+
                            "      result += texture("+UniformConstants.texture0+", pass_texCoord + vec2(offsets[i] * texSize.x * "+BlurShader.size+", 0.0)) * weights[i] * "+BlurShader.strength+";\n"+
                            "      result += texture("+UniformConstants.texture0+", pass_texCoord - vec2(offsets[i] * texSize.x * "+BlurShader.size+", 0.0)) * weights[i] * "+BlurShader.strength+";\n"+
                            "    }\n"+
                            "  } else {\n"+
                            "    for(int i = 1; i < 6; ++i) {\n"+
                            "      result += texture("+UniformConstants.texture0+", pass_texCoord + vec2(0.0, offsets[i] * texSize.y * "+BlurShader.size+")) * weights[i] * "+BlurShader.strength+";\n"+
                            "      result += texture("+UniformConstants.texture0+", pass_texCoord - vec2(0.0, offsets[i] * texSize.y * "+BlurShader.size+")) * weights[i] * "+BlurShader.strength+";\n"+
                            "    }\n"+
                            "  }\n"+
                            "  fragColor = vec4(result.rgb, 1);\n" +
                            "}\n", uniforms);
        }

    }

    private static class BlendShader {

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
                            "  vec4 blurColor = texture("+UniformConstants.texture1+", pass_texCoord);\n"+
                            "  vec4 texColor = texture("+UniformConstants.texture0+", pass_texCoord);\n"+
                            "  fragColor = texColor + blurColor;\n"+ //vec4(blurColor.rgb, pow((blurColor.r + blurColor.g * 2 + blurColor.b)/2,1)*1.1);\n"+
                            "}\n", uniforms);
            shader.use();
            shader.loadUniform(UniformConstants.texture0, 0);
            shader.loadUniform(UniformConstants.texture1, 1);
            return shader;
        }

    }

}
