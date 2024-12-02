package cc.diary.sketch.elements.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import cc.diary.sketch.Diary;

public class ListenerManager {
    private ArrayList<Element> elements;

    public ListenerManager(Diary root) {
        this.elements = new ArrayList<Element>();
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

    // TODO: implement required annotations
    // private Class[] requiredAnnotations = { Setup.class };

    public void register(Element element) {
        ensureRoot(element, () -> {
            this.elements.add(element);
            sort();
            runAllAnnotationsFor(element, ElementHandler.Event.SETUP);
        });
    }

    // true if removed successfully.
    public boolean unregister(Element element) {
        return this.elements.remove(element);
    }

    private void sort() {
        this.elements.sort((e1, e2) -> Integer.compare(e1.getPriority(), e2.getPriority()));
        Collections.reverse(elements); // higher priority first

        // this.elements.forEach(element -> {
        // System.out.printf("%d\t\t%s\r\n", element.getPriority(),
        // element.getClass().getSimpleName());
        // });
    }

    public void draw() {
        // sort();
        this.elements.forEach(element -> runAllAnnotationsFor(element, ElementHandler.Event.DRAW));
    }

    public void mouseClicked() {
        this.elements.forEach(element -> runAllAnnotationsFor(element, ElementHandler.Event.MOUSE_CLICKED));
    }
}
