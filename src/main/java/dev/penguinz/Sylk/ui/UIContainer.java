package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.input.MouseClickEvent;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.ui.constraints.AbsoluteConstraint;
import dev.penguinz.Sylk.ui.constraints.UIConstraints;
import dev.penguinz.Sylk.ui.font.FontShader;
import dev.penguinz.Sylk.ui.font.UIFontRenderable;
import dev.penguinz.Sylk.util.Disposable;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UIContainer extends UIComponent implements Disposable {

    private Matrix4f orthoProjection;

    private final Shader shader;
    private final Shader fontShader;

    public UIContainer() {
        this.shader = UIShader.create();
        this.fontShader = FontShader.create();

        updateProjection();
        updateScreenConstraints();
    }

    public void render() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        fontShader.use();
        fontShader.loadUniform(UniformConstants.projectionMatrix, orthoProjection);

        shader.use();
        shader.loadUniform(UniformConstants.projectionMatrix, orthoProjection);

        VAO.quad.bind();
        VAO.quad.enableVertexAttribArrays();

        List<UIRenderable> uiRenderables = new ArrayList<>();

        addRenderableChildren(this, uiRenderables);

        for (UIRenderable uiRenderable: uiRenderables) {
            if(uiRenderable instanceof UIFontRenderable) {
                fontShader.use();
                uiRenderable.render(this.fontShader);
                shader.use();
                VAO.quad.bind();
                VAO.quad.enableVertexAttribArrays();
            } else
                uiRenderable.render(this.shader);

        }

        VAO.quad.disableVertexAttribArrays();
        VAO.quad.unbind();
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void addRenderableChildren(UIComponent component, List<UIRenderable> uiRenderables) {
        if(component instanceof UIRenderable)
            uiRenderables.add((UIRenderable) component);
        component.getChildren().forEach(child -> addRenderableChildren(child, uiRenderables));
    }

    private final Set<UIComponent> hovering = new HashSet<>();

    public void update() {
        this.forceUpdateConstraints();

        List<UIComponent> uiEventListeners = new ArrayList<>();

        addEventListenerChildren(this, uiEventListeners);

        float mouseX = Application.getInstance().getInput().getMousePosX();
        float mouseY = Application.getInstance().getInput().getMousePosY();
        for (UIComponent uiEventListener : uiEventListeners) {
            Vector2 pointA = new Vector2(
                    uiEventListener.getConstraints().getXConstraintValue(),
                    uiEventListener.getConstraints().getYConstraintValue());
            Vector2 pointB = new Vector2(
                    pointA.x + uiEventListener.getConstraints().getWidthConstraintValue(),
                    pointA.y + uiEventListener.getConstraints().getHeightConstraintValue());

            boolean hovering = pointA.x <= mouseX && pointB.x >= mouseX && pointA.y <= mouseY && pointB.y >= mouseY;

            if(!hovering && this.hovering.contains(uiEventListener)) {
                ((UIEventListener) uiEventListener).onMouseExit();
                this.hovering.remove(uiEventListener);
            }
            if(hovering && !this.hovering.contains(uiEventListener)) {
                ((UIEventListener) uiEventListener).onMouseEnter();
                this.hovering.add(uiEventListener);
            }
            if(hovering)
                ((UIEventListener) uiEventListener).onMouseHover(mouseX, mouseY);

        }
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            updateProjection();
            updateScreenConstraints();
        }

        for (UIComponent uiEventListener : hovering) {
            if(event instanceof MouseClickEvent && !event.isHandled()) {
                ((UIEventListener) uiEventListener).onMouseClicked(((MouseClickEvent) event).button);
                event.handle();
            }
        }
    }

    private void addEventListenerChildren(UIComponent component, List<UIComponent> uiEventListeners) {
        if(component instanceof UIEventListener)
            uiEventListeners.add(component);
        component.getChildren().forEach(child -> addEventListenerChildren(child, uiEventListeners));
    }

    private void updateProjection() {
        this.orthoProjection =
                new Matrix4f().
                ortho2D(0, Application.getInstance().getWindowWidth(), Application.getInstance().getWindowHeight(), 0);
    }

    private void updateScreenConstraints() {
        this.setConstraints(
                new UIConstraints().
                        setXConstraint(new AbsoluteConstraint(0)).
                        setYConstraint(new AbsoluteConstraint(0)).
                        setWidthConstraint(new AbsoluteConstraint(Application.getInstance().getWindowWidth())).
                        setHeightConstraint(new AbsoluteConstraint(Application.getInstance().getWindowHeight())));
    }

    @Override
    public void dispose() {
        this.shader.dispose();
    }
}
