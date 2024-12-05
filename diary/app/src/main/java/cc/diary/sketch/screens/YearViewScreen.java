package cc.diary.sketch.screens;

import java.util.Arrays;
import java.util.List;

import cc.diary.sketch.data.Datastore;
import cc.diary.sketch.data.HeartRateRecordBean;
import cc.diary.sketch.elements.Text;
import cc.diary.sketch.elements.DotGrid;
import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
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

        private Text yearText;
        private DotGrid dots;

        private String getText() {
                return String.format("%d (%d/365 days collected)", year, hrChosenYear.size());
        }

        @ElementHandler(Event.SETUP)
        public void setup() {
                // Get all (chosen year) data
                hrChosenYear = data.hrFilter((item) -> item.getLocalDateTime().getYear() == year);

                printf("Using year %d (%d entries)", year, hrChosenYear.size());

                yearText = Text.builder()
                                .root(getRoot())
                                .text(getText())
                                .textSize(textSize)
                                // .coords(new PVector(24, 24))
                                .coords(new PVector(0, 0))
                                .build();

                getRoot().getListenerManager().register(yearText);

                dots = DotGrid.builder()
                                .root(getRoot())
                                .coords(new PVector(0, 24))
                                .size(new PVector(getRoot().width, getRoot().height - textSize))
                                .elements(hrChosenYear)
                                .build();

                getRoot().getListenerManager().register(dots);

                printf("created DotGrid of %fx%f (size %d)",
                                dots.getGridSize().x,
                                dots.getGridSize().y,
                                dots.getElementSize());

        }

        private void unregister() {
                getRoot().getListenerManager().unregister(yearText);
                getRoot().getListenerManager().unregister(dots);
        }

        private static int[] ACTION_KEYS = { PConstants.LEFT, PConstants.RIGHT };

        @ElementHandler(Event.KEY_PRESSED)
        public void onKeyPressed() {
                if (getRoot().key == PConstants.CODED) {
                        int keyCode = getRoot().keyCode;

                        if (Arrays.stream(ACTION_KEYS).anyMatch(x -> x == keyCode)) {
                                // if left, decrement else increment
                                year = (keyCode == PConstants.LEFT) ? year - 1 : year + 1;

                                System.out.println("CHANGED");

                                hrChosenYear = data.hrFilter((item) -> item.getLocalDateTime().getYear() == year);

                                unregister();

                                yearText = yearText.toBuilder()
                                                .text(getText())
                                                .build();

                                dots = dots.toBuilder()
                                                .elements(hrChosenYear)
                                                .build();

                                getRoot().getListenerManager().register(yearText);
                                getRoot().getListenerManager().register(dots);
                        }
                }
        }
}
