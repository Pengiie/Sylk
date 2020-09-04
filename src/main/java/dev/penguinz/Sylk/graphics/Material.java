package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.graphics.texture.SubTextureData;
import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Vector2;

public class Material {

    public Color color;
    private RefContainer<Texture> texture;
    public SubTextureData subTextureData;

    public Material() {
        this(new Color(Color.white), null, null);
    }

    public Material(Color color) {
        this(color, null, null);
    }

    public Material(RefContainer<Texture> texture) {
        this(new Color(Color.white), texture, new SubTextureData(new Vector2(), new Vector2()));
    }

    public Material(RefContainer<Texture> texture, SubTextureData subTexture) {
        this(new Color(Color.white), texture, subTexture);
    }

    public Material(Color color, RefContainer<Texture> texture, SubTextureData subTextureData) {
        this.color = new Color(color);
        this.texture = texture;
        this.subTextureData = subTextureData;
    }

    public Texture getTexture() {
        return texture.value;
    }

    public boolean hasTexture() {
        return texture != null && texture.value != null;
    }

    public void setTexture(RefContainer<Texture> texture) {
        this.texture = texture;
    }
}
