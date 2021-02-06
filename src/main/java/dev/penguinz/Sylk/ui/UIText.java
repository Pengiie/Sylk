package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.ui.font.*;
import dev.penguinz.Sylk.util.Alignment;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.TextUtils;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class UIText extends UIComponent implements UIFontRenderable {

    private final List<Text> texts = new ArrayList<>();

    private String text;
    private Color color;
    private final RefContainer<Font> font;
    private TextHeight height;
    private boolean wrapText;
    public Alignment horizontalAlignment, verticalAlignment;
    public float lineThickness = 1.05f;

    private boolean loadedTexts = false;
    private float previousHeight;

    public UIText(String text, Font font, TextHeight height) {
        this(text, Color.black, new RefContainer<>(font), height, true, Alignment.CENTER, Alignment.CENTER);
    }

    public UIText(String text, Color color, Font font, TextHeight height) {
        this(text, color, new RefContainer<>(font), height, true, Alignment.CENTER, Alignment.CENTER);
    }

    public UIText(String text, Color color, RefContainer<Font> font, TextHeight height) {
        this(text, color, font, height, true, Alignment.CENTER, Alignment.CENTER);
    }

    public UIText(String text, RefContainer<Font> font, TextHeight height) {
        this(text, Color.black, font, height, true, Alignment.CENTER, Alignment.CENTER);
    }

    public UIText(String text, Color color, RefContainer<Font> font, TextHeight height, boolean wrapText, Alignment horizontalAlignment, Alignment verticalAlignment) {
        this.text = text;
        this.color = color;
        this.font = font;
        this.height = height;
        this.wrapText = wrapText;
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    public UIText setHorizontalAlignment(Alignment alignment) {
        this.horizontalAlignment = alignment;
        return this;
    }

    public UIText setVerticalAlignment(Alignment alignment) {
        this.verticalAlignment = alignment;
        return this;
    }

    private void updateTexts() {
        this.previousHeight = this.getConstraints().getHeightConstraintValue();
        if(font.value == null)
            return;

        int pixelHeight = height.getPixelHeight(this.getConstraints());

        texts.clear();
        if(pixelHeight <= this.getConstraints().getHeightConstraintValue())
            for (String text : TextUtils.splitTexts(text, pixelHeight, getConstraints().getWidthConstraintValue(), font.value)) {
                texts.add(new Text(text, pixelHeight, color, font));
                if(!wrapText)
                    break;
            }
        loadedTexts = true;
    }

    public void setText(String text) {
        this.text = text;
        loadedTexts = false;
    }

    @Override
    public void render(Shader shader) {
        if(font.value == null)
            return;

        if(!loadedTexts)
            updateTexts();

        if(texts.isEmpty())
            return;

        if(this.previousHeight != this.getConstraints().getHeightConstraintValue())
            updateTexts();

        int pixelHeight = height.getPixelHeight(this.getConstraints());

        shader.loadUniform(UniformConstants.color, color.toVector());
        shader.loadUniform(FontShader.lineThickness, lineThickness);

        float xPos = this.getConstraints().getXConstraintValue();
        float width = this.getConstraints().getWidthConstraintValue();

        float totalHeight = 0;
        for (Text text : texts)
            totalHeight += font.value.getFont(text.pixelHeight).getLineHeight();
        float height = this.getConstraints().getHeightConstraintValue();
        float yPos = this.getConstraints().getYConstraintValue();
        if(verticalAlignment == Alignment.CENTER)
            yPos = yPos + height/2 - totalHeight/2;
        if(verticalAlignment == Alignment.BOTTOM)
            yPos = yPos + height - totalHeight;
        int i = 0;
        for (Text text: texts) {
            Matrix4f translation = new Matrix4f();

            if(horizontalAlignment == Alignment.LEFT)
                translation.m30(xPos);
            if(horizontalAlignment == Alignment.CENTER)
                translation.m30(xPos + width/2 - text.getWidth()/2);
            if(horizontalAlignment == Alignment.RIGHT)
                translation.m30(xPos + width - text.getWidth());

            translation.m31(yPos + i * font.value.getFont(text.pixelHeight).getNewLineSpace());

            shader.loadUniform(FontShader.position, translation);
            shader.loadUniform(UniformConstants.texture0, font.value.getFont(text.pixelHeight).getTexture());

            VAO vao = text.getVAO();
            vao.bind();
            vao.enableVertexAttribArrays();

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());

            i++;
        }
    }

    @Override
    public boolean getOverflow() {
        return overflow;
    }

    @Override
    public Vector4f getBounds() {
        float x = getConstraints().getXConstraintValue();
        float width = getConstraints().getWidthConstraintValue();
        float y = Application.getInstance().getWindowHeight() - getConstraints().getYConstraintValue();
        float height = getConstraints().getHeightConstraintValue();
        return new Vector4f(x, y - height, x + width, y);
    }
}
