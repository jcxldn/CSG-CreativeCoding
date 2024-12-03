package cc.diary.sketch.logging;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Logger class that can be extended for classes not using SuperBuilder.
 */
public class StandaloneLogger {
    @Getter(AccessLevel.PROTECTED)
    private static final InstanceLogger.Provider logger = InstanceLogger.Provider.builder().build();
}
