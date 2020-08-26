package dev.penguinz.Sylk.ui.font;

import dev.penguinz.Sylk.assets.Texture;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.VBO;
import dev.penguinz.Sylk.graphics.VBOType;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.RefContainer;

import java.util.Arrays;

public class Text {

    public final Color color;
    private final int pixelHeight;
    private final String text;
    public final RefContainer<Font> font;

    private VAO generatedVAO;

    public Text(String text, int pixelHeight, Color color, RefContainer<Font> font) {
        this.text = text;
        this.pixelHeight = pixelHeight;
        this.color = color;
        this.font = font;

        if(font == null)
            throw new RuntimeException("Text font reference must be initialized");

        calculateVao();
    }

    private void calculateVao() {
        if(this.font.value == null)
            return;
        Font font = this.font.value;
        float[] positions = new float[text.length()*12];
        float[] textureCoords = new float[text.length()*12];

        int posIndex = 0;

        float xPos = 0;

        for (int i = 0; i < text.length(); i++) {
            int codepoint = text.charAt(i);

            Character charData = font.getCharacterData()[codepoint-Font.START_CHAR];

            float width = (charData.position.z - charData.position.x) * font.getCharacterScale() * pixelHeight;
            float height = (charData.position.w - charData.position.y) * font.getCharacterScale() * pixelHeight;
            float descent = charData.descent * font.getCharacterScale() * pixelHeight;

            // 0, 1
            positions[posIndex] = 0 + xPos;
            textureCoords[posIndex++] = charData.texturePosition.x;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) - height + descent;
            textureCoords[posIndex++] = charData.texturePosition.y;

            // 1, 0
            positions[posIndex] = width + xPos;
            textureCoords[posIndex++] = charData.texturePosition.z;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) + descent;
            textureCoords[posIndex++] = charData.texturePosition.w;

            // 0, 0
            positions[posIndex] = 0 + xPos;
            textureCoords[posIndex++] = charData.texturePosition.x;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) + descent;
            textureCoords[posIndex++] = charData.texturePosition.w;

            // 0, 1
            positions[posIndex] = 0 + xPos;
            textureCoords[posIndex++] = charData.texturePosition.x;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) - height + descent;
            textureCoords[posIndex++] = charData.texturePosition.y;

            // 1, 0
            positions[posIndex] = width + xPos;
            textureCoords[posIndex++] = charData.texturePosition.z;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) + descent;
            textureCoords[posIndex++] = charData.texturePosition.w;

            // 1, 1
            positions[posIndex] = width + xPos;
            textureCoords[posIndex++] = charData.texturePosition.z;
            positions[posIndex] = font.getLineHeight(font.getFontScale(pixelHeight)) - height + descent;
            textureCoords[posIndex++] = charData.texturePosition.y;


            xPos += charData.advance * font.getCharacterScale() * pixelHeight;
            System.out.println((char) codepoint+" "+codepoint+" "+charData.codepoint);
        }
        this.generatedVAO = new VAO(new VBO(positions, 2, VBOType.VERTICES), new VBO(textureCoords, 2, VBOType.TEXTURE_COORDS));
    }

    public VAO getVAO() {
        if(this.generatedVAO == null)
            calculateVao();
        return this.generatedVAO == null ? new VAO() : this.generatedVAO;
    }

    public String getText() {
        return text;
    }
}
