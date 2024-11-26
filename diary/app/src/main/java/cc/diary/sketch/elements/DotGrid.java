package cc.diary.sketch.elements;

import java.util.function.BiConsumer;

import cc.diary.sketch.collision.CircularBoundingBox;
import cc.diary.sketch.collision.RectangularBoundingBox;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import processing.core.PVector;

@SuperBuilder
public class DotGrid extends Element {
    // Set via superBuilder
    private @Getter PVector coords;
    private @Getter PVector size;
    private @Getter int desiredElements;

    // Set in setup

    // Set during setup
    private @Getter PVector gridSize;
    private @Getter int elementSize;
    private RectangularBoundingBox boundingBox;

    // Determine grid size given bounds and desired elements
    private void determineSize() {
        float xToYRatio = Math.max(size.x, size.y) / Math.min(size.x, size.y);

        int x = (int) Math.ceil(Math.sqrt(desiredElements) * xToYRatio);
        // round up integer division <https://stackoverflow.com/a/17149572>
        int y = (int) Math.ceil((double) desiredElements / x);

        // Determine size of elements (use Math.min so we can't go off-screen)
        double elementSize = Math.min((size.x / x), (size.y / y));

        // Sanity check
        if (x * y < desiredElements) {
            System.out.printf("[%s]: number of elements for size [%d, %d] (%d) less than desired %d\r\n",
                    getClass().getName(), x, y, x * y, desiredElements);
            System.exit(3);
        }

        System.out.printf(
                "[%s]: area [%d, %d] used to create grid of size [%d, %d] (%d elements) to fit requested %d elements\r\n",
                getClass().getName(), (int) size.x, (int) size.y, x, y, x * y, desiredElements);

        // Set variables
        this.gridSize = new PVector(x, y);
        this.elementSize = (int) elementSize;
    }

    private void forEachElement(BiConsumer<PVector, Integer> action) {
        int xSize = (int) getGridSize().x;
        for (int index = 0; index < desiredElements; index++) {
            // Determine x, y position in grid
            int xIndex = index % xSize;
            int yIndex = index / xSize;

            PVector position = new PVector(
                    coords.x + ((xIndex * getElementSize()) + (getElementSize() / 2)),
                    coords.y + ((yIndex * getElementSize()) + (getElementSize() / 2)));

            action.accept(position, getElementSize());
        }
    }

    public void executeSetup() {
        if (size == null) {
            System.out.println("Size not set.");
            System.exit(2);
        }

        // Size was set
        determineSize();

        // Create bounding box
        boundingBox = new RectangularBoundingBox(coords, size);

    }

    public void executeDraw() {
        boundingBox.draw(this);

        forEachElement((position, size) -> {
            CircularBoundingBox circleBounds = new CircularBoundingBox(new PVector(position.x, position.y), size);
            getRoot().circle(position.x, position.y, size);
            circleBounds.draw(this);

            // Get the largest (square) bounding box that will fit inside the circle.
            int rectSize = (int) Math.floor((getElementSize() / 2) * Math.sqrt(2));
            // top left coords of rect
            PVector rectCoordsVector = new PVector(
                    position.x - (getElementSize() / 2),
                    position.y - (getElementSize() / 2));
            PVector rectSizeVector = new PVector(rectSize, rectSize);

            int offset = (int) Math.floor(Math.sqrt(getElementSize()));

            // square text BB
            RectangularBoundingBox textBounds = new RectangularBoundingBox(rectCoordsVector.add(offset, offset),
                    rectSizeVector);

            textBounds.draw(this);
        });
    }
}
