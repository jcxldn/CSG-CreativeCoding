package cc.diary.sketch.elements.core;

import java.util.ArrayList;
import java.util.function.Supplier;

import cc.diary.sketch.Diary;
import cc.diary.sketch.collision.BoundingBox;
import cc.diary.sketch.logging.InstanceLogger;
import cc.diary.sketch.util.Color;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PVector;

@SuperBuilder
public abstract class Element extends InstanceLogger {
    private @Getter Diary root;
    private @Builder.Default @Getter ElementPriority priority = ElementPriority.LOWEST;
    // default to always displaying
    private @Builder.Default @Getter Supplier<Boolean> visibleWhen = () -> true;

    private @Builder.Default ArrayList<Element> priorities = new ArrayList<>();

    // #region Utility functions

    public boolean mouseInBounds(BoundingBox bounds) {
        return bounds.contains(new PVector(getRoot().mouseX, getRoot().mouseY));
    }

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
