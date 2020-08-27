package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.graphics.texture.Texture;
import dev.penguinz.Sylk.assets.options.TextureOptions;

public class TextureLoader implements AssetLoader<Texture, TextureOptions> {

    private Texture texture;

    @Override
    public void loadAsync(String path, TextureOptions options) {
        if(options == null)
            options = new TextureOptions();
        this.texture = new Texture(options.minFilter, options.magFilter);
        this.texture.loadAsync(path);
    }

    @Override
    public Texture loadSync() {
        this.texture.loadSync();
        return texture;
    }

    @Override
    public AssetLoader<Texture, TextureOptions> copy() {
        return new TextureLoader();
    }
}
