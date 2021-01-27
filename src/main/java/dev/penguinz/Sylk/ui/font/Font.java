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
import java.util.HashMap;

public class Font implements Disposable {

    public final static int START_CHAR = 32, WIDTH = 1028, HEIGHT = 1028;

    private STBTTFontinfo font;

    private final HashMap<Integer, CharacterSet> characterSets = new HashMap<>();

    ByteBuffer buffer;

    public void loadAsync(String path) {
        this.buffer = IOUtils.loadFile(path);

        this.font = STBTTFontinfo.malloc();
        STBTruetype.stbtt_InitFont(font, buffer);
    }

    public CharacterSet getFont(int pixelHeight) {
        if(!characterSets.containsKey(pixelHeight))
            characterSets.put(pixelHeight, new CharacterSet(this, pixelHeight, WIDTH, HEIGHT, 1));
        return characterSets.get(pixelHeight);
    }

    public STBTTFontinfo getFontInfo() {
        return font;
    }

    public float getKernAdvance(float scale, int codepointLeft, int codepointRight) {
        return STBTruetype.stbtt_GetCodepointKernAdvance(font, codepointLeft, codepointRight);
    }

    @Override
    public void dispose() {
        font.free();
        characterSets.values().forEach(CharacterSet::dispose);
    }
}
