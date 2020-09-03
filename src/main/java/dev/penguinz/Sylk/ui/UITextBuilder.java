package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.ui.font.Font;
import dev.penguinz.Sylk.ui.font.RelativeTextHeight;
import dev.penguinz.Sylk.ui.font.TextHeight;
import dev.penguinz.Sylk.util.Alignment;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.RefContainer;

public class UITextBuilder {

    private final RefContainer<Font> font;
    private final String text;
    private Color color = Color.black;
    private TextHeight height = new RelativeTextHeight(0.5f);
    private boolean wrapText = true;
    private Alignment
            horizontalAlignment = Alignment.LEFT,
            verticalAlignment = Alignment.TOP;

    public UITextBuilder(String text, Font font) {
        this(text, new RefContainer<>(font));
    }

    public UITextBuilder(String text, RefContainer<Font> font) {
        this.text = text;
        this.font = font;
    }

    public UITextBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public UITextBuilder setHeight(TextHeight height) {
        this.height = height;
        return this;
    }

    public UITextBuilder setWrap(boolean wrap) {
        this.wrapText = wrap;
        return this;
    }

    public UITextBuilder setAlignment(Alignment horizontalAlignment, Alignment verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public UIText build() {
        return new UIText(text, color, font, height, wrapText, horizontalAlignment, verticalAlignment);
    }

}
