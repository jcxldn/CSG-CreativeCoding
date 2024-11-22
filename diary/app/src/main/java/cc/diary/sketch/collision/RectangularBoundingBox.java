package cc.diary.sketch.collision;

import cc.diary.sketch.Diary;
import lombok.Getter;
import processing.core.PGraphics;
import processing.core.PVector;

public class RectangularBoundingBox extends BoundingBox {

    private @Getter PVector min;
    private @Getter PVector size;

    public RectangularBoundingBox(PVector min, PVector size) {
        this.min = min;
        this.size = size;
    }

    public RectangularBoundingBox(float minX, float minY, float sizeX, float sizeY) {
        // Call above constructor
        this(new PVector(minX, minY), new PVector(sizeX, sizeY));
    }

    public PVector getMax() {
        return new PVector(getMin().x + getSize().x, getMin().y + getSize().y);
    }

    public boolean contains(PVector other) {
        return (
        // Is inside x bound
        getMin().x <= other.x && // Is after min x
                getMax().x >= other.x && // And before max x

                // Is inside y bound
                getMin().y <= other.y && // Is after min y
                getMax().y >= other.y // And before max y
        );
    }

    public void draw(Diary root) {
        root.stroke(255, 0, 0);
        root.noFill();
        // Rect takes (min) x,y as well as width(x) and height (not max x,y!!)
        root.rect(getMin().x, getMin().y, getSize().x, getSize().y);
    }

}
