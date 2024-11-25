package cc.diary.sketch.screens;

import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.elements.BoundedText;
import cc.diary.sketch.elements.DotGrid;
import cc.diary.sketch.elements.Element;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import processing.core.PConstants;
import processing.core.PVector;

@SuperBuilder
public class YearViewScreen extends Element {
    private int year;

    private final float textSize = 24;

    private BoundedText yearText;
    private DotGrid dots;

    public void executeSetup() {
        yearText = BoundedText.builder()
                .root(getRoot())
                .text(year)
                // .coords(new PVector(24, 24))
                .coords(new PVector(0, 0))
                .build();
        yearText.setup();

        dots = DotGrid.builder()
                .root(getRoot())
                .coords(new PVector(0, 24))
                .size(new PVector(getRoot().width, getRoot().height - textSize))
                .desiredElements(365)
                .build();

        dots.setup();

        System.out.printf("%fx%f (size %d)\r\n", dots.getGridSize().x, dots.getGridSize().y,
                dots.getElementSize());

        // dots.setup();

    }

    public void executeDraw() {
        getRoot().background(255);

        getRoot().fill(0);
        getRoot().textSize(textSize);
        yearText.draw();

        getRoot().fill(0, 255, 0);

        dots.draw();
    }
}
