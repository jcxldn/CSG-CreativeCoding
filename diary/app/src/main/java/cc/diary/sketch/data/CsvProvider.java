package cc.diary.sketch.data;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import lombok.Getter;

public class CsvProvider<T extends RecordBean> {
    private String filename;

    private Class<T> cls;

    private @Getter List<T> data;

    public CsvProvider(Class<T> cls, String filename) {
        this.cls = cls;
        this.filename = filename;
    }

    public boolean load() {
        InputStream stream = ClassLoader.getSystemResourceAsStream(filename);

        if (stream == null)
            return false;

        InputStreamReader reader = new InputStreamReader(stream);

        this.data = new CsvToBeanBuilder<T>(reader)
                .withType(cls)
                // Ensure that any line with empty fields is ignored
                // (withFieldAsNull, withFilter)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .withFilter(values -> Arrays.stream(values).allMatch(value -> value != null))
                // Build and parse
                .build()
                .parse();

        return true;
    }
}
