package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.assets.Texture;
import dev.penguinz.Sylk.assets.options.TextureOptions;

public class TextureLoader implements AssetLoader<Texture, TextureOptions> {

    private final Texture texture = new Texture();

    @Override
    public void loadAsync(String path, TextureOptions options) {
        if(options == null)
            options = new TextureOptions();
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
