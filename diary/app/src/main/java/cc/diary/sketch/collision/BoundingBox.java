package cc.diary.sketch.collision;

import cc.diary.sketch.elements.Element;
import cc.diary.sketch.util.Color;
import processing.core.PVector;

public abstract class BoundingBox {
    protected static final Color OUTLINE_COLOR = new Color(255, 0, 0);

    public abstract boolean contains(PVector other);

    public abstract void draw(Element element);
}
