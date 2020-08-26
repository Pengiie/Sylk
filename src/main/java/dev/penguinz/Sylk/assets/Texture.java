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

    private int id;
    private int width, height;
    private int channels;

    private ByteBuffer data;

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void loadAsync(String path) {
        this.data = IOUtils.loadFile(path);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            STBImage.nstbi_set_flip_vertically_on_load(1);
            this.data = STBImage.stbi_load_from_memory(this.data, width, height, channels, 3);

            this.width = width.get(0);
            this.height = height.get(0);
            this.channels = channels.get(0);
        }
    }

    public void loadSync() {
        this.id = GL11.glGenTextures();
        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL15.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, mapFormat(), width, height, 0, mapFormat(), GL_UNSIGNED_BYTE, data);

        STBImage.stbi_image_free(data);
    }

    private int mapFormat() {
        switch (channels) {
            case 1: return GL_RED;
            case 3: return GL_RGB;
            case 4: return GL_RGBA;
            default: throw new RuntimeException("Trying to load unsupported image format");
        }
    }

    public void setData(ByteBuffer data, int width, int height, int channels) {
        this.data = data;
        this.width = width;
        this.height = height;
        this.channels = channels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        GL11.glDeleteTextures(id);
    }
}
