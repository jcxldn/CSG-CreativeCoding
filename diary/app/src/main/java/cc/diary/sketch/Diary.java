package cc.diary.sketch;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import cc.diary.sketch.data.DataProvider;
import cc.diary.sketch.data.HeartRateRecordBean;
import processing.core.PApplet;

public class Diary extends PApplet {

    private DataProvider dp;

    // Forgive me lord for I have sinned in the name of IntelliSense
    @SuppressWarnings("unchecked")
    private void forEachEntry(Consumer<? super HeartRateRecordBean> action) {
        for (HeartRateRecordBean entry : (List<HeartRateRecordBean>) dp.getData()) {
            action.accept(entry);
        }
    }

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        println("Hello, world!");

        dp = new DataProvider<HeartRateRecordBean>(HeartRateRecordBean.class, "daily_heart_rate_jc.csv");

        Boolean loadWasSuccessful = dp.load();

        if (!loadWasSuccessful) {
            System.exit(1);
        }

        println("Loaded csv file!");

        forEachEntry(entry -> {
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
