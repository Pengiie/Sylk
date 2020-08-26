package dev.penguinz.Sylk.assets.options;

import java.util.function.Consumer;

public class AssetOptions<T> {

    public Consumer<T> assetLoadedCallback;

    public AssetOptions<T> setAssetLoadedCallback(Consumer<T> assetLoadedCallback) {
        this.assetLoadedCallback = assetLoadedCallback;
        return this;
    }

    public void callAssetLoadedCallback(Object assetObj) {
        if(this.assetLoadedCallback != null) {
            @SuppressWarnings("unchecked")
            T asset = (T) assetObj;
            this.assetLoadedCallback.accept(asset);
        }
    }

}
