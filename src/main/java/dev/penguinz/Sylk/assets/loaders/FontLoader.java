package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.ui.font.Font;
import dev.penguinz.Sylk.assets.options.FontOptions;

public class FontLoader implements AssetLoader<Font, FontOptions> {

    private final Font font = new Font();

    @Override
    public void loadAsync(String path, FontOptions options) {
        if(options == null)
            options = new FontOptions();
        this.font.loadAsync(path, options.charRange, options.pixelHeight, options.resolution, options.resolution);
    }

    @Override
    public Font loadSync() {
        this.font.loadSync();
        return font;
    }

    @Override
    public AssetLoader<Font, FontOptions> copy() {
        return new FontLoader();
    }
}
