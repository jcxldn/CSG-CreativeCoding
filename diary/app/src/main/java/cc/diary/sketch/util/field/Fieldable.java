package cc.diary.sketch.util.field;

import java.lang.reflect.InvocationTargetException;

public class Fieldable {
    public static <T extends Fieldable> Field<T> toDefaultField(Class<T> clazz)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        Field<T> field = new Field<T>();
        T startingValue = clazz.getConstructor().newInstance();

        field.setField(startingValue);

        return field;
    }
}
