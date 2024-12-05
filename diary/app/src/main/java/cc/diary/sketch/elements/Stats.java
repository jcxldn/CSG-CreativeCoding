package cc.diary.sketch.elements;

import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import cc.diary.sketch.util.Color;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public class Stats extends Element {
    // Set via SuperBuilder
    private PVector coords;

    @ElementHandler(Event.DRAW)
    public void draw() {
        // getRoot().textAlign(PConstants.RIGHT, PConstants.TOP);

        withFill(true, new Color(0, 0, 0), () -> {
            Text bt = Text.builder()
                    .root(getRoot())
                    .text(
                            String.format("%d/%d elements enabled | %d logger(s) | frame %d (%.0f FPS)",
                                    getRoot().getListenerManager().getSizeEnabled(),
                                    getRoot().getListenerManager().getSizeAll(),
                                    getLoggersSize(),
                                    getRoot().frameCount,
                                    getRoot().frameRate))
                    .textSize(16)
                    .xAlign(PConstants.RIGHT)
                    .yAlign(PConstants.BOTTOM)
                    .coords(coords).build();
            bt.draw();
        });
    }
}
