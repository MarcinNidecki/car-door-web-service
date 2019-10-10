package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.repository.BookingStatusCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingStatusCodeServiceTest {

    @InjectMocks
    private BookingStatusCodeService bookingStatusCodeService;
    @Mock
    private BookingStatusCodeRepository bookingStatusCodeRepository;

    public BookingStatusCode getBookingStatusCode() {
        BookingStatusCode bookingStatusCode =  new BookingStatusCode(1L,"IN RENT");
        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .totalCost(BigDecimal.valueOf(600))
                .startDate(Timestamp.valueOf("2020-12-11 15:00:00"))
                .returnDate(Timestamp.valueOf("2020-12-13 15:00:00"))
                .createdDate(Timestamp.valueOf("2020-12-10 15:00:00"))
                .bookingStatusCode(bookingStatusCode)
                .build();

        bookingStatusCode.getBookingList().add(booking);
        return bookingStatusCode;
    }

    @Test
    public void shouldFindAllBookingStatusCode() {
        //Given
        List<BookingStatusCode> bookingStatusCodes = new ArrayList<>();
        bookingStatusCodes.add(getBookingStatusCode());

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
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getCreatedDate());
    }

    @Test
    public void shouldFindById() {
        //Given
        Optional<BookingStatusCode> bookingStatusCode = Optional.of(getBookingStatusCode());

        when(bookingStatusCodeRepository.findById(anyLong())).thenReturn(bookingStatusCode);

        //When
        BookingStatusCode foundedStatusCode = bookingStatusCodeService.findById(1L);

        //Then
        assertEquals(Long.valueOf(1),foundedStatusCode.getId());
        assertEquals("IN RENT",foundedStatusCode.getDescription());
        assertEquals(1, foundedStatusCode.getBookingList().size());
        assertEquals(Long.valueOf(1),foundedStatusCode.getBookingList().get(0).getId());
        assertEquals(BigDecimal.valueOf(600),foundedStatusCode.getBookingList().get(0).getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),
                foundedStatusCode.getBookingList().get(0).getCreatedDate());
    }

    @Test
    public void shouldFindEmptyOptionalOfBookingStatusCode() {
        //Given

        when(bookingStatusCodeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        BookingStatusCode foundedStatusCode = bookingStatusCodeService.findById(1L);

        //Then
        assertEquals( foundedStatusCode, new BookingStatusCode());

    }

    @Test
    public void shouldSaveBookingStatusCode() {
        //Given
        BookingStatusCode bookingStatusCode = getBookingStatusCode();

        when(bookingStatusCodeRepository.save(bookingStatusCode)).thenReturn(bookingStatusCode);

        //When
        BookingStatusCode savedCode = bookingStatusCodeService.save(bookingStatusCode);

        //Then
        assertEquals(Long.valueOf(1),savedCode.getId());
        assertEquals("IN RENT",savedCode.getDescription());
        assertEquals(1, savedCode.getBookingList().size());
        assertEquals(Long.valueOf(1),savedCode.getBookingList().get(0).getId());
        assertEquals(BigDecimal.valueOf(600),savedCode.getBookingList().get(0).getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),
                savedCode.getBookingList().get(0).getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),
                savedCode.getBookingList().get(0).getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),
                savedCode.getBookingList().get(0).getCreatedDate());
    }

    @Test
    public void ShouldDeleteById() {
        //Given
        doNothing().when(bookingStatusCodeRepository).deleteById(anyLong());

        //When
        bookingStatusCodeService.deleteById(1L);

        //Then
        verify(bookingStatusCodeRepository, times(1)).deleteById(1L);
    }
}