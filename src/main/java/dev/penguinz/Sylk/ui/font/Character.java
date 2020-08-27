package dev.penguinz.Sylk.ui.font;

import org.joml.Vector4f;

public class Character {

    public final Vector4f position;
    public final Vector4f texturePosition;

    public final float advance, descent;

    public final int codepoint;

    public Character(int codepoint, Vector4f position, Vector4f texturePosition, float advance, float descent) {
        this.codepoint = codepoint;
        this.position = position;
        this.texturePosition = texturePosition;
        this.advance = advance;
        this.descent = descent;
    }
}
