package dev.penguinz.Sylk.assets.options;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.texture.TextureParameter;

public class TextureOptions extends AssetOptions<Texture> {

    public TextureParameter minFilter = TextureParameter.LINEAR_NEAREST, magFilter = TextureParameter.LINEAR_NEAREST;

    /**
     * Sets the texture filter using for interpolation when scaling down.
     * @param minFilter the filter used for minification interpolation.
     * @return a reference to this object.
     */
    public TextureOptions setMinFilter(TextureParameter minFilter) {
        this.minFilter = minFilter;
        return this;
    }

    /**
     * Sets the texture filter using for interpolation when scaling up.
     * @param magFilter the filter used for magnification interpolation.
     * @return a reference to this object.
     */
    public TextureOptions setMagFilter(TextureParameter magFilter) {
        this.magFilter = magFilter;
        return this;
    }
}
