package cc.diary.sketch.elements;

import cc.diary.sketch.elements.text.AbstractText;
import cc.diary.sketch.util.GetSizeIntermediate;
import cc.diary.sketch.util.VectorUtil;
import cc.diary.sketch.util.field.Field;
import lombok.experimental.SuperBuilder;
import processing.core.PVector;

@SuperBuilder
public class BoundedText extends AbstractText {
    private static final float TEXT_SIZE_INCREMENT = 1;
    private PVector maxBounds;

    @Override
    protected PVector getSize() {
        try {
            // Determine (largest) size for message
            boolean done = false;

            Field<GetSizeIntermediate> lastValidField = GetSizeIntermediate.toDefaultField(GetSizeIntermediate.class);

            while (!done) {
                Field<GetSizeIntermediate> nextField = GetSizeIntermediate.toDefaultField(GetSizeIntermediate.class);

                // Increment previous text size
                float nextTextSize = (float) (lastValidField.getField().getTextSize() + TEXT_SIZE_INCREMENT);

                // Set next text size to lastValid textSize + 1
                nextField.getField().setTextSize(nextTextSize);

                withTextSize(nextTextSize, () -> {
                    float determinedWidth = getRoot().textWidth(getMessage());
                    nextField.getField().setWidth(determinedWidth);
                });

                // Convert field to PVector
                PVector bounds = nextField.getField().toVector();

                if (VectorUtil.inBounds(bounds, maxBounds)) {
                    // update lastValid to next
                    lastValidField.setField(nextField.getField());
                } else {
                    done = true;
                }
            }

            return lastValidField.getField().toVector();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(4);
        }

        return null;
    }

}
