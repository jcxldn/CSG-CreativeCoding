package cc.diary.sketch;

import java.time.LocalDateTime;

import cc.diary.sketch.data.Datastore;
import cc.diary.sketch.elements.Stats;
import cc.diary.sketch.screens.YearViewScreen;
import processing.core.PApplet;
import processing.core.PVector;

public class Diary extends PApplet {

    private Datastore data;

    private YearViewScreen yvs;
    private Stats stats;

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        data = new Datastore();

        yvs = YearViewScreen.builder().root(this).data(data).year(2024).build();
        yvs.setup();

        int statsOffset = 5; // 2 (5 to see bounding box)
        stats = Stats.builder().root(this).coords(new PVector(width - statsOffset, height - statsOffset)).build();
        stats.setup();

        // yvs.setRoot(this);

        /**
         * data.hrForEach(entry -> {
         * LocalDateTime ldt = entry.getLocalDateTime();
         * String date = String.format("%s/%s/%s", ldt.getDayOfMonth(),
         * ldt.getMonthValue(), ldt.getYear());
         * println(String.format("[%s]: %d steps", date, entry.getSteps()));
         * });
         */

    }

    /**
     * public void draw() {
     * println("loop");
     * delay(1000);
     * }
     */

    public void draw() {
        yvs.draw();
        stats.draw();

    }

}
