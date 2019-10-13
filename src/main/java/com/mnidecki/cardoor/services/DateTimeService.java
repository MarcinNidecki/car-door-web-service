package com.mnidecki.cardoor.services;

import org.apache.commons.validator.routines.TimeValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.apache.commons.validator.GenericValidator.isDate;

@Service
public class DateTimeService {

    private TimeValidator timeValidator = new TimeValidator();


    public String getShortDisplayNameOfDay(String date) {
        OffsetDateTime odt = OffsetDateTime.parse(date);
        LocalDate localDate = odt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
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

    public boolean isBookingDateValid(String startDate, String startTime, String endDate, String endTime) {
        if (isDateValid(startDate) && isDateValid(endDate) && isTimeValid(startTime) && isTimeValid(endTime)) {
            if (startTime.length() == 4) startTime = "0".concat(startTime);
            if (endTime.length() == 4) endTime = "0".concat(endTime);
            LocalDateTime start = LocalDateTime.parse(startDate + "T" + startTime + ":00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T" + endTime + ":00");
            return start.isAfter(LocalDateTime.now()) && start.isBefore(end);
        } else {
            return false;
        }
    }

    private boolean isDateValid(String date) {
        return isDate(date, "yyyy-MM-dd", true);
    }

    private boolean isTimeValid(String time) {
        return timeValidator.isValid(time, "HH:mm");
    }

    public String fixTheTime(String time) {
        if (timeValidator.isValid(time, "HH:mm")) {
            if (time.length() == 4) time = "0".concat(time);
        }
        return time;
    }


}
