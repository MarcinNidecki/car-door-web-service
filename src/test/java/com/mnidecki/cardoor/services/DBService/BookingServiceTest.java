package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.repository.BookingRepository;
import com.mnidecki.cardoor.services.SimpleEmailService;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private BookingExtrasService extrasService;
    @Mock
    private CommentService commentService;

    public Booking getBooking() {
        User user = new User.UserBuilder()
                .id(1L)
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .password("password")
                .status(1)
                .username("username")
                .addressLine1("address line 1")
                .addressLine2("address line 2")
                .country("Poland")
                .state("Slaskie")
                .zipCode("44-444")
                .build();

        Car car = new Car.CarBuilder()
                .id(1L)
                .registration("WWA 123456")
                .vehicleStatus("RENT")
                .price(BigDecimal.valueOf(250))
                .build();

        Locationn location = new Locationn(1L,"Poland","Katowice","Katowice Airport","address line 1","address line 2",
                "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");

        BookingExtras extras = new BookingExtras(1L,"BOOSTER","RED MIDDLE",BigDecimal.valueOf(50), "static/img" +
                "/icon.jpg",
                new ArrayList<>());
        BookingExtrasItem bookingExtrasItem = new BookingExtrasItem(1L,2L, BigDecimal.valueOf(100), extras);
        List<BookingExtrasItem> extrasItemList = new ArrayList<>();
        extrasItemList.add(bookingExtrasItem);

        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .location(location)
                .totalCost(BigDecimal.valueOf(600))
                .startDate(Timestamp.valueOf("2020-12-11 15:00:00"))
                .returnDate(Timestamp.valueOf("2020-12-13 15:00:00"))
                .createdDate(Timestamp.valueOf("2020-12-10 15:00:00"))
                .bookingExtrasList(extrasItemList)
                .build();

        extrasItemList.get(0).setBooking(booking);

        return booking;

    }

    @Test
    public void shouldFindAllBookings() {
        //Given
        List<Booking> bookingsList = new ArrayList<>();
        Booking booking = getBooking();
        bookingsList.add(booking);

        when(bookingRepository.findAll()).thenReturn(bookingsList);

        //When
        List<Booking> founded = bookingService.findAll();
        Booking foundedBooking = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),foundedBooking.getId());
        assertEquals(BigDecimal.valueOf(600),foundedBooking.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),foundedBooking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),foundedBooking.getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals("lastname",foundedBooking.getUser().getLastname());
        assertEquals("firstname",foundedBooking.getUser().getFirstname());
        assertEquals("email",foundedBooking.getUser().getEmail());
        assertEquals("password",foundedBooking.getUser().getPassword());
        assertEquals(Integer.valueOf(1),foundedBooking.getUser().getStatus());
        assertEquals("username",foundedBooking.getUser().getUsername());
        assertEquals("address line 1",foundedBooking.getUser().getAddressLine1());
        assertEquals("address line 2",foundedBooking.getUser().getAddressLine2());
        assertEquals("Poland",foundedBooking.getUser().getCountry());
        assertEquals("Slaskie",foundedBooking.getUser().getState());
        assertEquals("44-444",foundedBooking.getUser().getZipCode());
        assertEquals(Long.valueOf(1),foundedBooking.getCar().getId());
        assertEquals("WWA 123456",foundedBooking.getCar().getRegistration());
        assertEquals("RENT",foundedBooking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250),foundedBooking.getCar().getPrice());
        assertEquals(Long.valueOf(1),foundedBooking.getLocation().getId());
        assertEquals("Poland",foundedBooking.getLocation().getCountry());
        assertEquals("Katowice",foundedBooking.getLocation().getCity());
        assertEquals("Katowice Airport",foundedBooking.getLocation().getLocationName());
        assertEquals("address line 1",foundedBooking.getLocation().getAddressLine());
        assertEquals("address line 2",foundedBooking.getLocation().getAddressLine2());
        assertEquals("Terminal 2, office next to parking",foundedBooking.getLocation().getPickUpInstructions());
        assertEquals("44-444",foundedBooking.getLocation().getPostalCode());
        assertEquals("email@email.pl",foundedBooking.getLocation().getEmail());
        assertEquals("555555555",foundedBooking.getLocation().getPhone());
        assertEquals("Mon - Fri 10-16",foundedBooking.getLocation().getOpeningHours());
        assertEquals(1,foundedBooking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1),foundedBooking.getBookingExtrasList().get(0).getId());
        assertEquals(Long.valueOf(2),foundedBooking.getBookingExtrasList().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(100),foundedBooking.getBookingExtrasList().get(0).getValue());

    }

    @Test
    public void shouldFindByIdOptionalOfBooking() {
        //Given
        Optional<Booking> bookingsList = Optional.of(getBooking());

        when(bookingRepository.findById(anyLong())).thenReturn(bookingsList);

        //When
        Booking foundedBooking = bookingService.findById(1L);

        //Then
        assertEquals(Long.valueOf(1L), foundedBooking.getId());
        assertEquals(BigDecimal.valueOf(600),foundedBooking.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),foundedBooking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),foundedBooking.getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals("lastname",foundedBooking.getUser().getLastname());
        assertEquals("firstname",foundedBooking.getUser().getFirstname());
        assertEquals("email",foundedBooking.getUser().getEmail());
        assertEquals("password",foundedBooking.getUser().getPassword());
        assertEquals(Integer.valueOf(1), foundedBooking.getUser().getStatus());
        assertEquals("username",foundedBooking.getUser().getUsername());
        assertEquals("address line 1",foundedBooking.getUser().getAddressLine1());
        assertEquals("address line 2",foundedBooking.getUser().getAddressLine2());
        assertEquals("Poland",foundedBooking.getUser().getCountry());
        assertEquals("Slaskie",foundedBooking.getUser().getState());
        assertEquals("44-444",foundedBooking.getUser().getZipCode());
        assertEquals(Long.valueOf(1),foundedBooking.getCar().getId());
        assertEquals("WWA 123456",foundedBooking.getCar().getRegistration());
        assertEquals("RENT",foundedBooking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250),foundedBooking.getCar().getPrice());
        assertEquals(Long.valueOf(1),foundedBooking.getLocation().getId());
        assertEquals("Poland",foundedBooking.getLocation().getCountry());
        assertEquals("Katowice",foundedBooking.getLocation().getCity());
        assertEquals("Katowice Airport",foundedBooking.getLocation().getLocationName());
        assertEquals("address line 1",foundedBooking.getLocation().getAddressLine());
        assertEquals("address line 2",foundedBooking.getLocation().getAddressLine2());
        assertEquals("Terminal 2, office next to parking",foundedBooking.getLocation().getPickUpInstructions());
        assertEquals("44-444",foundedBooking.getLocation().getPostalCode());
        assertEquals("email@email.pl",foundedBooking.getLocation().getEmail());
        assertEquals("555555555",foundedBooking.getLocation().getPhone());
        assertEquals("Mon - Fri 10-16",foundedBooking.getLocation().getOpeningHours());
        assertEquals(1,foundedBooking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1),foundedBooking.getBookingExtrasList().get(0).getId());
        assertEquals(Long.valueOf(2),foundedBooking.getBookingExtrasList().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(100),foundedBooking.getBookingExtrasList().get(0).getValue());
    }

    @Test
    public void shouldReturnEmptyBooking() {
        //Given
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Booking founded = bookingService.findById(2L);

        //Then
        assertEquals(new Booking(), founded);

    }

    @Test
    public void  save() {
        Booking booking = getBooking();
        when(simpleEmailService.sendBookingConfirmation(booking)).thenReturn(true);
        when(bookingRepository.save(booking)).thenReturn(booking);
        //When
        Booking foundedBooking = bookingService.save(booking);

        //Then
        assertEquals(Long.valueOf(1),foundedBooking.getId());
        assertEquals(BigDecimal.valueOf(600),foundedBooking.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),foundedBooking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),foundedBooking.getReturnDate());
        assertEquals("lastname",foundedBooking.getUser().getLastname());
        assertEquals("firstname",foundedBooking.getUser().getFirstname());
        assertEquals("email",foundedBooking.getUser().getEmail());
        assertEquals("password",foundedBooking.getUser().getPassword());
        assertEquals(Integer.valueOf(1),foundedBooking.getUser().getStatus());
        assertEquals("username",foundedBooking.getUser().getUsername());
        assertEquals("address line 1",foundedBooking.getUser().getAddressLine1());
        assertEquals("address line 2",foundedBooking.getUser().getAddressLine2());
        assertEquals("Poland",foundedBooking.getUser().getCountry());
        assertEquals("Slaskie",foundedBooking.getUser().getState());
        assertEquals("44-444",foundedBooking.getUser().getZipCode());
        assertEquals(Long.valueOf(1),foundedBooking.getCar().getId());
        assertEquals("WWA 123456",foundedBooking.getCar().getRegistration());
        assertEquals("RENT",foundedBooking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250),foundedBooking.getCar().getPrice());
        assertEquals(Long.valueOf(1),foundedBooking.getLocation().getId());
        assertEquals("Poland",foundedBooking.getLocation().getCountry());
        assertEquals("Katowice",foundedBooking.getLocation().getCity());
        assertEquals("Katowice Airport",foundedBooking.getLocation().getLocationName());
        assertEquals("address line 1",foundedBooking.getLocation().getAddressLine());
        assertEquals("address line 2",foundedBooking.getLocation().getAddressLine2());
        assertEquals("Terminal 2, office next to parking",foundedBooking.getLocation().getPickUpInstructions());
        assertEquals("44-444",foundedBooking.getLocation().getPostalCode());
        assertEquals("email@email.pl",foundedBooking.getLocation().getEmail());
        assertEquals("555555555",foundedBooking.getLocation().getPhone());
        assertEquals("Mon - Fri 10-16",foundedBooking.getLocation().getOpeningHours());
        assertEquals(1,foundedBooking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1),foundedBooking.getBookingExtrasList().get(0).getId());
        assertEquals(Long.valueOf(2),foundedBooking.getBookingExtrasList().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(100),foundedBooking.getBookingExtrasList().get(0).getValue());
    }

    @Test
    public void deleteById() {
        //Given
        doNothing().when(bookingRepository).deleteById(anyLong());

        //When
        bookingService.deleteById(1L);

        //Then
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void ShouldReturnAllBookingByCarId() {
        List<Booking> bookingsList = new ArrayList<>();
        Booking booking = getBooking();
        bookingsList.add(booking);
        Car car = booking.getCar();

        when(bookingRepository.findAllByCar_Id(car.getId())).thenReturn(bookingsList);

        //When
        List<Booking> foundedList = bookingService.getAllBookingByCarId(car.getId());
        Booking foundedBooking = foundedList.get(0);

        //Then
        assertEquals(1, foundedList.size());
        assertEquals(Long.valueOf(1),foundedBooking.getId());
        assertEquals(BigDecimal.valueOf(600),foundedBooking.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"),foundedBooking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"),foundedBooking.getReturnDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals(Timestamp.valueOf("2020-12-10 15:00:00"),foundedBooking.getCreatedDate());
        assertEquals("lastname",foundedBooking.getUser().getLastname());
        assertEquals("firstname",foundedBooking.getUser().getFirstname());
        assertEquals("email",foundedBooking.getUser().getEmail());
        assertEquals("password",foundedBooking.getUser().getPassword());
        assertEquals(Integer.valueOf(1),foundedBooking.getUser().getStatus());
        assertEquals("username",foundedBooking.getUser().getUsername());
        assertEquals("address line 1",foundedBooking.getUser().getAddressLine1());
        assertEquals("address line 2",foundedBooking.getUser().getAddressLine2());
        assertEquals("Poland",foundedBooking.getUser().getCountry());
        assertEquals("Slaskie",foundedBooking.getUser().getState());
        assertEquals("44-444",foundedBooking.getUser().getZipCode());
        assertEquals(Long.valueOf(1),foundedBooking.getCar().getId());
        assertEquals("WWA 123456",foundedBooking.getCar().getRegistration());
        assertEquals("RENT",foundedBooking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250),foundedBooking.getCar().getPrice());
        assertEquals(Long.valueOf(1),foundedBooking.getLocation().getId());
        assertEquals("Poland",foundedBooking.getLocation().getCountry());
        assertEquals("Katowice",foundedBooking.getLocation().getCity());
        assertEquals("Katowice Airport",foundedBooking.getLocation().getLocationName());
        assertEquals("address line 1",foundedBooking.getLocation().getAddressLine());
        assertEquals("address line 2",foundedBooking.getLocation().getAddressLine2());
        assertEquals("Terminal 2, office next to parking",foundedBooking.getLocation().getPickUpInstructions());
        assertEquals("44-444",foundedBooking.getLocation().getPostalCode());
        assertEquals("email@email.pl",foundedBooking.getLocation().getEmail());
        assertEquals("555555555",foundedBooking.getLocation().getPhone());
        assertEquals("Mon - Fri 10-16",foundedBooking.getLocation().getOpeningHours());
        assertEquals(1,foundedBooking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1),foundedBooking.getBookingExtrasList().get(0).getId());
        assertEquals(Long.valueOf(2),foundedBooking.getBookingExtrasList().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(100),foundedBooking.getBookingExtrasList().get(0).getValue());


    }

    @Test
    public void shouldCountBookingDays() {
        //Given
        Booking booking = getBooking();
        //When
        Long bookingDays = bookingService.countBookingDays(booking.getStartDate(),booking.getReturnDate());
        //Then
        assertEquals(Long.valueOf(2),bookingDays);
    }

    @Test
    public void shouldAddExtraDayIfReturnHourIsAfterStartHour() {
        //Given
        Booking booking = getBooking();
        booking.setReturnDate(Timestamp.valueOf("2020-12-13 16:00:00"));
        //When
        Long bookingDays = bookingService.countBookingDays(booking.getStartDate(),booking.getReturnDate());
        //Then
        assertEquals(Long.valueOf(3),bookingDays);
    }

    @Test
    public void setAllBookingCostFields() {
        //Given
        Booking booking = getBooking();
        booking.setTotalCost(BigDecimal.ZERO);
        booking.getBookingExtrasList().get(0).setValue(BigDecimal.ZERO);

        //When
        Booking bookingWithCost = bookingService.setAllBookingCostFields(booking);

        //Then
        assertEquals(BigDecimal.valueOf(600),bookingWithCost.getTotalCost());
        assertEquals(BigDecimal.valueOf(100),bookingWithCost.getBookingExtrasList().get(0).getValue());
    }

    @Test
    public void prepareEmptyExtrasItemList() {
        //Given
        BookingExtras extras = new BookingExtras(1L,"GPS","5'", BigDecimal.valueOf(100), "static/img/icon.jpg",
                new ArrayList<>());
        BookingExtras extras2 = new BookingExtras(2L,"Booster","RED", BigDecimal.valueOf(50), "static/img/icon2.jpg",
                new ArrayList<>());
        List<BookingExtras> extrasList = new ArrayList<>();
        extrasList.add(extras);
        extrasList.add(extras2);

        when(extrasService.findAll()).thenReturn(extrasList);
        //When
        List<BookingExtrasItem> extrasItemList = bookingService.prepareEmptyExtrasItemList();

        //Then
        assertEquals(2, extrasItemList.size());
        assertEquals(BigDecimal.valueOf(0), extrasItemList.get(0).getValue());
        assertEquals(BigDecimal.valueOf(0), extrasItemList.get(1).getValue());
        assertEquals(Long.valueOf(0), extrasItemList.get(0).getQuantity());
        assertEquals(Long.valueOf(0), extrasItemList.get(1).getQuantity());
        assertEquals(Long.valueOf(1), extrasItemList.get(0).getBookingExtras().getId());
        assertEquals(Long.valueOf(2), extrasItemList.get(1).getBookingExtras().getId());
        assertEquals("GPS", extrasItemList.get(0).getBookingExtras().getName());
        assertEquals("Booster", extrasItemList.get(1).getBookingExtras().getName());
        assertEquals("5'", extrasItemList.get(0).getBookingExtras().getDescription());
        assertEquals("RED", extrasItemList.get(1).getBookingExtras().getDescription());
        assertEquals("static/img/icon.jpg", extrasItemList.get(0).getBookingExtras().getIconPath());
        assertEquals("static/img/icon2.jpg", extrasItemList.get(1).getBookingExtras().getIconPath());
        assertEquals(BigDecimal.valueOf(100), extrasItemList.get(0).getBookingExtras().getPrice());
        assertEquals(BigDecimal.valueOf(50), extrasItemList.get(1).getBookingExtras().getPrice());
    }

    @Test
    public void shouldReturnEmptyList() {
        //Given
        when(extrasService.findAll()).thenReturn(new ArrayList<>());
        //When
        List<BookingExtrasItem> extrasItemList = bookingService.prepareEmptyExtrasItemList();

        //Then
        assertEquals(0, extrasItemList.size());
        assertTrue(extrasItemList.isEmpty());

    }

    @Test
    public void shouldReturnCorrectTimestamp() {
        //Given
        String startDate = "2018-12-12";
        String startHour = "22:20:00";

        //When
        Timestamp timestamp = bookingService.stringTimeToTimestampConverter(startDate,startHour);
        Timestamp exceptedTime = Timestamp.valueOf("2018-12-12 22:20:00");

        //Then
        assertEquals(exceptedTime,timestamp);
    }
    @Test
    public void shouldReturnCorrectTimestampWithTimeWithOutNanoSecond() {
        //Given
        String startDate = "2018-12-12";
        String startHour = "22:20";

        //When
        Timestamp timestamp = bookingService.stringTimeToTimestampConverter(startDate,startHour);
        Timestamp exceptedTime = Timestamp.valueOf("2018-12-12 22:20:00");

        //Then
        assertEquals(exceptedTime,timestamp);
    }


    @Test
    public void shouldCountHappyClients() {
        //Given
        int commentsWithRatingLessThen2= 2;
        List<Booking> bookingList  = new ArrayList<>();
        bookingList.add(getBooking());
        bookingList.add(getBooking());
        bookingList.add(getBooking());

       when(bookingService.findAll()).thenReturn(bookingList);
       when(commentService.countAllByRatingLessThan2()).thenReturn(commentsWithRatingLessThen2);

       //When
        int happyClient = bookingService.countHappyClients();
        System.out.println(happyClient);
        //Then
        assertEquals(1,happyClient);
    }
}

