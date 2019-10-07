package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.repository.BookingExtrasItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookingExtrasItemServiceTest {


    @MockBean
    private BookingExtrasItemRepository extrasItemRepository;
    @InjectMocks
    private BookingExtrasItemService extrasItemService;


    public BookingExtrasItem getExtrasItem() {
        BookingExtras extras = new BookingExtras(1L,"TV","32'",BigDecimal.valueOf(100), "static/img/icon.jpg", new ArrayList<>());
        BookingExtrasItem bookingExtrasItem = new BookingExtrasItem(1L,5L, BigDecimal.valueOf(500), extras);
        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .totalCost(BigDecimal.valueOf(500))
                .startDate(Timestamp.valueOf("2019-08-10 21:44:22"))
                .returnDate(Timestamp.valueOf("2019-08-12 21:44:22"))
                .bookingExtrasList(new ArrayList<>())
                .build();
        booking.getBookingExtrasList().add(bookingExtrasItem);
        bookingExtrasItem.setBooking(booking);

        return  bookingExtrasItem;
    }


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllExtrasItem() {
        //Given
        List<BookingExtrasItem> extrasList = new ArrayList<>();
        BookingExtrasItem bookingExtrasItem = getExtrasItem();
        extrasList.add(bookingExtrasItem);

        when(extrasItemRepository.findAll()).thenReturn(extrasList);

        //When
        List<BookingExtrasItem> founded = extrasItemService.findAll();
        BookingExtrasItem foundedExtrasItem = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),foundedExtrasItem.getId());
        assertEquals(Long.valueOf(5),foundedExtrasItem.getQuantity());
        assertEquals(BigDecimal.valueOf(500), foundedExtrasItem.getValue());
        assertEquals(Long.valueOf(1),foundedExtrasItem.getBookingExtras().getId());
        assertEquals("TV",foundedExtrasItem.getBookingExtras().getName());
        assertEquals("32'",foundedExtrasItem.getBookingExtras().getDescription());
        assertEquals(BigDecimal.valueOf(100),foundedExtrasItem.getBookingExtras().getPrice());
        assertEquals("static/img/icon.jpg",foundedExtrasItem.getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(1),foundedExtrasItem.getBooking().getId());
        assertEquals(BigDecimal.valueOf(500),foundedExtrasItem.getBooking().getTotalCost());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),foundedExtrasItem.getBooking().getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),foundedExtrasItem.getBooking().getReturnDate());

    }

    @Test
    public void shouldSaveBookingExtrasItem() {
        //Given
        BookingExtrasItem bookingExtrasItem = getExtrasItem();

        when(extrasItemRepository.save(bookingExtrasItem)).thenReturn(bookingExtrasItem);

        //When
        BookingExtrasItem founded = extrasItemService.save(bookingExtrasItem);

        //Then

        assertThat(bookingExtrasItem, is(equalTo(founded)));
        assertEquals(Long.valueOf(1),founded.getId());
        assertEquals(Long.valueOf(5),founded.getQuantity());
        assertEquals(BigDecimal.valueOf(500), founded.getValue());
        assertEquals(Long.valueOf(1),founded.getBookingExtras().getId());
        assertEquals("TV",founded.getBookingExtras().getName());
        assertEquals("32'",founded.getBookingExtras().getDescription());
        assertEquals(BigDecimal.valueOf(100),founded.getBookingExtras().getPrice());
        assertEquals("static/img/icon.jpg",founded.getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(1),founded.getBooking().getId());
        assertEquals(BigDecimal.valueOf(500),founded.getBooking().getTotalCost());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),founded.getBooking().getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),founded.getBooking().getReturnDate());
    }

    @Test
    public void shouldFindOptionalOfBookingExtrasItem() {
        //Given
        BookingExtrasItem bookingExtrasItem = getExtrasItem();

        when(extrasItemRepository.findById(bookingExtrasItem.getId())).thenReturn(Optional.of(bookingExtrasItem));

        //When
        Optional<BookingExtrasItem> founded = extrasItemService.findById(bookingExtrasItem.getId());

        //Then
        assertTrue(founded.isPresent());
        assertThat(bookingExtrasItem, is(equalTo(founded.get())));
        assertEquals(Long.valueOf(1),founded.get().getId());
        assertEquals(Long.valueOf(5),founded.get().getQuantity());
        assertEquals(BigDecimal.valueOf(500), founded.get().getValue());
        assertEquals(Long.valueOf(1),founded.get().getBookingExtras().getId());
        assertEquals("TV",founded.get().getBookingExtras().getName());
        assertEquals("32'",founded.get().getBookingExtras().getDescription());
        assertEquals(BigDecimal.valueOf(100),founded.get().getBookingExtras().getPrice());
        assertEquals("static/img/icon.jpg",founded.get().getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(1),founded.get().getBooking().getId());
        assertEquals(BigDecimal.valueOf(500),founded.get().getBooking().getTotalCost());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),founded.get().getBooking().getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),founded.get().getBooking().getReturnDate());

    }
    @Test
    public void shouldFindOptionalEmpty() {
        //Given
        when(extrasItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<BookingExtrasItem> founded = extrasItemService.findById(2L);

        //Then
        assertFalse(founded.isPresent());
    }


    @Test
    public void deleteById() {
        //Given
        doNothing().when(extrasItemRepository).deleteById(anyLong());

        //When
        extrasItemService.deleteById(1L);

        //Then
        verify(extrasItemRepository, times(1)).deleteById(1L);
    }
}