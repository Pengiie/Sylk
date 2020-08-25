package dev.penguinz.Sylk.assets;

import dev.penguinz.Sylk.util.Disposable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssetManager implements Disposable {

    private final ExecutorService executorService;

    public AssetManager() {
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void dispose() {
        executorService.shutdown();
    }
}
