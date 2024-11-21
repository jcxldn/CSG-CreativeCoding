package cc.diary.sketch;

import processing.core.PApplet;

public class Diary extends PApplet {

    // call size here when not using PDE
    // use setup() for other stuff
    public void settings() {
        size(640, 480);
    }

    public void setup() {
        println("Hello, world!");
    }

    public void draw() {
        println("loop");
        delay(1000);
    }

}
