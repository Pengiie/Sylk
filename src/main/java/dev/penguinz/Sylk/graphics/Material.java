package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.util.Color;

public class Material {

    public Color color;
    public Texture texture;
    public boolean glows;

    public Material() {
        this(new Color(Color.white), null);
    }

    public Material(Color color) {
        this(color, null);
    }

    public Material(Texture texture) {
        this(new Color(Color.white), texture);
    }

    public Material(Color color, Texture texture) {
        this.color = new Color(color);
        this.texture = texture;
    }

}
