package dev.penguinz.Sylk.assets;

import dev.penguinz.Sylk.assets.loaders.AssetLoader;
import dev.penguinz.Sylk.assets.options.AssetOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LoadingTask<T> {

    private final AssetDescriptor<T> descriptor;
    private final ExecutorService executor;
    private final AssetLoader<T, AssetOptions<T>> loader;
    private Object asset;

    private volatile boolean loadedAsync = false;
    private volatile boolean loadedSuccessfully = true;
    private volatile Future<Void> future;

    public LoadingTask(AssetDescriptor<T> assetDescriptor, AssetLoader<T, AssetOptions<T>> loader, ExecutorService executor) {
        this.descriptor = assetDescriptor;
        this.loader = loader.copy();
        this.executor = executor;
    }

    public boolean update() {
        if(!loadedAsync && future == null) {
            this.future = executor.submit(() -> {
                try {
                    loader.loadAsync(descriptor.path, descriptor.options);
                } catch (Exception e) {
                    loadedSuccessfully = false;
                }
                loadedAsync = true;
                return null;
            });
        } else {
            if(future.isDone()) {
                if(!loadedSuccessfully)
                    throw new RuntimeException("Couldn't load asset: "+ getDescriptor().path);
                this.asset = loader.loadSync();
                return true;
            }
        }
        return false;
    }

    public Object getAsset() {
        return asset;
    }

    public AssetDescriptor<T> getDescriptor() {
        return descriptor;
    }
}
