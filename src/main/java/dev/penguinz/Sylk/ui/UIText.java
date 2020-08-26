package dev.penguinz.Sylk.ui;

import dev.penguinz.Sylk.animation.values.AnimatableValue;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.shader.Shader;
import dev.penguinz.Sylk.graphics.shader.uniforms.UniformConstants;
import dev.penguinz.Sylk.ui.constraints.PixelConstraint;
import dev.penguinz.Sylk.ui.font.*;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.TextUtils;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UIText extends UIComponent implements UIFontRenderable {

    public final HashMap<Text, Float> texts = new HashMap<>();

    private String text;
    private Color color;
    private final RefContainer<Font> font;
    private int pixelHeight;
    private boolean wrapText;
    public TextAlignment horizontalAlignment, verticalAlignment;

    private boolean loadedTexts = false;

    public UIText(String text, Font font, int pixelHeight) {
        this(text, Color.black, new RefContainer<>(font), pixelHeight, true, TextAlignment.CENTER, TextAlignment.CENTER);
    }

    public UIText(String text, Color color, Font font, int pixelHeight) {
        this(text, color, new RefContainer<>(font), pixelHeight, true, TextAlignment.CENTER, TextAlignment.CENTER);
    }

    public UIText(String text, Color color, RefContainer<Font> font, int pixelHeight) {
        this(text, color, font, pixelHeight, true, TextAlignment.CENTER, TextAlignment.CENTER);
    }

    public UIText(String text, RefContainer<Font> font, int pixelHeight) {
        this(text, Color.black, font, pixelHeight, true, TextAlignment.CENTER, TextAlignment.CENTER);
    }

    public UIText(String text, Color color, RefContainer<Font> font, int pixelHeight, boolean wrapText, TextAlignment horizontalAlignment, TextAlignment verticalAlignment) {
        this.text = text;
        this.color = color;
        this.font = font;
        this.pixelHeight = pixelHeight;
        this.wrapText = wrapText;
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    private void updateTexts() {
        if(font.value == null)
            return;

        texts.clear();
        for (String text : TextUtils.splitTexts(text, pixelHeight, getConstraints().getWidthConstraintValue(), font.value)) {
            texts.put(new Text(text, pixelHeight, color, font), TextUtils.getTextWidth(text, pixelHeight, font.value));
            if(!wrapText)
                break;
        }
        loadedTexts = true;
    }

    @Override
    public void render(Shader shader) {
        if(font.value == null)
            return;

        if(!loadedTexts)
            updateTexts();

        if(texts.isEmpty())
            return;

        shader.loadUniform(UniformConstants.color, color.toVector());
        shader.loadUniform(UniformConstants.texture0, font.value.getTexture());

        float xPos = this.getConstraints().getXConstraintValue();
        float width = this.getConstraints().getWidthConstraintValue();

        float totalHeight = texts.size() * font.value.getNewLineSpace(font.value.getFontScale(pixelHeight)) + font.value.getLineGap(font.value.getFontScale(pixelHeight)) - font.value.getDescent(font.value.getFontScale(pixelHeight));
        float height = this.getConstraints().getHeightConstraintValue();
        float yPos = this.getConstraints().getYConstraintValue();
        System.out.println(yPos);
        if(verticalAlignment == TextAlignment.CENTER)
            yPos = yPos + height/2 - totalHeight/2;
        if(verticalAlignment == TextAlignment.BOTTOM)
            yPos = yPos + height - totalHeight;

        int i = 0;
        for (Text text: texts.keySet()) {
            Matrix4f translation = new Matrix4f();

            if(horizontalAlignment == TextAlignment.LEFT)
                translation.m30(xPos);
            if(horizontalAlignment == TextAlignment.CENTER)
                translation.m30(xPos + width/2 - texts.get(text)/2);
            if(horizontalAlignment == TextAlignment.RIGHT)
                translation.m30(xPos + width - texts.get(text));

            translation.m31(yPos + i * font.value.getNewLineSpace(font.value.getFontScale(pixelHeight)));

            shader.loadUniform(FontShader.position, translation);

            VAO vao = text.getVAO();
            vao.bind();
            vao.enableVertexAttribArrays();

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());

            i++;
        }
    }
}
