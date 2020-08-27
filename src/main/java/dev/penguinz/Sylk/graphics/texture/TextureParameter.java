package dev.penguinz.Sylk.graphics.texture;

import org.lwjgl.opengl.GL11;

public enum TextureParameter {

    NEAREST(GL11.GL_NEAREST), LINEAR(GL11.GL_LINEAR);

    TextureParameter(int glType) {
        this.glType = glType;
    }

    public final int glType;

}
