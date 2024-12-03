package cc.diary;

import cc.diary.sketch.Diary;
import cc.diary.sketch.logging.StandaloneLogger;
import processing.core.PApplet;

public class Main extends StandaloneLogger {

    public static void main(String[] args) {
        startSketch(new Diary());
    }

    public static <T extends PApplet> void startSketch(T applet) {
        Class<? extends PApplet> cls = applet.getClass();

        getLogger().printf("Starting sketch %s (%s)", cls.getSimpleName(), cls.getName());

        String[] args = new String[] { cls.getName() };

        T.main(args);
    }
}
