package cc.diary.sketch;

import java.time.LocalDateTime;

import cc.diary.sketch.data.Datastore;
import cc.diary.sketch.screens.YearViewScreen;
import processing.core.PApplet;

public class Diary extends PApplet {

    private Datastore data;

    private YearViewScreen yvs;

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        data = new Datastore();

        yvs = YearViewScreen.builder().root(this).year(2024).build();
        yvs.setup();
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
        // clear();
        text("hi", width / 4, height / 4);
        yvs.draw();
    }

}
