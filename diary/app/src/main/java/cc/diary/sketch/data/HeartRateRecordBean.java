package cc.diary.sketch.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import lombok.Getter;

// Date,Active Energy (kJ),Heart Rate [Min] (bpm),
// Heart Rate [Max] (bpm),Heart     Rate [Avg] (bpm),
// Step Count (steps),Walking + Running Distance (km) 

/**
 * Record "bean"
 */
public class HeartRateRecordBean extends RecordBean {
    @CsvDate("yyyy-MM-dd hh:mm:ss")
    @CsvBindByName(required = false)
    private Date date;

    @CsvNumber("#0.00")
    @CsvBindByName(required = false)
    @Getter
    private float activeEnergy;

    @CsvNumber("000")
    @CsvBindByName(required = false)
    @Getter
    private int heartRateMin;

    @CsvNumber("000")
    @CsvBindByName(required = false)
    @Getter
    private int heartRateMax;

    @CsvNumber("000")
    @CsvBindByName(required = false)
    @Getter
    private int heartRateAvg;

    @CsvNumber("000")
    @CsvBindByName(required = false)
    @Getter
    private int steps;

    @CsvNumber("#0.00")
    @CsvBindByName(required = false)
    @Getter
    private float distance;

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
