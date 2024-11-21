package cc.diary.sketch.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.opencsv.bean.CsvToBeanBuilder;

import lombok.Getter;

public class DataProvider<T extends RecordBean> {
    private String filename;

    private Class<T> cls;

    private @Getter List<T> data;

    public DataProvider(Class<T> cls, String filename) {
        this.cls = cls;
        this.filename = filename;
    }

    public boolean load() {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
            FileReader reader = new FileReader(path.toFile());

            this.data = new CsvToBeanBuilder<T>(reader)
                    .withType(cls)
                    .build()
                    .parse();

            return true;

        } catch (FileNotFoundException | URISyntaxException e) {

            return false; // file not found or could not
        }
    }
}
