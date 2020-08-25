package dev.penguinz.Sylk.assets;

import dev.penguinz.Sylk.assets.loaders.AssetLoader;
import dev.penguinz.Sylk.assets.options.AssetOptions;

public class AssetDescriptor<T> {

    public final String path;
    public final AssetOptions<T> options;
    public final Class<T> classType;

    public AssetDescriptor(String path, AssetOptions<T> options, Class<T> classType) {
        this.path = path;
        this.options = options;
        this.classType = classType;
    }
}
