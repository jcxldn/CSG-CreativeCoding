package cc.diary.sketch.elements;

import java.util.function.BiConsumer;

import javax.annotation.processing.Generated;

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
        int elementNum = 0;

        boolean done = false;

        while (!done) {
            elementNum += 1;

            // Determine bounds (as if a square)
            double squareBoundsX = Math.floor(Math.sqrt(elementNum));
            double squareBoundsY = Math.floor(elementNum / squareBoundsX);

            if ((squareBoundsX * squareBoundsY) >= desiredElements) {
                // Div by square to get bounds that suit our size
                double floatX = size.x / squareBoundsX;
                double floatY = size.y / squareBoundsY;

                // Round down to nearest number
                double x = Math.floor(floatX);
                double y = Math.floor(floatY);

                // Determine size of elements
                double elementSize = Math.max((size.x / x), (size.y / y));

                // Done! set variables
                // -1 for x since we are using dots and having to shift them by 0.5x elementSize
                // when drawing
                // so without this -1 we end up with an extra dot every row (as we lose 2x.5 (1)
                // dot when we shift)
                this.gridSize = new PVector((int) x - 1, (int) y);
                this.elementSize = (int) elementSize;

                done = true;
            }
        }
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
            getRoot().circle(position.x, position.y, size);
        });
    }
}
