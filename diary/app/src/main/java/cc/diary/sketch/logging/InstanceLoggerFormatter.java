package cc.diary.sketch.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class InstanceLoggerFormatter extends Formatter {

    private static final String FORMAT_SPECIFIER = "[%s] (%s.%s): %s";

    @Override
    public String format(LogRecord record) {
        String classNameNoPackage = record.getSourceClassName();
        String methodName = record.getSourceMethodName();
        return String.format(FORMAT_SPECIFIER,
                record.getLevel().getName(),
                classNameNoPackage,
                methodName,
                record.getMessage()

        );
    }

}
