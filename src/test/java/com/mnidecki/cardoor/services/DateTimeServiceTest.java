package com.mnidecki.cardoor.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeServiceTest {

    @InjectMocks
    private DateTimeService dateTimeService;

    @Test
    public void dateShouldBeValid() {
        //Given
        String startDate = LocalDate.now().plusDays(2).toString();
        String startHour = "22:20";
        String returnDate = LocalDate.now().plusDays(4).toString();
        String returnHour = "22:20";
        //When
        boolean isValid = dateTimeService.isBookingDateValid(startDate,startHour,returnDate,returnHour);

        //Then
        assertTrue(isValid);

    }

    @Test
    public void dateShouldBeNotValidWhenStartIsBeforeNow() {
        //Given
        String startDate = LocalDate.now().toString();
        String startHour = LocalDateTime.now().minusHours(1).toLocalTime().toString();
        String returnDate = LocalDate.now().plusDays(4).toString();
        String returnHour = LocalDateTime.now().minusHours(1).toLocalTime().toString();

        //When
        boolean isValid = dateTimeService.isBookingDateValid(startDate,startHour,returnDate,returnHour);

        //Then
        assertFalse(isValid);
    }


    @Test
    public void dateShouldBeNotValidWhenStartDateIsAfterReturnDate() {
        //Given
        String startDate = LocalDate.now().plusDays(2).toString();
        String startHour = LocalDateTime.now().toLocalTime().toString();
        String returnDate = LocalDate.now().plusDays(1).toString();
        String returnHour = LocalDateTime.now().toLocalTime().toString();

        //When
        boolean isValid = dateTimeService.isBookingDateValid(startDate,startHour,returnDate,returnHour);

        //Then
        assertFalse(isValid);
    }
}