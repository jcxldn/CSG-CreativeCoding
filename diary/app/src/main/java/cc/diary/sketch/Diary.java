package cc.diary.sketch;

import java.time.LocalDateTime;

import cc.diary.sketch.data.Datastore;
import processing.core.PApplet;

public class Diary extends PApplet {

    private Datastore data;

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        data = new Datastore();

        data.hrForEach(entry -> {
            LocalDateTime ldt = entry.getLocalDateTime();
            String date = String.format("%s/%s/%s", ldt.getDayOfMonth(), ldt.getMonthValue(), ldt.getYear());
            println(String.format("[%s]: %d steps", date, entry.getSteps()));
        });

    }

    /**
     * public void draw() {
     * println("loop");
     * delay(1000);
     * }
     */

}
