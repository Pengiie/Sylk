package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.animation.values.AnimatableValue;
import dev.penguinz.Sylk.ui.constraints.RelativeConstraint;
import dev.penguinz.Sylk.ui.constraints.UIConstraints;
import dev.penguinz.Sylk.util.Color;

public class UIButton extends UIBlock implements UIEventListener {

    private Color previousColor;
    public AnimatableValue<Color> hoverColor;

    public UIText text;

    public Runnable onClick;

    public UIButton(Color color, Color hoverColor, UIText text, Runnable onClick) {
        super(color);
        this.hoverColor = new AnimatableColor(hoverColor);
        this.onClick = onClick;
        this.text = text;

        this.addComponent(
                text,
                new UIConstraints().
                setXConstraint(new RelativeConstraint(0.1f)).
                setYConstraint(new RelativeConstraint(0.1f)).
                setWidthConstraint(new RelativeConstraint(0.8f)).
                setHeightConstraint(new RelativeConstraint(0.8f)));


    }

    public UIText getText() {
        return text;
    }

    @Override
    public void onMouseEnter() {
        this.previousColor = new Color(color.value);
        this.color = new AnimatableColor(new Color(hoverColor.value));
    }

    @Override
    public void onMouseExit() {
        this.color.value = new Color(this.previousColor);
    }

    @Override
    public void onMouseClicked(int button) {
        if(button == 0)
            onClick.run();
    }
}
