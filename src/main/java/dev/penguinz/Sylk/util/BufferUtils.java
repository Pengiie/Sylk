package dev.penguinz.Sylk.util;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class BufferUtils {

    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
