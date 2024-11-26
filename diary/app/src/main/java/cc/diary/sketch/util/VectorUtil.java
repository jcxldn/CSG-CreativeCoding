package cc.diary.sketch.util;

import processing.core.PVector;

public class VectorUtil {
    /**
     * Return true if vector is within target
     * 
     * @param vector
     * @param other
     * @return
     */
    public static boolean inBounds(PVector vector, PVector target) {
        return (vector.x <= target.x &&
                vector.y <= target.y);
    }
}
