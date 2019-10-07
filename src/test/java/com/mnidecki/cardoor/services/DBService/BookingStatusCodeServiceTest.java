package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.repository.BookingRepository;
import com.mnidecki.cardoor.repository.BookingStatusCodeRepository;
import com.mnidecki.cardoor.services.SimpleEmailService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingStatusCodeServiceTest {

    @InjectMocks
    private BookingStatusCodeService bookingStatusCodeService;
    @Mock
    private BookingStatusCodeRepository bookingStatusCodeRepository;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldFindAllBookingStatusCode() {
        //Given
        BookingStatusCode bookingStatusCode =  new BookingStatusCode();
        bookingStatusCode.setId(1L);
        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .totalCost(BigDecimal.valueOf(600))
                .startDate(Timestamp.valueOf("2020-12-11 15:00:00"))
                .returnDate(Timestamp.valueOf("2020-12-13 15:00:00"))
                .createdDate(Timestamp.valueOf("2020-12-10 15:00:00"))
                .bookingStatusCode(bookingStatusCode)
                .build();

        bookingStatusCode.getBookingList().add(booking);


        List<BookingStatusCode> bookingStatusCodes = new ArrayList<>();
        bookingStatusCodes.add(bookingStatusCode);


        when(bookingStatusCodeRepository.findAll()).thenReturn(bookingStatusCodes);

        //When
        List<BookingStatusCode> founded = bookingStatusCodeService.findAll();
        BookingStatusCode foundedStatusCode = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),foundedStatusCode.getId());
        assertEquals("IN RENT",foundedStatusCode.getDescription());
        assertEquals(1, foundedStatusCode.getBookingList().size());
        assertEquals(Long.valueOf(1),foundedStatusCode.getBookingList().get(0).getId());
        assertEquals(BigDecimal.valueOf(600),foundedStatusCode.getBookingList().get(0).getTotalCost());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),
                foundedStatusCode.getBookingList().get(0).getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),
                foundedStatusCode.getBookingList().get(0).getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getCreatedDate());
    }

    @Test
    public void findByID() {
    }

    @Test
    public void save() {
    }

    @Test
    public void deleteById() {
    }
}