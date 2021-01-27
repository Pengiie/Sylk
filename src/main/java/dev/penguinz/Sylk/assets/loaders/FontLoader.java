package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.assets.options.FontOptions;
import dev.penguinz.Sylk.ui.font.Font;

public class FontLoader implements AssetLoader<Font, FontOptions> {

    private final Font font = new Font();

    @Override
    public void loadAsync(String path, FontOptions options) {
        if(options == null)
            options = new FontOptions();
        this.font.loadAsync(path);
    }

    @Override
    public Font loadSync() {
        return font;
    }

    @Override
    public AssetLoader<Font, FontOptions> copy() {
        return new FontLoader();
    }
}
