package cc.diary;

import cc.diary.sketch.Diary;

import processing.core.PApplet;

public class Main {
    public static void main(String[] args) {
        startSketch(new Diary());
    }

    public static <T extends PApplet> void startSketch(T applet) {
        Class<? extends PApplet> cls = applet.getClass();

        System.out.printf("Starting sketch %s (%s)\r\n\r\n", cls.getSimpleName(), cls.getName());

        String[] args = new String[] { cls.getName() };

        T.main(args);
    }
}
