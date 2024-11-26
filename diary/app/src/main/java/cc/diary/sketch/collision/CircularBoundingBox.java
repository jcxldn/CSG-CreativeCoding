package cc.diary.sketch.collision;

import cc.diary.sketch.elements.Element;
import lombok.Getter;
import processing.core.PApplet;
import processing.core.PVector;

public class CircularBoundingBox extends BoundingBox {
    private @Getter PVector coords;
    private @Getter int diameter;

    public CircularBoundingBox(PVector coords, int diameter) {
        this.coords = coords;
        this.diameter = diameter;
    }

    public boolean contains(PVector other) {
        return PApplet.dist(coords.x, coords.y, other.x, other.y) <= diameter / 2;
    }

    @Override
    public void draw(Element element) {
        element.withStroke(BoundingBox.OUTLINE_COLOR, () -> {
            element.withFill(false, null, () -> {
                element.getRoot().circle(coords.x, coords.y, diameter);
            });
        });
    }

}
