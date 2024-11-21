package cc.diary.sketch.data;

import java.util.List;
import java.util.function.Consumer;

public class Datastore {
    private final String HEART_RATE_CSV_FILENAME = "daily_heart_rate_jc.csv";

    private CsvProvider<HeartRateRecordBean> hrProvider;

    public Datastore() {
        hrProvider = new CsvProvider<>(HeartRateRecordBean.class, HEART_RATE_CSV_FILENAME);

        if (!hrProvider.load()) {
            System.out.println("Error loading hrProvider, exiting...");
            System.exit(1);
        }

        System.out.println("Loaded data!");
    }

    // Forgive me lord for I have sinned in the name of IntelliSense
    public void hrForEach(Consumer<HeartRateRecordBean> action) {
        for (HeartRateRecordBean entry : (List<HeartRateRecordBean>) hrProvider.getData()) {
            action.accept(entry);
        }
    }
}
