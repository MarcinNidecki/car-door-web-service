package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.repository.BookingExtrasRepository;
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
import static org.mockito.Mockito.times;
@RunWith(SpringJUnit4ClassRunner.class)
public class BookingExtrasServiceTest {

    @MockBean
    private BookingExtrasRepository extrasRepository;
    @InjectMocks
    private BookingExtrasService extrasService;


    public BookingExtras getExtras() {
        BookingExtras extras = new BookingExtras(1L,"TV","32'", BigDecimal.valueOf(100), "static/img/icon.jpg", new ArrayList<>());

        BookingExtrasItem bookingExtrasItem = new BookingExtrasItem(1L,5L, BigDecimal.valueOf(500), extras);
        extras.getBookingExtrasItems().add(bookingExtrasItem);
          return  extras;
    }


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllExtras() {
        //Given
        List<BookingExtras> extrasList = new ArrayList<>();
        BookingExtras bookingExtras = getExtras();
        extrasList.add(bookingExtras);

        when(extrasRepository.findAll()).thenReturn(extrasList);

        //When
        List<BookingExtras> founded = extrasService.findAll();
        BookingExtras foundedExtras = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),foundedExtras.getId());
        assertEquals("TV",foundedExtras.getName());
        assertEquals("32'",foundedExtras.getDescription());
        assertEquals(BigDecimal.valueOf(100),foundedExtras.getPrice());
        assertEquals("static/img/icon.jpg",foundedExtras.getIconPath());
        assertEquals(Long.valueOf(5),foundedExtras.getBookingExtrasItems().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(500), foundedExtras.getBookingExtrasItems().get(0).getValue());
    }

    @Test
    public void shouldSaveBookingExtras() {
        //Given
        BookingExtras bookingExtras = getExtras();

        when(extrasRepository.save(bookingExtras)).thenReturn(bookingExtras);

        //When
        BookingExtras founded = extrasService.save(bookingExtras);

        //Then
        assertThat(bookingExtras, is(equalTo(founded)));
        assertEquals(Long.valueOf(1),founded.getId());
        assertEquals("TV",founded.getName());
        assertEquals("32'",founded.getDescription());
        assertEquals(BigDecimal.valueOf(100),founded.getPrice());
        assertEquals("static/img/icon.jpg",founded.getIconPath());
        assertEquals(Long.valueOf(5),founded.getBookingExtrasItems().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(500), founded.getBookingExtrasItems().get(0).getValue());
    }

    @Test
    public void shouldFindOptionalOfBookingExtras() {
        //Given
        BookingExtras bookingExtras = getExtras();

        when(extrasRepository.findById(bookingExtras.getId())).thenReturn(Optional.of(bookingExtras));

        //When
        Optional<BookingExtras> founded = extrasService.findById(bookingExtras.getId());

        //Then
        assertTrue(founded.isPresent());
        assertEquals(Long.valueOf(1),founded.get().getId());
        assertEquals("TV",founded.get().getName());
        assertEquals("32'",founded.get().getDescription());
        assertEquals(BigDecimal.valueOf(100),founded.get().getPrice());
        assertEquals("static/img/icon.jpg",founded.get().getIconPath());
        assertEquals(Long.valueOf(5),founded.get().getBookingExtrasItems().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(500), founded.get().getBookingExtrasItems().get(0).getValue());

    }
    @Test
    public void shouldFindOptionalEmpty() {
        //Given
        when(extrasRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<BookingExtras> founded = extrasService.findById(2L);

        //Then
        assertFalse(founded.isPresent());
    }


    @Test
    public void deleteById() {
        //Given
        doNothing().when(extrasRepository).deleteById(anyLong());

        //When
        extrasService.deleteById(1L);

        //Then
        verify(extrasRepository, times(1)).deleteById(1L);
    }

}