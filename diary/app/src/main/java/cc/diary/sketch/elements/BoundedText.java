package cc.diary.sketch.elements;

import java.io.Serializable;

import cc.diary.sketch.collision.RectangularBoundingBox;
import lombok.experimental.SuperBuilder;
import processing.core.PVector;

@SuperBuilder
public class BoundedText extends Element {
    // Set via superBuilder
    private Serializable text;
    private PVector coords;

    // Updated after each draw call
    private RectangularBoundingBox bounds;

    private String getMessage() {
        return text.toString();
    }

    // Make own function so size can be updated every time it's called
    // eg. if we change text size after setup runs
    private PVector getSize() {
        return new PVector(root.textWidth(getMessage()), root.g.textSize);
    }

    // executeSetup not required as there is a default impl in Element.

    public void executeDraw() {

        // Update bounds
        PVector size = getSize();
        bounds = new RectangularBoundingBox(coords, size);

        // Testing purposes, draw bounding box
        bounds.draw(root);

        // Draw text
        root.textAlign(root.LEFT, root.TOP);
        root.text(getMessage(), coords.x, coords.y, coords.z);
    }

}
