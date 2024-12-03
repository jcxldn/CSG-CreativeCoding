package cc.diary.sketch.elements.core;

public enum ElementPriority {
    /**
     * Runs first
     */
    HIGHEST(0),

    SCREEN_ROOT(1),

    /**
     * Runs last
     */
    LOWEST(Integer.MAX_VALUE);

    public final int priority;

    private ElementPriority(int priority) {
        this.priority = priority;
    }
}
