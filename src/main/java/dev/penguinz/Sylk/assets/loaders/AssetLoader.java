package dev.penguinz.Sylk.assets.loaders;

import dev.penguinz.Sylk.assets.options.AssetOptions;
import jdk.internal.jline.internal.Nullable;

public interface AssetLoader<T, P extends AssetOptions<T>> {

    void loadAsync(String path, @Nullable P options);

    T loadSync();

    AssetLoader<T, P> copy();

}
