package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.graphics.post.EffectsLayer;
import dev.penguinz.Sylk.graphics.post.effects.PostEffect;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniform;
import dev.penguinz.Sylk.graphics.shader.uniforms.ShaderUniformInt;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.Layer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class ApplicationRenderer implements Disposable {

    public static final VAO screenQuad = new VAO(new VBO(new float[] {
            -1, 1,
            1, -1,
            -1, -1,
            -1, 1,
            1, -1,
            1, 1
    }, 2, VBOType.VERTICES), new VBO(new float[] {
            0, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 0,
            1, 1
    }, 2, VBOType.TEXTURE_COORDS));

    private final Shader shader;

    private final EffectsLayer[] layers = new EffectsLayer[RenderLayer.values().length];

    private final List<Integer> usedLayers = new ArrayList<>();

    public ApplicationRenderer() {
        this.shader = ApplicationShader.create();

        for (int i = 0; i < layers.length; i++) {
            layers[i] = new EffectsLayer();
        }
    }

    public void addEffect(RenderLayer layer, PostEffect effect) {
        layers[layer.ordinal()].addEffect(effect);
    }

    public void renderToLayer(RenderLayer layer) {
        usedLayers.add(layer.ordinal());
        layers[layer.ordinal()].bindBuffer();
    }

    public void render() {
        List<Integer> usedTextures = new ArrayList<>();
        screenQuad.bind();
        screenQuad.enableVertexAttribArrays();
        for (int i = 0; i < layers.length; i++) {
            if(usedLayers.contains(i))
                usedTextures.add(layers[i].process());
        }
        shader.use();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        glEnable(GL11.GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        for (int texture : usedTextures) {
            GL11.glBindTexture(GL_TEXTURE_2D, texture);
            glDrawArrays(GL_TRIANGLES, 0, screenQuad.getVertexCount());
        }
        glBlendFunc(GL_ONE, GL_ONE);
        for (int i = 0; i < layers.length; i++) {
            if(usedLayers.contains(i)) {
                GL11.glBindTexture(GL_TEXTURE_2D, layers[i].getBlurTexture());
                glDrawArrays(GL_TRIANGLES, 0, screenQuad.getVertexCount());
            }
        }
        glDisable(GL11.GL_BLEND);
        screenQuad.disableVertexAttribArrays();
        screenQuad.unbind();
        for (int i = 0; i < layers.length; i++) {
            if(usedLayers.contains(i))
                layers[i].clearBuffer();
        }
        usedLayers.clear();
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            for (EffectsLayer layer : layers) {
                layer.onEvent(event);
            }
        }
    }

    @Override
    public void dispose() {
        for (EffectsLayer layer : layers) {
            layer.dispose();
        }
        shader.dispose();
        screenQuad.dispose();
    }

    private static class ApplicationShader {
        private static final List<ShaderUniform<?>> uniforms = new ArrayList<>();

        static {
            uniforms.add(new ShaderUniformInt(UniformConstants.texture0));
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
                            "void main()\n"+
                            "{\n" +
                            "  vec4 texColor = texture("+UniformConstants.texture0+", pass_texCoord);\n" +
                            "  if(texColor.a < 0.1)\n"+
                            "    discard;\n"+
                            "  fragColor = texColor;\n"+
                            "}\n", uniforms);
            shader.use();
            return shader;
        }
    }

}
