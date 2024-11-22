package cc.diary.sketch.elements;

import cc.diary.sketch.Diary;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Element {
    protected Diary root;

    public Element() {
    }

    // default implementation
    protected void executeSetup() {
    }

    // default implementation
    protected void executeDraw() {
    };

    private void ensureRoot(Runnable action) {
        if (Diary.class.isInstance(root)) {
            action.run();
        } else {
            System.out.printf("[%s]: Root not set, did you call setRoot()?", this.getClass().getName());
            System.exit(2);
        }
    }

    public void setup() {
        ensureRoot(() -> executeSetup());
    }

    public void draw() {
        ensureRoot(() -> executeDraw());
    }
}
