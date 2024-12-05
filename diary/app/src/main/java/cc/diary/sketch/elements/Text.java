package cc.diary.sketch.elements;

import java.io.Serializable;

import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import cc.diary.sketch.util.Color;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Text (with an optional BoundingBox)
 */
// TODO: make Text part of AbstractText
@SuperBuilder(toBuilder = true)
public class Text extends Element {
    // Set via superBuilder
    private Serializable text;
    private PVector coords;
    private float textSize;

    // TODO: rectangular bounding box does not work if these are changed (see Stats)
    private @Builder.Default int xAlign = PConstants.LEFT;
    private @Builder.Default int yAlign = PConstants.TOP;

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

    @ElementHandler(Event.SETUP)
    public void setup() {
        updateBounds(); // make bounds available before first draw
    }

    @ElementHandler(Event.DRAW)
    public void draw() {

        // Update bounds
        updateBounds();

        // Testing purposes, draw bounding box
        bounds.drawIfEnabled(this);

        // Draw text
        withFill(true, new Color(0, 0, 0), () -> {
            withTextSize(textSize, () -> {
                getRoot().textAlign(xAlign, yAlign);
                getRoot().text(getMessage(), coords.x, coords.y, coords.z);
            });
        });
    }

}
