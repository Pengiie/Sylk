package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.util.Disposable;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VAO implements Disposable {

    public static final VAO quad = new VAO(new VBO(new float[] {
            0, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 0,
            1, 1
    }, 2, VBOType.VERTICES), new VBO(new float[] {
            0, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 0,
            1, 1
    }, 2, VBOType.TEXTURE_COORDS));

    public static final VAO triangle = new VAO(new VBO(new float[] {
            0, 0,
            1, 0,
            0.5f, 1f,
    }, 2, VBOType.VERTICES), new VBO(new float[] {
            0, 0,
            1, 0,
            0.5f, 1f,
    }, 2, VBOType.TEXTURE_COORDS));

    private final int id;
    private int vertexCount;

    private final VBO[] vbos;

    public VAO(VBO... vbos) {
        this.id = GL30.glGenVertexArrays();
        this.vbos = vbos;
        bind();

        for (VBO vbo : vbos) {
            vbo.create();
            if(vbo.getType() == VBOType.VERTICES) {
                this.vertexCount = vbo.getDataLength()/vbo.getDataSize();
            }
        }
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public VBO[] getVbos() {
        return vbos;
    }

    public void enableVertexAttribArrays() {
        for (VBO vbo : vbos) {
            GL20.glEnableVertexAttribArray(vbo.getType().getIndex());
        }
    }

    public void disableVertexAttribArrays() {
        for (VBO vbo : vbos) {
            GL20.glDisableVertexAttribArray(vbo.getType().getIndex());
        }
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public void dispose() {
        GL30.glDeleteVertexArrays(id);

        for (VBO vbo : vbos) {
            vbo.dispose();
        }
    }
}
