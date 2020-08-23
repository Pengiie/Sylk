package dev.penguinz.Sylk.assets;

import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.IOUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture implements Disposable {

    private final int id;
    private int width, height;
    private int channels;

    private ByteBuffer data;

    public Texture(String path) {
        this.id = GL11.glGenTextures();
        bind();

        loadAsync(path);
        loadSync();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    private void loadAsync(String path) {
       // this.data = IOUtils.loadFile(path);
       // System.out.println(this.data.capacity());

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            STBImage.nstbi_set_flip_vertically_on_load(1);
            this.data = STBImage.stbi_load(path, width, height, channels, 3);

            this.width = width.get(0);
            this.height = height.get(0);
            this.channels = channels.get(0);
        }
    }

    private void loadSync() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, mapFormat(), width, height, 0, mapFormat(), GL_UNSIGNED_BYTE, data);

        STBImage.stbi_image_free(data);
    }

    private int mapFormat() {
        switch (channels) {
            case 3: return GL_RGB;
            case 4: return GL_RGBA;
            default: throw new RuntimeException("Trying to load unsupported image format");
        }
    }

    @Override
    public void dispose() {
        GL11.glDeleteTextures(id);
    }
}
