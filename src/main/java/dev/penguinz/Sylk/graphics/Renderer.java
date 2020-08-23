package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.util.maths.Transform;

import java.util.ArrayList;
import java.util.List;

public abstract class Renderer implements Disposable {

    protected final Shader shader;
    protected List<Item> renderItems = new ArrayList<>();
    protected VAO currentVao;

    protected Camera currentCamera;

    public Renderer(Shader shader) {
        this.shader = shader;
    }

    public void begin(Camera camera) {
        shader.use();
        this.currentCamera = camera;
    }

    public void render(VAO vao, Transform transform, Material material) {
        if(currentVao != vao) {
            if(currentVao != null)
                flush();
            currentVao = vao;
        }
        renderItems.add(new Item(transform, material));
    }
    
    protected abstract void flush();

    public void finish() {
        flush();
    }

    @Override
    public void dispose() {
        this.shader.dispose();
    }

    protected static class Item {
        public final Transform transform;
        public final Material material;

        public Item(Transform transform, Material material) {
            this.transform = transform;
            this.material = material;
        }
    }

}
