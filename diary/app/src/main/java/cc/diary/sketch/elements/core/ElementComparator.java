package cc.diary.sketch.elements.core;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element> {

    @Override
    public int compare(Element e1, Element e2) {
        // Lowest first
        return Integer.compare(e1.getPriority().priority, e2.getPriority().priority);
    }
}
