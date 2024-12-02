package cc.diary.sketch;

import cc.diary.sketch.data.Datastore;
import cc.diary.sketch.elements.Clear;
import cc.diary.sketch.elements.Stats;
import cc.diary.sketch.elements.core.ListenerManager;
import cc.diary.sketch.screens.YearViewScreen;
import lombok.Getter;
import processing.core.PApplet;
import processing.core.PVector;

public class Diary extends PApplet {

    // default false
    private @Getter boolean displayBoundingBoxes;
    private boolean displayFrameTimes;

    private Datastore data;
    private @Getter ListenerManager listenerManager;

    private YearViewScreen yvs;
    private Stats stats;

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        data = new Datastore();
        listenerManager = new ListenerManager(this);

        yvs = YearViewScreen.builder().root(this).data(data).year(2024).build();
        listenerManager.register(yvs);

        int statsOffset = 5; // 2 (5 to see bounding box)
        stats = Stats.builder()
                .root(this)
                .priority(-1)
                .visibleWhen(() -> displayFrameTimes)
                .coords(new PVector(width - statsOffset, height - statsOffset))
                .build();
        listenerManager.register(stats);

        Clear clear = Clear.builder().root(this).build();
        listenerManager.register(clear);

    }

    public void draw() {
        listenerManager.draw();
    }

    public void keyPressed() {
        if (key == 'b') {
            displayBoundingBoxes = !displayBoundingBoxes;
        } else if (key == 'f') {
            displayFrameTimes = !displayFrameTimes;
        }
    }

}
