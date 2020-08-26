package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.assets.Texture;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.IOUtils;

import jdk.javadoc.internal.tool.Start;
import org.joml.Vector4f;
import org.lwjgl.stb.*;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Font implements Disposable {

    public final static int START_CHAR = 32;

    private final Texture texture = new Texture();
    private ByteBuffer textureData;

    private final STBTTFontinfo font = STBTTFontinfo.create();
    private STBTTPackedchar.Buffer charBuffer;

    private Character[] characterData;
    
    private float fontScale;
    private float characterScale;

    private float lineHeight, newLineSpace, lineGap, descent;

    public void loadAsync(String path, int charRange, int pixelHeight, int width, int height) {
        ByteBuffer buffer = IOUtils.loadFile(path);
        
        this.textureData = ByteBuffer.allocateDirect(width * height);
        
        this.charBuffer = STBTTPackedchar.create(charRange);
        this.characterData = new Character[charRange];

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
        }

        STBTTPackContext packContext = STBTTPackContext.create();

        STBTruetype.stbtt_PackBegin(packContext, this.textureData, width, height, 0, 1);
        STBTruetype.stbtt_PackSetOversampling(packContext, 2, 2);
        STBTruetype.stbtt_PackFontRange(packContext, buffer, 0, pixelHeight, START_CHAR, charBuffer);
        STBTruetype.stbtt_PackEnd(packContext);

        STBImageWrite.stbi_write_png("testFont.png", width, height, 1, this.textureData, 0);


        texture.setData(textureData, width, height, 1);
        loadCharacterData();
    }
    
    private void loadCharacterData() {
        float lastXPos = 0;

        for (int i = 0; i < characterData.length; i++) {
            try(MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer xPos = stack.mallocFloat(1);
                FloatBuffer yPos = stack.mallocFloat(1);

                STBTTAlignedQuad quad = STBTTAlignedQuad.mallocStack(stack);

                STBTruetype.stbtt_GetPackedQuad(charBuffer, texture.getWidth(), texture.getHeight(), i, xPos, yPos, quad, false);

                characterData[i] = new Character(i,
                        new Vector4f(quad.x0(), quad.y0(), quad.x1(), quad.y1()),
                        new Vector4f(quad.s0(), quad.t0(), quad.s1(), quad.t1()),
                        xPos.get(0) - lastXPos, quad.y1());
                System.out.println((char) (i+START_CHAR)+" "+(i+START_CHAR)+" "+characterData[i].texturePosition+" "+characterData[i].advance+" "+characterData[i].descent);

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

    @Override
    public void dispose() {
        STBTruetype.stbtt_FreeBitmap(textureData);
        this.texture.dispose();
    }
}
