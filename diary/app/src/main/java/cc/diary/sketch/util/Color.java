package cc.diary.sketch.util;

import lombok.Getter;

public class Color {

    private static final float DEFAULT_ALPHA = 255;

    private @Getter ColorMode mode;
    private @Getter float val1, val2, val3, val4;

    public Color(float r, float g, float b, float a) {
        mode = ColorMode.RGB;
        val1 = r;
        val2 = g;
        val3 = b;
        val4 = a;
    }

    public Color(float r, float g, float b) {
        this(r, g, b, DEFAULT_ALPHA);
    }
}
