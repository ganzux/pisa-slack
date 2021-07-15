package com.ganzux.mahris.slack.util;

import com.ganzux.mahris.slack.persistance.dto.TimeSheetDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Component
public class TimeUtil {

    private static final Locale LOCALE = Locale.UK;
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String PICKER_DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public LocalDate weekInit(LocalDate localDate){
        if (null == localDate) {
            localDate = LocalDate.now();
        }
        TemporalField fieldISO = WeekFields.of(LOCALE).dayOfWeek();

        return localDate.with(fieldISO, 1);
    }

    public LocalDate weekEnd(LocalDate localDate){
        return weekInit(localDate).plusDays(6);
    }

    public long calculateHoursWork(List<TimeSheetDto> timeSheetDtos) {
        long hours = 0L;

        for (TimeSheetDto timeSheetDto : timeSheetDtos) {
            hours += timeSheetDto.getDuration();
        }

        return Math.round(hours / 60);
    }

    public String today() {
        LocalDate localDate = LocalDate.now();
        return prettyDate(localDate);
    }

    public String weekInit() {
        LocalDate localDate = weekInit(LocalDate.now());
        return prettyDate(localDate);
    }

    public String weekEnd() {
        LocalDate localDate = weekEnd(LocalDate.now());
        return prettyDate(localDate);
    }

    public LocalDate transformFromView(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PICKER_DATE_PATTERN);
        return LocalDate.parse(date, formatter);
    }

    public String prettyDate(LocalDate localDate) {
        return localDate.format(FORMATTER);
    }

}
