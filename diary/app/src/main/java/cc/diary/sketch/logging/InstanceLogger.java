package cc.diary.sketch.logging;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public abstract class InstanceLogger {

    /**
     * Extension of InstanceLogger to call (class).builder against as you cannot do
     * this from a base class annotated with SuperBuilder.
     */
    @SuperBuilder
    public static class Provider extends InstanceLogger {

    };

    private static final String NEWLINE = "\r\n";

    @Getter(value = AccessLevel.PRIVATE, lazy = true)
    private final Logger logger = createLogger();

    private final String[] ignoredClassNames = {
            StackTraceElement.class.getName(),
            Thread.class.getName(),
            InstanceLogger.class.getName()
    };

    public Logger createLogger() {
        Logger logger = Logger.getLogger(this.getClass().getSimpleName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new InstanceLoggerFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        return logger;
    }

    private Map<String, String> determineCalling() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        for (int i = 0; i < elements.length; i++) {
            StackTraceElement element = elements[i];

            if (!Arrays.asList(ignoredClassNames).contains(element.getClassName())) {
                // valid target (ie not something called in printf for example)

                // determine class name, method name
                String className;
                try {

                    Class<?> callingClass = Class.forName(element.getClassName());
                    className = callingClass.getSimpleName();
                } catch (ClassNotFoundException ex) {
                    className = "[unknown]";
                }

                String methodName = element.getMethodName();

                return Collections.singletonMap(className, methodName);
            }
        }

        // TODO: Collections.emptyMap() instead?
        return Collections.singletonMap("unknown", "unknown");
    }

    public void println(Level level, Serializable message) {
        // Determine caller
        Map<String, String> caller = determineCalling();
        Entry<String, String> names = caller.entrySet().iterator().next();
        String className = names.getKey();
        String methodName = names.getValue();

        LogRecord rec = new LogRecord(level, message.toString());
        rec.setSourceClassName(className);
        rec.setSourceMethodName(methodName);

        getLogger().log(rec);
    }

    public void println(Serializable message) {
        println(Level.INFO, message + NEWLINE);
    }

    public void printf(Serializable message, Object... args) {
        String formattedMsg = String.format(message.toString(), args);
        println(formattedMsg);
    }
}
