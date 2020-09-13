package dev.penguinz.Sylk.assets.options;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.graphics.texture.TextureParameter;

public class TextureOptions extends AssetOptions<Texture> {

    public TextureParameter minFilter = TextureParameter.LINEAR_NEAREST, magFilter = TextureParameter.LINEAR_NEAREST;

}
