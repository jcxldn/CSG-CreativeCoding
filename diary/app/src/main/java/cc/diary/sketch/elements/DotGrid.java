package cc.diary.sketch.elements;

import java.util.List;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.function.TriConsumer;

import cc.diary.sketch.collision.CircularBoundingBox;
import cc.diary.sketch.collision.RectangularBoundingBox;
import cc.diary.sketch.data.HeartRateRecordBean;
import cc.diary.sketch.elements.core.Element;
import cc.diary.sketch.elements.core.ElementHandler;
import cc.diary.sketch.elements.core.ElementHandler.Event;
import cc.diary.sketch.screens.ActiveScreen;
import cc.diary.sketch.util.Color;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PApplet;
import processing.core.PVector;

@SuperBuilder(toBuilder = true)
public class DotGrid extends Element {
    // Set via superBuilder
    private @Getter PVector coords;
    private @Getter PVector size;
    private @Getter List<HeartRateRecordBean> elements;

    // Set in setup

    // Set during setup
    private @Getter PVector gridSize;
    private @Getter int elementSize;
    private RectangularBoundingBox boundingBox;

    // caching
    private BoundedText[] textArr;
    private CircularBoundingBox[] dotBounds;

    private static final Color DOT_COLOR = new Color(255, 128, 128);
    private static final Color DOT_COLOR_ACTIVE = new Color(255, 32, 32);

    private int getDesiredElements() {
        return elements.size();
    }

    // Determine grid size given bounds and desired elements
    private void determineSize() {
        float xToYRatio = Math.max(size.x, size.y) / Math.min(size.x, size.y);

        int x = (int) Math.ceil(Math.sqrt(getDesiredElements()) * xToYRatio);
        // round up integer division <https://stackoverflow.com/a/17149572>
        int y = (int) Math.ceil((double) getDesiredElements() / x);

        // Determine size of elements (use Math.min so we can't go off-screen)
        double elementSize = Math.min((size.x / x), (size.y / y));

        // Sanity check
        if (x * y < getDesiredElements()) {
            printf("number of elements for size [%d, %d] (%d) less than desired %d",
                    x, y, x * y, getDesiredElements());
            System.exit(3);
        }
        printf(
                "area [%d, %d] used to create grid of size [%d, %d] (%d elements) to fit requested %d elements",
                (int) size.x, (int) size.y, x, y, x * y, getDesiredElements());

        // Set variables
        this.gridSize = new PVector(x, y);
        this.elementSize = (int) elementSize;
    }

    private void forEachElement(TriConsumer<Integer, PVector, Integer> action) {
        int xSize = (int) getGridSize().x;
        for (int index = 0; index < getDesiredElements(); index++) {
            // Determine x, y position in grid
            int xIndex = index % xSize;
            int yIndex = index / xSize;

            PVector position = new PVector(
                    coords.x + ((xIndex * getElementSize()) + (getElementSize() / 2)),
                    coords.y + ((yIndex * getElementSize()) + (getElementSize() / 2)));

            action.accept(index, position, getElementSize());
        }
    }

    @ElementHandler(Event.SETUP)
    public void setup() {
        if (size == null) {
            println("Size not set.");
            System.exit(2);
        }

        // Size was set
        determineSize();

        textArr = new BoundedText[getDesiredElements()];
        dotBounds = new CircularBoundingBox[getDesiredElements()];

        forEachElement((index, position, size) -> {
            dotBounds[index] = new CircularBoundingBox(new PVector(position.x, position.y), size);
        });

        // Create bounding box
        boundingBox = new RectangularBoundingBox(coords, size);

    }

    @ElementHandler(Event.DRAW)
    public void draw() {

        boundingBox.drawIfEnabled(this);

        forEachElement((index, position, size) -> {
            // Darken circle (switch to "active color") if under mouse
            Color color = (dotBounds[index].contains(new PVector(getRoot().mouseX, getRoot().mouseY)))
                    ? DOT_COLOR_ACTIVE
                    : DOT_COLOR;

            // Draw circle with selected color
            withFill(true, color, () -> {
                getRoot().circle(position.x, position.y, size);
            });

            dotBounds[index].drawIfEnabled(this);

            // Get the largest (square) bounding box that will fit inside the circle.
            int rectSize = (int) Math.floor((getElementSize() / 2) * Math.sqrt(2));
            // top left coords of rect
            PVector rectCoordsVector = new PVector(
                    position.x - (rectSize / 2),
                    position.y - (rectSize / 2));
            PVector rectSizeVector = new PVector(rectSize, rectSize);

            int dayAverage = elements.get(index).getHeartRateAvg();

            // if boundedText entry not cached, create and store in "cache" array
            if (textArr[index] == null) {
                textArr[index] = BoundedText.builder()
                        .root(getRoot())
                        .text(dayAverage)
                        .centred(true)
                        .maxBounds(rectSizeVector)
                        .coords(rectCoordsVector)
                        .build();
            }

            // Draw from cache array
            withFill(true, new Color(0, 0, 0), () -> {
                textArr[index].draw();
            });

            // Rect bounding box for testing
            // RectangularBoundingBox textBounds = new
            // RectangularBoundingBox(rectCoordsVector, rectSizeVector);
            // textBounds.draw(this);
        });
    }

    @ElementHandler(Event.MOUSE_CLICKED)
    public void onClick() {
        forEachElement((index, position, size) -> {
            if (mouseInBounds(dotBounds[index])) {
                println(index);
                getRoot().setActiveScreen(ActiveScreen.DAY_VIEW_SCREEN);
            }
        });
    }
}
