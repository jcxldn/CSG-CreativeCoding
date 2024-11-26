package cc.diary.sketch.screens;

import java.time.LocalDateTime;
import java.util.List;

import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.data.Datastore;
import cc.diary.sketch.data.HeartRateRecordBean;
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
    // Set via SuperBuilder
    private Datastore data;
    private int year;

    private List<HeartRateRecordBean> hrChosenYear;

    private final float textSize = 24;

    private BoundedText yearText;
    private DotGrid dots;

    public void executeSetup() {
        // Get all (chosen year) data
        hrChosenYear = data.hrFilter((item) -> item.getLocalDateTime().getYear() == year);

        System.out.printf("[%s]: Using year %d (%d entries)\r\n", getClass().getName(), year, hrChosenYear.size());

        yearText = BoundedText.builder()
                .root(getRoot())
                .text(String.format("%d (%d/365 days collected)", year, hrChosenYear.size()))
                .textSize(textSize)
                // .coords(new PVector(24, 24))
                .coords(new PVector(0, 0))
                .build();
        yearText.setup();

        dots = DotGrid.builder()
                .root(getRoot())
                .coords(new PVector(0, 24))
                .size(new PVector(getRoot().width, getRoot().height - textSize))
                .desiredElements(hrChosenYear.size())
                .build();

        dots.setup();

        System.out.printf("[%s]: created DotGrid of %fx%f (size %d)\r\n", this.getClass().getName(),
                dots.getGridSize().x,
                dots.getGridSize().y,
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
