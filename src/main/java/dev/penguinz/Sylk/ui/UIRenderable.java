package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.graphics.shader.Shader;
import org.joml.Vector4f;

public interface UIRenderable {

    void render(Shader shader);

    boolean getOverflow();

    Vector4f getBounds();

}
