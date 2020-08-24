package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.ui.UIBlock;
import dev.penguinz.Sylk.ui.UIRenderer;
import dev.penguinz.Sylk.ui.constraints.PixelConstraint;
import dev.penguinz.Sylk.ui.constraints.RelativeConstraint;
import dev.penguinz.Sylk.ui.constraints.UIConstraints;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;

public class UISandbox implements Layer {

    private UIRenderer uiRenderer;

    @Override
    public void init() {
        this.uiRenderer = new UIRenderer();

        this.uiRenderer.getScreenComponent().addComponent(
                new UIBlock(new Color(1, 0, 0, 1)),
                new UIConstraints().
                setXConstraint(new PixelConstraint(50)).
                setYConstraint(new PixelConstraint(50)).
                setWidthConstraint(new RelativeConstraint(0.1f)).
                setHeightConstraint(new RelativeConstraint(0.1f))
        );
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        this.uiRenderer.renderScreen();
    }

    @Override
    public void onEvent(Event event) {
        this.uiRenderer.onEvent(event);
    }

    @Override
    public void dispose() {

    }

    public static void main(String[] args) {
        new ApplicationBuilder().
                setTitle("UI Sandbox").
                setResizable(true).
                withLayers(new UISandbox()).
                buildAndRun();
    }
}
