package cc.diary.sketch.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

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
                .build()
                .parse();

        return true;
    }
}
