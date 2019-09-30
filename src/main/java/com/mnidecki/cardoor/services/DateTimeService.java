package com.mnidecki.cardoor.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class DateTimeService {

    public String getShortDisplayNameOfDay(String date) {
        OffsetDateTime odt = OffsetDateTime.parse(date);
        LocalDate localDate = odt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return  localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    public String getLongDisplayNameOfDay(String date) {
        OffsetDateTime odt = OffsetDateTime.parse(date);
        LocalDate localDate = odt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public String getDateFromString(String date) {
        OffsetDateTime odt = OffsetDateTime.parse(date);
        return odt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }
}
