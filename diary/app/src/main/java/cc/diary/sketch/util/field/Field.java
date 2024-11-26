package cc.diary.sketch.util.field;

import lombok.Getter;
import lombok.Setter;

// based on https://stackoverflow.com/a/24859777
// generic setter/getter to get around setting objects in lambda
public class Field<T> {
    private @Getter @Setter T field;
}
