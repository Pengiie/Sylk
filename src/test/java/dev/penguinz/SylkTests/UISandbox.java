package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.animation.Animation;
import dev.penguinz.Sylk.animation.Animator;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.input.Key;
import dev.penguinz.Sylk.ui.UIBlock;
import dev.penguinz.Sylk.ui.UIContainer;
import dev.penguinz.Sylk.ui.constraints.*;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;

public class UISandbox implements Layer {

    private UIContainer uiContainer;

    private final Animator animator = new Animator();

    private Animation increasePixels;
    private Animation changeColor;

    UIBlock component = new UIBlock(new Color(1, 0, 0));

    @Override
    public void init() {
        this.uiContainer = new UIContainer();

        this.uiContainer.addComponent(
                component,
                new UIConstraints().
                setXConstraint(new CenterConstraint()).
                setYConstraint(new PixelConstraint(50)).
                setWidthConstraint(new RelativeConstraint(0.1f)).
                setHeightConstraint(new RatioConstraint(1))
        );
        this.increasePixels = new Animation(1).
                addValue(component.getConstraints().getYAnimatableConstraint(), 50, 100);

        this.changeColor = new Animation(1).
                addValue(component.color, new Color(1, 0, 0, 1), new Color(0, 1, 0, 0));
    }

    @Override
    public void update() {
        this.uiContainer.update();

        animator.update();
        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_T)) {
            animator.playAnimation(this.increasePixels);
            animator.playAnimation(this.changeColor);
        }
    }

    @Override
    public void render() {
        this.uiContainer.render();
    }

    @Override
    public void onEvent(Event event) {
        this.uiContainer.onEvent(event);
    }

    @Override
    public void dispose() {
        this.uiContainer.dispose();
    }

    public static void main(String[] args) {
        new ApplicationBuilder().
                setTitle("UI Sandbox").
                setResizable(true).
                withLayers(new UISandbox()).
                buildAndRun();
    }
}
