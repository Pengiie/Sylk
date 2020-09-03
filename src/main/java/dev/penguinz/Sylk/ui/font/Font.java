package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.texture.TextureParameter;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.IOUtils;
import org.joml.Vector4f;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Font implements Disposable {

    public final static int START_CHAR = 32;

    private final Texture texture = new Texture(TextureParameter.LINEAR_NEAREST, TextureParameter.LINEAR);

    private STBTTFontinfo font;

    private Character[] characterData;
    
    private float fontScale;
    private float characterScale;

    private float lineHeight, newLineSpace, lineGap, descent;

    public void loadAsync(String path, int charRange, int pixelHeight, int width, int height, int overSampling) {
        ByteBuffer buffer = IOUtils.loadFile(path);

        ByteBuffer textureData = MemoryUtil.memAlloc(width * height);

        this.characterData = new Character[charRange];

        this.font = STBTTFontinfo.malloc();
        STBTruetype.stbtt_InitFont(font, buffer);
        this.fontScale = STBTruetype.stbtt_ScaleForPixelHeight(font, pixelHeight);
        this.characterScale = (float) 1 / pixelHeight;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ascent = stack.mallocInt(1);
            IntBuffer descent = stack.mallocInt(1);
            IntBuffer lineGap = stack.mallocInt(1);

            STBTruetype.stbtt_GetFontVMetrics(font, ascent, descent, lineGap);

            this.lineHeight = ascent.get(0) - (this.descent = descent.get(0));
            this.newLineSpace = this.lineHeight + (this.lineGap = lineGap.get(0));

            STBTTPackContext packContext = STBTTPackContext.mallocStack(stack);

            STBTTPackedchar.Buffer charBuffer = STBTTPackedchar.mallocStack(charRange, stack);

            STBTruetype.stbtt_PackBegin(packContext, textureData, width, height, 0, 1);
            STBTruetype.stbtt_PackSetOversampling(packContext, overSampling, overSampling);
            STBTruetype.stbtt_PackFontRange(packContext, buffer, 0, pixelHeight, START_CHAR, charBuffer);
            STBTruetype.stbtt_PackEnd(packContext);

            texture.setData(textureData, width, height, 1);
            loadCharacterData(charBuffer);
        }
    }
    
    private void loadCharacterData(STBTTPackedchar.Buffer charBuffer) {
        float lastXPos = 0;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer xPos = stack.mallocFloat(1);
            FloatBuffer yPos = stack.mallocFloat(1);

            STBTTAlignedQuad quad = STBTTAlignedQuad.mallocStack(stack);
            for (int i = 0; i < characterData.length; i++) {
                STBTruetype.stbtt_GetPackedQuad(charBuffer, texture.getWidth(), texture.getHeight(), i, xPos, yPos, quad, true);

                characterData[i] = new Character(i,
                        new Vector4f(quad.x0(), quad.y0(), quad.x1(), quad.y1()),
                        new Vector4f(quad.s0(), quad.t0(), quad.s1(), quad.t1()),
                        xPos.get(0) - lastXPos - (quad.x1() - quad.x0()), quad.y1());

                lastXPos = xPos.get(0);
            }
        }
    }

    public void loadSync() {
        texture.loadSync();
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Character[] getCharacterData() {
        return characterData;
    }

    public float getFontScale() {
        return fontScale;
    }

    public float getFontScale(float pixelHeight) {
        return pixelHeight / (lineHeight);
    }

    public float getCharacterScale() {
        return characterScale;
    }

    public float getLineHeight(float scale) {
        return lineHeight * scale;
    }

    public float getNewLineSpace(float scale) {
        return newLineSpace * scale;
    }

    public float getLineGap(float scale) {
        return lineGap * scale;
    }

    public float getDescent(float scale) {
        return descent * scale;
    }

    public float getKernAdvance(float scale, int codepointLeft, int codepointRight) {
        return STBTruetype.stbtt_GetCodepointKernAdvance(font, codepointLeft, codepointRight);
    }

    @Override
    public void dispose() {
        font.free();
        this.texture.dispose();
    }
}
