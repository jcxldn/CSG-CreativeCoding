package cc.diary.sketch.elements.text;

import java.io.Serializable;

import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.elements.Element;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public abstract class AbstractText extends Element {
    // Set via SuperBuilder
    protected Serializable text;
    protected PVector coords;

    // Updated after each draw call
    protected @Getter RectangularBoundingBox bounds;

    protected String getMessage() {
        return text.toString();
    }

    protected abstract PVector getSize();

    private void updateBounds() {
        PVector size = getSize();
        bounds = new RectangularBoundingBox(coords, size);

    }

    public void executeSetup() {
        updateBounds(); // make bounds available before first draw
    }

    public void executeDraw() {
        updateBounds();

        withTextSize(getSize().y, () -> {
            getRoot().textAlign(PConstants.LEFT, PConstants.TOP);
            getRoot().text(getMessage(), coords.x, coords.y, coords.z);
        });

        bounds.draw(this);
    }
}
