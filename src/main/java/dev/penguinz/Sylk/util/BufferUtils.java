package dev.penguinz.Sylk.util;

import java.nio.FloatBuffer;

public class BufferUtils {

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
