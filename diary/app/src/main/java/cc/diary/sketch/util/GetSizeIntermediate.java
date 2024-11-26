package cc.diary.sketch.util;

import cc.diary.sketch.util.field.Fieldable;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

/**
 * Intermediate for BoundedText.getSize to pass between lambda scope and
 * function scope.
 */
public class GetSizeIntermediate extends Fieldable {
    private @Getter @Setter float textSize;
    private @Getter @Setter float width;

    public PVector toVector() {
        // x = width; y = textSize
        return new PVector(width, textSize);
    }
}