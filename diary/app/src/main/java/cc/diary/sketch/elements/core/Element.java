package cc.diary.sketch.elements.core;

import cc.diary.sketch.Diary;
import cc.diary.sketch.util.Color;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class Element {
    private @Getter Diary root;
    private @Builder.Default @Getter int priority = Integer.MAX_VALUE - 2;

    // #region Utility functions

    public void withStroke(Color color, Runnable action) {
        // Make a record of the current colorMode and stroke
        int previousColorMode = root.g.colorMode;
        boolean previousStrokeEnabled = root.g.stroke;
        int previousStrokeColor = root.g.strokeColor;

        root.colorMode(color.getMode().getValue());
        root.stroke(color.getVal1(), color.getVal2(), color.getVal3(), color.getVal4());

        action.run();

        // Restore previous

        root.colorMode(previousColorMode);

        if (previousStrokeEnabled) {
            root.stroke(previousStrokeColor);
        } else {
            root.noStroke();
        }
    }

    public void withFill(boolean enabled, Color color, Runnable action) {
        // Make a record of the current fillColor and status
        int previousFillColor = root.g.fillColor;
        boolean previousFillEnabled = root.g.fill;

        // Set
        if (enabled) {
            root.fill(color.getVal1(), color.getVal2(), color.getVal3(), color.getVal4());
        } else {
            root.noFill();
        }

        action.run();

        // Restore previous
        if (previousFillEnabled) {
            root.fill(previousFillColor);
        } else {
            root.noFill();
        }
    }

    public void withTextSize(float size, Runnable action) {
        // Make a record of current textsize;
        float previousSize = root.g.textSize;

        root.textSize(size);

        action.run();

        root.textSize(previousSize);
    }

    // #endregion

}
