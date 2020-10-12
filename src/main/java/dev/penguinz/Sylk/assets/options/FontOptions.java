package dev.penguinz.Sylk.assets.options;

import dev.penguinz.Sylk.ui.font.Font;

public class FontOptions extends AssetOptions<Font> {

    public int resolution = 2048;
    public int pixelHeight = 64;
    public int charRange = 96;
    public int overSampling = 1;

    /**
     * Sets the resolution for the generated bitmap file made from the font. The higher, the more characters that would fit.
     * @param resolution the resolution of the bitmap file.
     * @return a reference to this object.
     */
    public FontOptions setResolution(int resolution) {
        this.resolution = resolution;
        return this;
    }

    /**
     * Sets the pixel height for the characters of the font to generate.
     * @param pixelHeight the pixel height for the characters.
     * @return a reference to this object.
     */
    public FontOptions setPixelHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
        return this;
    }

    /**
     * Sets the range of chars to generate based off UTF-8 character range, starting from 32.
     * The more characters generated the larger the bitmap size needs to be.
     * @param charRange the range of characters to use.
     * @return a reference to this object.
     */
    public FontOptions setCharRange(int charRange) {
        this.charRange = charRange;
        return this;
    }

    /**
     * Sets the oversampling rate for the generated bitmap file. The more oversampling the smoother.
     * @param overSampling the oversampling rate.
     * @return a reference to this object.
     */
    public FontOptions setOverSampling(int overSampling) {
        this.overSampling = overSampling;
        return this;
    }
}
