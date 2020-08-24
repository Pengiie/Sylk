package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.graphics.Renderer;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.ui.constraints.AbsoluteConstraint;
import dev.penguinz.Sylk.ui.constraints.UIConstraints;
import dev.penguinz.Sylk.util.Disposable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class UIRenderer implements Disposable {

    private Matrix4f orthoProjection;

    private final Shader shader;

    private final UIComponent screenComponent = new UIComponent();

    public UIRenderer() {
        this.shader = UIShader.create();

        updateProjection();
        updateScreenConstraints();
    }

    public void renderScreen() {
        shader.use();
        shader.loadUniform(UniformConstants.projectionMatrix, orthoProjection);

        VAO.quad.bind();
        VAO.quad.enableVertexAttribArrays();

        List<UIRenderable> uiRenderables = new ArrayList<>();

        addRenderableChildren(this.screenComponent, uiRenderables);

        for (UIRenderable uiRenderable : uiRenderables) {
            uiRenderable.render(this.shader);
        }

        VAO.quad.disableVertexAttribArrays();
        VAO.quad.unbind();
    }

    private void addRenderableChildren(UIComponent component, List<UIRenderable> uiRenderables) {
        if(component instanceof UIRenderable)
            uiRenderables.add((UIRenderable) component);
        component.getChildren().forEach(child -> addRenderableChildren(child, uiRenderables));
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            updateProjection();
            updateScreenConstraints();
        }
    }

    private void updateProjection() {
        this.orthoProjection =
                new Matrix4f().
                ortho2D(0, Application.getInstance().getWindowWidth(), Application.getInstance().getWindowHeight(), 0);
    }

    private void updateScreenConstraints() {
        this.screenComponent.setConstraints(
                new UIConstraints().
                        setXConstraint(new AbsoluteConstraint(0)).
                        setYConstraint(new AbsoluteConstraint(0)).
                        setWidthConstraint(new AbsoluteConstraint(Application.getInstance().getWindowWidth())).
                        setHeightConstraint(new AbsoluteConstraint(Application.getInstance().getWindowHeight())));
        this.screenComponent.updateConstraints();
    }

    public UIComponent getScreenComponent() {
        return screenComponent;
    }

    @Override
    public void dispose() {
        this.shader.dispose();
    }
}
