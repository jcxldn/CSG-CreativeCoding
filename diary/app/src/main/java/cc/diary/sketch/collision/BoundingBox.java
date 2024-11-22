package cc.diary.sketch.collision;

import cc.diary.sketch.Diary;
import processing.core.PVector;

public abstract class BoundingBox {
    public abstract boolean contains(PVector other);

    public abstract void draw(Diary draw);
}
