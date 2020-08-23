package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class VBO implements Disposable {

    private int id;
    private final float[] data;
    private final int size;

    private final VBOType type;

    public VBO(float[] data, int size, VBOType type) {
        this.data = data;
        this.size = size;
        this.type = type;
    }

    public void create() {
        this.id = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(this.data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(type.getIndex(), size, GL11.GL_FLOAT, false, 0, 0);
    }

    public VBOType getType() {
        return type;
    }

    public int getDataLength() {
        return data.length;
    }

    public int getDataSize() {
        return size;
    }

    @Override
    public void dispose() {
        GL15.glDeleteBuffers(id);
    }

}
