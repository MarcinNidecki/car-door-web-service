package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.dto.BookingExtrasItemDto;
import com.mnidecki.cardoor.services.DBService.BookingExtrasService;
import com.mnidecki.cardoor.services.DBService.BookingService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingExtrasItemMapperTest {

    @InjectMocks
    private BookingExtrasItemMapper mapper;
    @Mock
    private BookingService bookingService;
    @Mock
    private BookingExtrasService bookingExtrasService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public BookingExtrasItem getExtrasItem() {

        BookingExtras extras = getBookingExtras();
        BookingExtrasItem bookingExtrasItem = new BookingExtrasItem(3L,5L, BigDecimal.valueOf(500), extras);
        Booking booking = getBooking();
        booking.getBookingExtrasList().add(bookingExtrasItem);
        bookingExtrasItem.setBooking(booking);

        return  bookingExtrasItem;
    }

    private BookingExtras getBookingExtras() {
        return new BookingExtras(1L,"TV","32'", BigDecimal.valueOf(100), "static/img/icon.jpg", new ArrayList<>());
    }

    private Booking getBooking() {
        return new Booking.BookingBuilder()
                    .id(2L)
                    .totalCost(BigDecimal.valueOf(500))
                    .startDate(Timestamp.valueOf("2019-08-10 21:44:22"))
                    .returnDate(Timestamp.valueOf("2019-08-12 21:44:22"))
                    .bookingExtrasList(new ArrayList<>())
                    .build();
    }

    public BookingExtrasItemDto getExtrasItemDto() {
        return new BookingExtrasItemDto(3L,"TV","32'", 5L,
                BigDecimal.valueOf(500), BigDecimal.valueOf(100), 1L, 2L, "static/img/icon.jpg");
    }

    @Test
    public void ShouldMapToBookingExtrasItemDto() {
        //Given
        BookingExtrasItem extrasItem = getExtrasItem();

        //When
        BookingExtrasItemDto extrasItemDto = mapper.mapToBookingExtrasItemDto(extrasItem);

        //Then
        assertEquals(Long.valueOf(3),extrasItemDto.getId());
        assertEquals("TV",extrasItemDto.getName());
        assertEquals("32'",extrasItemDto.getDescription());
        assertEquals(Long.valueOf(5),extrasItemDto.getQuantity());
        assertEquals(BigDecimal.valueOf(500), extrasItemDto.getTotalValue());
        assertEquals(BigDecimal.valueOf(100), extrasItemDto.getPrice());
        assertEquals(Long.valueOf(1),extrasItemDto.getBookingExtrasId());
        assertEquals("static/img/icon.jpg",extrasItemDto.getIconPath());
        assertEquals(Long.valueOf(2),extrasItemDto.getBookingId());
    }

    @Test
    public void ShouldMapToBookingExtrasItemDtoWhenBookingIsNull() {
        //Given
        BookingExtrasItem extrasItem = getExtrasItem();
        extrasItem.setBooking(null);

        //When
        BookingExtrasItemDto extrasItemDto = mapper.mapToBookingExtrasItemDto(extrasItem);

        //Then
        assertEquals(Long.valueOf(3),extrasItemDto.getId());
        assertEquals("TV",extrasItemDto.getName());
        assertEquals("32'",extrasItemDto.getDescription());
        assertEquals(Long.valueOf(5),extrasItemDto.getQuantity());
        assertEquals(BigDecimal.valueOf(500), extrasItemDto.getTotalValue());
        assertEquals(BigDecimal.valueOf(100), extrasItemDto.getPrice());
        assertEquals(Long.valueOf(1),extrasItemDto.getBookingExtrasId());
        assertEquals("static/img/icon.jpg",extrasItemDto.getIconPath());
    }

    @Test
    public void ShouldMapToBookingExtrasItemDtoList() {
        //Given
        BookingExtrasItem extrasItem = getExtrasItem();
        List<BookingExtrasItem> extrasItemList = new ArrayList<>();
        extrasItemList.add(extrasItem);
        //When
        List<BookingExtrasItemDto> foundedList = mapper.mapToBookingExtrasItemDtoList(extrasItemList);
        BookingExtrasItemDto extrasItemDto = foundedList.get(0);

        //Then
        assertEquals(1,foundedList.size());
        assertEquals(Long.valueOf(3),extrasItemDto.getId());
        assertEquals("TV",extrasItemDto.getName());
        assertEquals("32'",extrasItemDto.getDescription());
        assertEquals(Long.valueOf(5),extrasItemDto.getQuantity());
        assertEquals(BigDecimal.valueOf(500), extrasItemDto.getTotalValue());
        assertEquals(BigDecimal.valueOf(100), extrasItemDto.getPrice());
        assertEquals(Long.valueOf(1),extrasItemDto.getBookingExtrasId());
        assertEquals("static/img/icon.jpg",extrasItemDto.getIconPath());
        assertEquals(Long.valueOf(2),extrasItemDto.getBookingId());
    }

    @Test
    public void ShouldMapToBookingExtrasItem() {
        //Given
        BookingExtrasItemDto extrasItemDto = getExtrasItemDto();
        Booking booking = getBooking();
        BookingExtras bookingExtras = getBookingExtras();

        when(bookingService.findById(booking.getId())).thenReturn(booking);
        when(bookingExtrasService.findById(bookingExtras.getId())).thenReturn(bookingExtras);

        //When
        BookingExtrasItem bookingExtrasItem = mapper.mapToBookingExtrasItem(extrasItemDto);

        //Then
        assertEquals(Long.valueOf(3),bookingExtrasItem.getId());
        assertEquals("TV",bookingExtrasItem.getBookingExtras().getName());
        assertEquals("32'",bookingExtrasItem.getBookingExtras().getDescription());
        assertEquals(Long.valueOf(5),bookingExtrasItem.getQuantity());
        assertEquals(BigDecimal.valueOf(500), bookingExtrasItem.getValue());
        assertEquals(BigDecimal.valueOf(100), bookingExtrasItem.getBookingExtras().getPrice());
        assertEquals(Long.valueOf(1),bookingExtrasItem.getBookingExtras().getId());
        assertEquals("static/img/icon.jpg",bookingExtrasItem.getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(2),bookingExtrasItem.getBooking().getId());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),bookingExtrasItem.getBooking().getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),bookingExtrasItem.getBooking().getReturnDate());
    }

    @Test
    public void ShouldMapToBookingExtrasItemWhenBookingIdIsNull() {
        //Given
        BookingExtrasItemDto extrasItemDto = getExtrasItemDto();
        extrasItemDto.setBookingId(null);
        BookingExtras bookingExtras = getBookingExtras();

        when(bookingExtrasService.findById(bookingExtras.getId())).thenReturn(bookingExtras);

        //When
        BookingExtrasItem bookingExtrasItem = mapper.mapToBookingExtrasItem(extrasItemDto);

        assertEquals(Long.valueOf(3),bookingExtrasItem.getId());
        assertEquals("TV",bookingExtrasItem.getBookingExtras().getName());
        assertEquals("32'",bookingExtrasItem.getBookingExtras().getDescription());
        assertEquals(Long.valueOf(5),bookingExtrasItem.getQuantity());
        assertEquals(BigDecimal.valueOf(500), bookingExtrasItem.getValue());
        assertEquals(BigDecimal.valueOf(100), bookingExtrasItem.getBookingExtras().getPrice());
        assertEquals(Long.valueOf(1),bookingExtrasItem.getBookingExtras().getId());
        assertEquals("static/img/icon.jpg",bookingExtrasItem.getBookingExtras().getIconPath());
        assertNull(bookingExtrasItem.getBooking());

    }

    @Test
    public void ShouldMapToBookingExtrasItemList() {
        //Given
        BookingExtrasItemDto extrasItemDto = getExtrasItemDto();
        Booking booking = getBooking();
        BookingExtras bookingExtras = getBookingExtras();
        List<BookingExtrasItemDto> extrasItemDtoList = new ArrayList<>();
        extrasItemDtoList.add(extrasItemDto);

        when(bookingService.findById(booking.getId())).thenReturn(booking);
        when(bookingExtrasService.findById(bookingExtras.getId())).thenReturn(bookingExtras);

        //When
        List<BookingExtrasItem> bookingExtrasItemList = mapper.mapToBookingExtrasItemList(extrasItemDtoList);
        BookingExtrasItem bookingExtrasItem = bookingExtrasItemList.get(0);
        //Then
        assertEquals(1,bookingExtrasItemList.size());
        assertEquals(Long.valueOf(3),bookingExtrasItem.getId());
        assertEquals("TV",bookingExtrasItem.getBookingExtras().getName());
        assertEquals("32'",bookingExtrasItem.getBookingExtras().getDescription());
        assertEquals(Long.valueOf(5),bookingExtrasItem.getQuantity());
        assertEquals(BigDecimal.valueOf(500), bookingExtrasItem.getValue());
        assertEquals(BigDecimal.valueOf(100), bookingExtrasItem.getBookingExtras().getPrice());
        assertEquals(Long.valueOf(1),bookingExtrasItem.getBookingExtras().getId());
        assertEquals("static/img/icon.jpg",bookingExtrasItem.getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(2),bookingExtrasItem.getBooking().getId());
        assertEquals(Timestamp.valueOf("2019-08-10 21:44:22"),bookingExtrasItem.getBooking().getStartDate());
        assertEquals(Timestamp.valueOf("2019-08-12 21:44:22"),bookingExtrasItem.getBooking().getReturnDate());

    }
}