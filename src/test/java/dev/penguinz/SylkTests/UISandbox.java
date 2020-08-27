package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.animation.Animation;
import dev.penguinz.Sylk.animation.Animator;
import dev.penguinz.Sylk.assets.options.FontOptions;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.input.Key;
import dev.penguinz.Sylk.ui.UIButton;
import dev.penguinz.Sylk.ui.UIContainer;
import dev.penguinz.Sylk.ui.UIText;
import dev.penguinz.Sylk.ui.constraints.*;
import dev.penguinz.Sylk.ui.font.Font;
import dev.penguinz.Sylk.ui.font.RelativeTextHeight;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.RefContainer;

public class UISandbox implements Layer {

    private UIContainer uiContainer;

    private final Animator animator = new Animator();

    private Animation increasePixels;

    private final RefContainer<Font> font = new RefContainer<>(null);

    UIButton component = new UIButton(new Color(1, 0, 1), new Color(0, 1, 0),
            new UIText("The Quick Brown Fox Jumped Over The Lazy Dog.", Color.white, font, new RelativeTextHeight(0.3f)),
            () -> Application.getInstance().getLogger().logInfo("Button has been clicked"));

    @Override
    public void init() {
        this.uiContainer = new UIContainer();

        this.uiContainer.addComponent(
                component,
                new UIConstraints().
                setXConstraint(new CenterConstraint()).
                setYConstraint(new PixelConstraint(50)).
                setWidthConstraint(new RelativeConstraint(0.2f)).
                setHeightConstraint(new RatioConstraint(0.35f))
        );
        this.increasePixels = new Animation(1).
                addValue(component.getConstraints().getYAnimatableConstraint(), 50, 100);

        Application.getInstance().getAssets().loadAsset("times.ttf",
                new FontOptions().
                setAssetLoadedCallback(font -> this.font.value = font));
    }

    @Override
    public void update() {
        this.uiContainer.update();
        animator.update();

        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_T)) {
            animator.playAnimation(this.increasePixels);
        }
        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_Y)) {
            animator.playAnimation(this.increasePixels, true);
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
