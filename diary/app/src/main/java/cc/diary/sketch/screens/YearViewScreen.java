package cc.diary.sketch.screens;

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

        @ElementHandler(Event.SETUP)
        public void setup() {
                // Get all (chosen year) data
                hrChosenYear = data.hrFilter((item) -> item.getLocalDateTime().getYear() == year);

                printf("Using year %d (%d entries)", year, hrChosenYear.size());

                yearText = Text.builder()
                                .root(getRoot())
                                .text(String.format("%d (%d/365 days collected)", year, hrChosenYear.size()))
                                .textSize(textSize)
                                // .coords(new PVector(24, 24))
                                .coords(new PVector(0, 0))
                                .build();

                getRoot().getListenerManager().register(yearText);

                dots = DotGrid.builder()
                                .root(getRoot())
                                .coords(new PVector(0, 24))
                                .size(new PVector(getRoot().width, getRoot().height - textSize))
                                .desiredElements(hrChosenYear.size())
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

        @ElementHandler(Event.KEY_PRESSED)
        public void onKeyPressed() {
                if (getRoot().key == PConstants.CODED) {
                        int keyCode = getRoot().keyCode;

                        if (keyCode == PConstants.LEFT) {
                                year--;
                                unregister();
                                setup();
                        } else if (keyCode == PConstants.RIGHT) {
                                year++;
                                unregister();
                                setup();
                        }
                }
        }
}
