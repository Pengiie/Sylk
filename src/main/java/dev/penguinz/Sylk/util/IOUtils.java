package dev.penguinz.Sylk.util;

import java.io.*;
import java.nio.ByteBuffer;

public class IOUtils {

    public static ByteBuffer loadFile(String path) {
        ByteBuffer buffer;

        try(InputStream stream = IOUtils.class.getClassLoader().getResourceAsStream(path)) {
            if(stream == null)
                throw new RuntimeException("Asset does not exist: "+path);
                buffer = ByteBuffer.allocate(stream.available());
                while(stream.available() > 1)
                    buffer.put((byte) stream.read());
                buffer.flip();
                return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Could not load asset: "+path);
        }
    }

}
