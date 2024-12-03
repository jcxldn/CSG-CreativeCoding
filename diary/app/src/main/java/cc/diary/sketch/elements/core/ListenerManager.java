package cc.diary.sketch.elements.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;
import java.util.function.Consumer;

import cc.diary.sketch.Diary;
import cc.diary.sketch.util.field.Field;

public class ListenerManager {
    private PriorityQueue<Element> elements;

    public ListenerManager(Diary root) {
        this.elements = new PriorityQueue<Element>(1, new ElementComparator());
    }

    private void runAllAnnotationsFor(Element element, ElementHandler.Event event) {
        Class<? extends Element> clazz = element.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            ElementHandler[] handlers = method.getAnnotationsByType(ElementHandler.class);

            for (ElementHandler handler : handlers) {
                if (handler.value() == event) {
                    ensureRoot(element, () -> {
                        try {
                            // execute if visible
                            if (element.getVisibleWhen().get())
                                method.invoke(element);

                        } catch (IllegalAccessException | InvocationTargetException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.exit(3);
                        }
                    });
                }
            }
        }
    }

    private void ensureRoot(Element listener, Runnable action) {
        if (Diary.class.isInstance(listener.getRoot())) {
            action.run();
        } else {
            System.out.printf("[%s]: Root not set, did you call setRoot()?", listener.getClass().getName());
        }
    }

    // Iterator ((PriorityQueue).forEach) not guaranteed to be in priority order
    // <https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html>
    // we will use poll instead and expose a forEach like method
    // Returns highest priority item first (lowest getPriority())
    private void forEach(Consumer<Element> action) {
        // shallow copy the queue
        PriorityQueue<Element> clonedQueue = new PriorityQueue<Element>(elements);

        // pop head element until empty (using clone so we keep orig queue)
        while (!clonedQueue.isEmpty()) {
            action.accept(clonedQueue.poll());
        }
    }

    public void displayPriorities() {
        // use a field to increment integer value inside consumer action (a lambda?)
        Field<Integer> index = new Field<Integer>();
        index.setField(-1);

        forEach(element -> {
            index.setField(index.getField() + 1); // increment index
            ElementPriority priority = element.getPriority(); // get priority enum

            // display values
            System.out.printf("%s\t(order #%d)\t%s\t(%d)\r\n",
                    String.format(
                            "%20s",
                            element.getClass().getSimpleName()),
                    index.getField(),
                    priority.name(),
                    priority.priority);
        });
    }

    // TODO: implement required annotations
    // private Class[] requiredAnnotations = { Setup.class };

    public void register(Element element) {
        ensureRoot(element, () -> {
            runAllAnnotationsFor(element, ElementHandler.Event.SETUP);
            this.elements.add(element);
        });
    }

    // true if removed successfully.
    public boolean unregister(Element element) {
        return this.elements.remove(element);
    }

    public void draw() {
        forEach(element -> runAllAnnotationsFor(element, ElementHandler.Event.DRAW));
    }

    public void mouseClicked() {
        forEach(element -> runAllAnnotationsFor(element, ElementHandler.Event.MOUSE_CLICKED));
    }
}
