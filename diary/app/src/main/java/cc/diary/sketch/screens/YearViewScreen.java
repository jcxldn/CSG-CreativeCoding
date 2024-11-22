package cc.diary.sketch.screens;

import cc.diary.sketch.elements.BoundedText;
import cc.diary.sketch.elements.Element;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public class YearViewScreen extends Element {
    private int year;

    private BoundedText yearText;

    public void executeSetup() {
        yearText = BoundedText.builder()
                .root(getRoot())
                .text(year)
                // .coords(new PVector(24, 24))
                .coords(new PVector(124, 100))
                .build();

        yearText.setup();

    }

    public void executeDraw() {
        getRoot().background(255);

        getRoot().textAlign(PConstants.CENTER);

        getRoot().fill(0);

        yearText.draw();

        getRoot().textSize(128);

        // BoundedText yearText = new BoundedText(year, new PVector(24, 24));
        // yearText

    }
}
