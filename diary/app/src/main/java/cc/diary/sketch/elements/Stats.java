package cc.diary.sketch.elements;

import cc.diary.sketch.util.Color;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public class Stats extends Element {
    // Set via SuperBuilder
    private PVector coords;

    public void executeDraw() {
        // getRoot().textAlign(PConstants.RIGHT, PConstants.TOP);

        withFill(true, new Color(0, 0, 0), () -> {
            Text bt = Text.builder()
                    .root(getRoot())
                    .text(String.format("%d (%.0f FPS)", getRoot().frameCount, getRoot().frameRate))
                    .textSize(16)
                    .xAlign(PConstants.RIGHT)
                    .yAlign(PConstants.BOTTOM)
                    .coords(coords).build();
            bt.draw();
        });
    }
}
