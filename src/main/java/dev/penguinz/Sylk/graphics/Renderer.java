package dev.penguinz.Sylk.graphics;

import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.maths.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * A renderer for rendering objects onto the screen.
 */
public abstract class Renderer implements Disposable {

    protected final Shader shader;
    protected List<Item> renderItems = new ArrayList<>();
    protected VAO currentVao;

    protected Camera currentCamera;

    public Renderer(Shader shader) {
        this.shader = shader;
    }

    /**
     * Prepares the renderer for rendering.
     * @param camera the camera used for rendering.
     */
    public void begin(Camera camera) {
        shader.use();
        this.currentCamera = camera;
    }

    /**
     * Renders a given model, transform, and material. Must be called between {@link #begin(Camera)} and {@link #finish()}.
     * @param vao the model to render.
     * @param transform the transform to use.
     * @param material the material to use.
     */
    public void render(VAO vao, Transform transform, Material material) {
        if(currentVao != vao) {
            if(currentVao != null)
                flush();
            currentVao = vao;
        }
        renderItems.add(new Item(transform, material));
    }
    
    protected abstract void flush();

    /**
     * Finishes rendering.
     */
    public void finish() {
        if(!renderItems.isEmpty())
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
