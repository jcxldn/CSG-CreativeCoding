package cc.diary.sketch.elements;

import java.io.Serializable;

import cc.diary.sketch.collision.RectangularBoundingBox;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Text (with an optional BoundingBox)
 */
@SuperBuilder
public class Text extends Element {
    // Set via superBuilder
    private Serializable text;
    private PVector coords;
    private float textSize;

    // Updated after each draw call
    private @Getter RectangularBoundingBox bounds;

    private String getMessage() {
        return text.toString();
    }

    // Make own function so size can be updated every time it's called
    // eg. if we change text size after setup runs
    private PVector getSize() {
        // Bit of hack to get around local var used in lambda
        PVector size[] = new PVector[1];
        withTextSize(textSize, () -> {
            size[0] = new PVector(getRoot().textWidth(getMessage()), textSize);
        });
        return size[0];
    }

    private void updateBounds() {
        PVector size = getSize();
        bounds = new RectangularBoundingBox(coords, size);

    }

    // executeSetup not required as there is a default impl in Element.
    public void executeSetup() {
        updateBounds(); // make bounds available before first draw
    }

    public void executeDraw() {

        // Update bounds
        updateBounds();

        // Testing purposes, draw bounding box
        bounds.draw(this);

        // Draw text
        withTextSize(textSize, () -> {
            getRoot().textAlign(PConstants.LEFT, PConstants.TOP);
            getRoot().text(getMessage(), coords.x, coords.y, coords.z);
        });
    }

}
