package cc.diary.sketch.elements;

import cc.diary.sketch.collision.CircularBoundingBox;
import cc.diary.sketch.data.HeartRateRecordBean;
import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import cc.diary.sketch.util.Color;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PVector;

@SuperBuilder
public class Dot extends Element {
    // Set via superbuilder
    private @Getter CircularBoundingBox bounds;
    private PVector position;
    private int size;
    private int elementSize;
    private HeartRateRecordBean record;

    // set during setup
    private BoundedText text;

    private static final Color DOT_COLOR = new Color(255, 128, 128);
    private static final Color DOT_COLOR_ACTIVE = new Color(255, 32, 32);

    @ElementHandler(Event.SETUP)
    public void setup() {
        // Get the largest (square) bounding box that will fit inside the circle.
        int rectSize = (int) Math.floor((elementSize / 2) * Math.sqrt(2));
        // top left coords of rect
        PVector rectCoordsVector = new PVector(
                position.x - (rectSize / 2),
                position.y - (rectSize / 2));
        PVector rectSizeVector = new PVector(rectSize, rectSize);

        int dayAverage = record.getHeartRateAvg();

        // if boundedText entry not cached, create and store in "cache" array
        text = BoundedText.builder()
                .root(getRoot())
                .text(dayAverage)
                .centred(true)
                .maxBounds(rectSizeVector)
                .coords(rectCoordsVector)
                .build();

    }

    @ElementHandler(Event.DRAW)
    public void draw() {
        drawCircle();
        drawText();
        bounds.drawIfEnabled(this);
    }

    private void drawCircle() {
        // Darken circle (switch to "active color") if under mouse
        Color color = (bounds.contains(new PVector(getRoot().mouseX, getRoot().mouseY)))
                ? DOT_COLOR_ACTIVE
                : DOT_COLOR;

        // Draw circle with selected color
        withFill(true, color, () -> {
            getRoot().circle(position.x, position.y, size);
        });
    }

    private void drawText() {
        // Draw from cache array
        withFill(true, new Color(0, 0, 0), () -> {
            text.draw();
        });
    }
}
