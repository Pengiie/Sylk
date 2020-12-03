package dev.penguinz.Sylk.util;

import org.joml.Vector4f;

import java.util.Arrays;

/**
 * A four component red, green, blue, and alpha color.
 */
public class Color {

    public static final Color transparent = new Color(0, 0, 0, 0);
    public static final Color white = new Color(1, 1, 1, 1);
    public static final Color black = new Color(0, 0, 0, 1);

    public float r, g, b, a;

    /**
     * Creates a new instance from another color.
     * @param color the color to copy.
     */
    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    /**
     * Creates a new color with the specified rgb values and an alpha of 1.
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    /**
     * Creates a new color with the specified rgb values.
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     * @param a alpha component.
     */
    public Color(float r, float g, float b, float a) {
        this.r = r > 1 ? r / 255 : r;
        this.g = g > 1 ? g / 255 : g;
        this.b = b > 1 ? b / 255 : b;
        this.a = a > 1 ? a / 255 : a;
    }

    /**
     * Creates a new color with the specified hex value.
     * @param hexcode a hex code such as #7e57c2
     */
    public Color(String hexcode) {
        if((hexcode.length() > 7 || hexcode.length() < 6))
            throw new IllegalArgumentException("Was given invalid hex color of \""+hexcode+"\"");
        if(hexcode.length() == 6)
            hexcode = "#" + hexcode;
        String[] segments = new String[] {hexcode.substring(1, 3), hexcode.substring(3, 5), hexcode.substring(5, 7)};
        this.r = (float) Integer.valueOf(segments[0], 16) / 255;
        this.g = (float) Integer.valueOf(segments[1], 16) / 255;
        this.b = (float) Integer.valueOf(segments[2], 16) / 255;
        this.a = 1;
    }

    public boolean equals(Color other) {
        return this.r == other.r && this.g == other.g && this.b == other.b && this.a == other.a;
    }

    public Vector4f toVector() {
        return new Vector4f(r, g, b, a);
    }

}
