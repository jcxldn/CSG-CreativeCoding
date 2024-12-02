package cc.diary.sketch.elements;

import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Clear extends Element {

    @ElementHandler(Event.DRAW)
    public void draw() {
        getRoot().background(255);
    }
}
