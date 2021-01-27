package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.texture.TextureParameter;
import dev.penguinz.Sylk.util.Disposable;
import org.joml.Vector4f;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class CharacterSet implements Disposable {

    private static float lastXPos = 0;

    private final int CHAR_RANGE = 96;

    private final Font font;

    private final Texture texture = new Texture(TextureParameter.LINEAR_LINEAR, TextureParameter.LINEAR);
    private final int pixelHeight, width, height, overSampling;

    private Character[] characterData;

    private float fontScale;
    private float characterScale;

    private float lineHeight, newLineSpace, lineGap, descent;

    public CharacterSet(Font font, int pixelHeight, int width, int height, int overSampling) {
        this.font = font;
        this.pixelHeight = pixelHeight;
        this.width = width;
        this.height = height;
        this.overSampling = overSampling;

        load();
    }

    public void load() {
        ByteBuffer textureData = MemoryUtil.memAlloc(width * height);

        this.fontScale = STBTruetype.stbtt_ScaleForPixelHeight(font.getFontInfo(), pixelHeight);
        this.characterData = new Character[CHAR_RANGE];


        this.characterScale = (float) 1 / pixelHeight;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer ascent = stack.mallocInt(1);
            IntBuffer descent = stack.mallocInt(1);
            IntBuffer lineGap = stack.mallocInt(1);

            STBTruetype.stbtt_GetFontVMetrics(font.getFontInfo(), ascent, descent, lineGap);

            this.lineHeight = ascent.get(0) - (this.descent = descent.get(0));
            this.newLineSpace = this.lineHeight + (this.lineGap = lineGap.get(0));

            STBTTPackContext packContext = STBTTPackContext.mallocStack(stack);

            STBTTPackedchar.Buffer charBuffer = STBTTPackedchar.mallocStack(CHAR_RANGE, stack);

            STBTruetype.stbtt_PackBegin(packContext, textureData, width, height, 0, 1);
            STBTruetype.stbtt_PackSetOversampling(packContext, overSampling, overSampling);
            STBTruetype.stbtt_PackFontRange(packContext, font.buffer, 0, pixelHeight, Font.START_CHAR, charBuffer);
            STBTruetype.stbtt_PackEnd(packContext);

            texture.setData(textureData, width, height, 1);
            loadCharacterData(charBuffer);
        }
        texture.loadSync();

    }

    private void loadCharacterData(STBTTPackedchar.Buffer charBuffer) {
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

    public Character[] getCharacterData() {
        return characterData;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getFontScale() {
        return fontScale;
    }

    public float getCharacterScale() {
        return characterScale;
    }

    public float getLineHeight() {
        return lineHeight * getFontScale();
    }

    public float getNewLineSpace(float scale) {
        return newLineSpace * getFontScale();
    }

    public float getLineGap(float scale) {
        return lineGap * scale;
    }

    public float getDescent(float scale) {
        return descent * scale;
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
