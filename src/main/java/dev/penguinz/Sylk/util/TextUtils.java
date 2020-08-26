package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.ui.font.Character;
import dev.penguinz.Sylk.ui.font.Font;
import sun.awt.windows.WPrinterJob;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {

    public static List<String> splitTexts(String text, float pixelHeight, float width, Font font) {
        List<String> texts = new ArrayList<>();
        String[] splitText = text.split(" ");
        StringBuilder workingText = new StringBuilder();
        for (String s : splitText) {
            if(getTextWidth(workingText + s, pixelHeight, font) > width) {
                texts.add(workingText.toString());
                workingText = new StringBuilder();
            }
            workingText.append(s).append(" ");
        }
        String lastText = workingText.toString().trim();
        if(lastText.length() > 0)
            texts.add(lastText);
        return texts;
    }

    public static float getTextWidth(String text, float pixelHeight, Font font) {
        float width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += font.getCharacterData()[text.charAt(i)-Font.START_CHAR].advance * font.getCharacterScale() * pixelHeight;
        }
        return width;
    }

}
