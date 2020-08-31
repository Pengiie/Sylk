package dev.penguinz.Sylk.graphics.texture;

import org.lwjgl.opengl.GL11;

public enum TextureParameter {

    NEAREST(GL11.GL_NEAREST),
    LINEAR(GL11.GL_LINEAR),
    NEAREST_LINEAR(GL11.GL_NEAREST_MIPMAP_LINEAR),
    NEAREST_NEAREST(GL11.GL_NEAREST_MIPMAP_NEAREST),
    LINEAR_LINEAR(GL11.GL_LINEAR_MIPMAP_LINEAR),
    LINEAR_NEAREST(GL11.GL_LINEAR_MIPMAP_NEAREST);

    TextureParameter(int glType) {
        this.glType = glType;
    }

    public final int glType;

}
