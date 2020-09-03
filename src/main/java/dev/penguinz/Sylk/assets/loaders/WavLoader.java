package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.assets.options.WavOptions;
import dev.penguinz.Sylk.audio.WavSound;

public class WavLoader implements AssetLoader<WavSound, WavOptions> {

    private final WavSound sound = new WavSound();

    @Override
    public void loadAsync(String path, WavOptions options) {
        this.sound.loadAsync(path);
    }

    @Override
    public WavSound loadSync() {
        return this.sound;
    }

    @Override
    public AssetLoader<WavSound, WavOptions> copy() {
        return new WavLoader();
    }
}
