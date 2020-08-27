package dev.penguinz.Sylk.util;

import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class IOUtils {

    public static ByteBuffer loadFile(String path) {
        ByteBuffer buffer;

        try(InputStream stream = IOUtils.class.getClassLoader().getResourceAsStream(path)) {
            if(stream == null)
                throw new RuntimeException("Asset does not exist: "+path);
                buffer = MemoryUtil.memAlloc(stream.available());
                while(stream.available() > 1)
                    buffer.put((byte) stream.read());
                buffer.flip();
                return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Could not load file: "+path);
        }
    }

}
