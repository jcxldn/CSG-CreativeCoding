package cc.diary.sketch.elements.text;

import java.io.Serializable;

import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public abstract class AbstractText extends Element {
    // Set via SuperBuilder
    @Builder.Default
    private boolean centred = false;
    protected Serializable text;
    protected PVector coords;
    protected PVector maxBounds;

    // Updated after each draw call
    protected @Getter RectangularBoundingBox bounds;

    protected String getMessage() {
        return text.toString();
    }

    protected abstract PVector getSize();

    private void updateBounds() {
        // If centred, use maxBounds as size otherwise use real size.
        PVector size = (centred) ? maxBounds : getSize();
        bounds = new RectangularBoundingBox(coords, size);

    }

    @ElementHandler(Event.SETUP)
    public void setup() {
        updateBounds(); // make bounds available before first draw
    }

    @ElementHandler(Event.DRAW)
    public void draw() {
        updateBounds();

        PVector finalCoords = coords.copy();

        withTextSize(getSize().y, () -> {
            if (centred) {
                getRoot().textAlign(PConstants.CENTER, PConstants.CENTER);
                finalCoords.add(maxBounds.x / 2, maxBounds.y / 2);

            } else {
                getRoot().textAlign(PConstants.LEFT, PConstants.TOP);
            }
            getRoot().text(getMessage(), finalCoords.x, finalCoords.y, finalCoords.z);
        });

        bounds.drawIfEnabled(this);
    }
}
