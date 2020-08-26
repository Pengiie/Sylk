package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.animation.Animation;
import dev.penguinz.Sylk.animation.Animator;
import dev.penguinz.Sylk.assets.AssetManager;
import dev.penguinz.Sylk.assets.Texture;
import dev.penguinz.Sylk.assets.options.TextureOptions;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.input.Key;
import dev.penguinz.Sylk.ui.UIBlock;
import dev.penguinz.Sylk.ui.UIButton;
import dev.penguinz.Sylk.ui.UIContainer;
import dev.penguinz.Sylk.ui.UIText;
import dev.penguinz.Sylk.ui.constraints.*;
import dev.penguinz.Sylk.ui.font.Font;
import dev.penguinz.Sylk.ui.font.Text;
import dev.penguinz.Sylk.ui.font.TextAlignment;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.RefContainer;
import org.joml.Matrix4f;

public class UISandbox implements Layer {

    private UIContainer uiContainer;

    private final Animator animator = new Animator();

    private Animation increasePixels;

    private RefContainer<Font> font = new RefContainer<>(null);

    UIButton component = new UIButton(new Color(1, 0, 1), new Color(0, 1, 0),
            new UIText("Test,", Color.white, font, 32),
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

        Application.getInstance().getAssets().loadAsset("arial.ttf");
        Application.getInstance().getAssets().finishLoading();

        this.font.value = Application.getInstance().getAssets().getAsset("arial.ttf");

        /*this.uiContainer.addComponent(
                new UIText("Hello, World!", Color.white, Application.getInstance().getAssets().getAsset("arial.ttf")),
                new UIConstraints().
                setXConstraint(new PixelConstraint(50)).
                setYConstraint(new PixelConstraint(50)).
                setWidthConstraint(new RelativeConstraint(0.25f)).
                setHeightConstraint(new PixelConstraint(128)));*/
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
