package cc.diary.sketch.util;

import lombok.Getter;

// From Processing PConstants
public enum ColorMode {
    RGB(1), ARGB(2), HSB(3);

    @Getter
    private final int value;

    private ColorMode(int value) {
        this.value = value;
    }
}
