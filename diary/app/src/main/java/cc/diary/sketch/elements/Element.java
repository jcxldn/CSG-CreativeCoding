package cc.diary.sketch.elements;

import cc.diary.sketch.Diary;
import cc.diary.sketch.util.Color;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Element {
    private @Getter Diary root;

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

    // Utility functions

    // TODO add optional stroke
    public void withStroke(Color color, Runnable action) {
        // Make a record of the current colorMode and stroke
        int previousColorMode = root.g.colorMode;
        boolean previousStrokeEnabled = root.g.stroke;
        int previousStrokeColor = root.g.strokeColor;

        root.colorMode(color.getMode().getValue());
        root.stroke(color.getVal1(), color.getVal2(), color.getVal3(), color.getVal4());

        action.run();

        // Restore previous

        root.colorMode(previousColorMode);

        if (previousStrokeEnabled) {
            root.stroke(previousStrokeColor);
        } else {
            root.noStroke();
        }
    }

    public void withFill(boolean enabled, Color color, Runnable action) {
        // Make a record of the current fillColor and status
        int previousFillColor = root.g.fillColor;
        boolean previousFillEnabled = root.g.fill;

        // Set
        if (enabled) {
            root.fill(color.getVal1(), color.getVal2(), color.getVal3(), color.getVal4());
        } else {
            root.noFill();
        }

        action.run();

        // Restore previous
        if (previousFillEnabled) {
            root.fill(previousFillColor);
        } else {
            root.noFill();
        }
    }
}
