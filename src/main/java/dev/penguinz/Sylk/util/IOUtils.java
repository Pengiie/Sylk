package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.logging.Logger;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class IOUtils {

    public static ByteBuffer loadFile(String path) {
        ByteBuffer buffer;

        long time = System.currentTimeMillis();
        try(InputStream stream = IOUtils.class.getClassLoader().getResourceAsStream(path)) {
            if(stream == null)
                throw new RuntimeException("Asset does not exist: "+path);
            byte[] buf = new byte[stream.available()];
            stream.read(buf);

            buffer = MemoryUtil.memAlloc(buf.length);
            buffer.put(buf);
            buffer.flip();

            Logger.getLogger().logInfo("Took "+(System.currentTimeMillis() - time)+"ms to load "+path);
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Could not load file: "+path);
        }
    }

}
