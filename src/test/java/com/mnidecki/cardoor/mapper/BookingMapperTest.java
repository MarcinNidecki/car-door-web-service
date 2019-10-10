package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.BookingDto;
import com.mnidecki.cardoor.domain.dto.BookingExtrasItemDto;
import com.mnidecki.cardoor.services.DBService.BookingStatusCodeService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingMapperTest {

    @InjectMocks
    private BookingMapper bookingMapper;
    @Mock
    private UserService userService;
    @Mock
    private LocationService cityService;
    @Mock
    private CarService carService;
    @Mock
    private BookingStatusCodeService statusService;
    @Mock
    private BookingExtrasItemMapper bookingExtrasItemMapper;

    public Booking getBooking() {
        User user = getUser();
        Car car = getCar();
        Locationn location = getLocationn();
        List<BookingExtrasItem> extrasItemList = getBookingExtrasItemList();
        BookingStatusCode bookingStatusCode = new BookingStatusCode(4L,"IN RENT");
        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .user(user)
                .car(car)
                .location(location)
                .bookingStatusCode(bookingStatusCode)
                .totalCost(BigDecimal.valueOf(600))
                .startDate(Timestamp.valueOf("2020-12-11 15:00:00"))
                .returnDate(Timestamp.valueOf("2020-12-13 15:00:00"))
                .createdDate(Timestamp.valueOf("2020-12-10 15:00:00"))
                .bookingExtrasList(extrasItemList)
                .build();

        extrasItemList.get(0).setBooking(booking);

        return booking;

    }

    private List<BookingExtrasItem> getBookingExtrasItemList() {
        BookingExtras extras = getBookingExtras();
        BookingExtrasItem bookingExtrasItem = new BookingExtrasItem(1L,2L, BigDecimal.valueOf(200), extras);
        List<BookingExtrasItem> extrasItemList = new ArrayList<>();
        extrasItemList.add(bookingExtrasItem);
        return extrasItemList;
    }

    private User getUser() {
        return new User.UserBuilder()
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
                    .zipCode("44-244")
                    .build();
    }

    private BookingExtras getBookingExtras() {
        return new BookingExtras(8L,"BOOSTER","RED MIDDLE", BigDecimal.valueOf(100), "/img/icon.jpg",
                    new ArrayList<>());
    }

    private Locationn getLocationn() {
        return new Locationn(5L,"Poland","Katowice","Katowice Airport","address line 1","address line 2",
                    "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");
    }

    private Car getCar() {
        return new Car.CarBuilder()
                    .id(3L)
                    .registration("WWA 123456")
                    .vehicleStatus("RENT")
                    .price(BigDecimal.valueOf(250))
                    .build();
    }

    private BookingDto getBookingDto() {
        BookingDto bookingDto = new BookingDto(1L, 2L, 3L, 4L, 5L, BigDecimal.valueOf(600),
                Timestamp.valueOf("2020-12-11 15:00:00"), Timestamp.valueOf("2020-12-13 15:00:00"), new ArrayList<>());

        BookingExtrasItemDto extrasItemDto = new BookingExtrasItemDto(1L, "BOOSTER", "RED MIDDLE", 2L,
                BigDecimal.valueOf(200), BigDecimal.valueOf(100), 1L, 1L, "/img/icon.jpg");

        List<BookingExtrasItemDto> extrasItemDtoList = Collections.singletonList(extrasItemDto);
        bookingDto.setBookingExtrasList(extrasItemDtoList);
        return bookingDto;
    }


    @Test
    public void ShouldMapToBookingWhenBookingIdIsGiven() {
        //Given
        BookingDto bookingDto = new BookingDto(1L, 1L, 3L, 4L, 5L, BigDecimal.valueOf(600),
                Timestamp.valueOf("2020-12-11 15:00:00"), Timestamp.valueOf("2020-12-13 15:00:00"), new ArrayList<>());

        BookingExtrasItemDto extrasItemDto = new BookingExtrasItemDto(1L, "BOOSTER", "RED MIDDLE", 2L,
                BigDecimal.valueOf(200), BigDecimal.valueOf(100), 8L, 1L, "/img/icon.jpg");

        List<BookingExtrasItemDto> extrasItemDtoList = Collections.singletonList(extrasItemDto);
        bookingDto.setBookingExtrasList(extrasItemDtoList);

        User user = getUser();
        Car car = getCar();

        BookingStatusCode bookingStatusCode = new BookingStatusCode(4L, "IN RENT");
        Locationn locationn = getLocationn();
        List<BookingExtrasItem> extrasItemList = getBookingExtrasItemList();

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(carService.findById(car.getId())).thenReturn(car);
        when(statusService.findById(bookingStatusCode.getId())).thenReturn(bookingStatusCode);
        when(cityService.findById(locationn.getId())).thenReturn(locationn);
        when(bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList())).thenReturn(extrasItemList);

        //When
        Booking booking = bookingMapper.mapToBooking(bookingDto);

        //Then

        assertEquals(Long.valueOf(1), booking.getId());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"), booking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"), booking.getReturnDate());
        assertEquals(BigDecimal.valueOf(600), booking.getTotalCost());
        assertEquals(Long.valueOf(1), booking.getUser().getId());
        assertEquals("firstname", booking.getUser().getFirstname());
        assertEquals("lastname", booking.getUser().getLastname());
        assertEquals("email", booking.getUser().getEmail());
        assertEquals("password", booking.getUser().getPassword());
        assertEquals("username", booking.getUser().getUsername());
        assertEquals("address line 1", booking.getUser().getAddressLine1());
        assertEquals("address line 2", booking.getUser().getAddressLine2());
        assertEquals("Poland", booking.getUser().getCountry());
        assertEquals("Slaskie", booking.getUser().getState());
        assertEquals("44-244", booking.getUser().getZipCode());
        assertEquals("WWA 123456", booking.getCar().getRegistration());
        assertEquals("RENT", booking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250), booking.getCar().getPrice());
        assertEquals(Long.valueOf(3), booking.getCar().getId());
        assertEquals("IN RENT", booking.getBookingStatusCode().getDescription());
        assertEquals(Long.valueOf(4), booking.getBookingStatusCode().getId());
        assertEquals(Long.valueOf(5), booking.getLocation().getId());
        assertEquals("address line 1", booking.getLocation().getAddressLine());
        assertEquals("address line 2", booking.getLocation().getAddressLine2());
        assertEquals("Katowice", booking.getLocation().getCity());
        assertEquals("Poland", booking.getLocation().getCountry());
        assertEquals("Terminal 2, office next to parking", booking.getLocation().getPickUpInstructions());
        assertEquals("Katowice Airport", booking.getLocation().getLocationName());
        assertEquals("email@email.pl", booking.getLocation().getEmail());
        assertEquals("Mon - Fri 10-16", booking.getLocation().getOpeningHours());
        assertEquals("555555555", booking.getLocation().getPhone());
        assertEquals("44-444", booking.getLocation().getPostalCode());
        assertEquals(1, booking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1), booking.getBookingExtrasList().get(0).getId());
        assertEquals(BigDecimal.valueOf(200), booking.getBookingExtrasList().get(0).getValue());
        assertEquals(BigDecimal.valueOf(100), booking.getBookingExtrasList().get(0).getBookingExtras().getPrice());
        assertEquals(Long.valueOf(8L), booking.getBookingExtrasList().get(0).getBookingExtras().getId());
        assertEquals("RED MIDDLE", booking.getBookingExtrasList().get(0).getBookingExtras().getDescription());
        assertEquals("BOOSTER", booking.getBookingExtrasList().get(0).getBookingExtras().getName());
        assertEquals("/img/icon.jpg", booking.getBookingExtrasList().get(0).getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(2), booking.getBookingExtrasList().get(0).getQuantity());
     //   assertEquals(Long.valueOf(1), booking.getDiscountCode().getId());
      //  assertEquals("IN RENT", booking.getDiscountCode().getDiscountDescription());
    }

    @Test
    public void mapToBookingWithoutBookingId() {
        //Given
        BookingDto bookingDto = new BookingDto( 1L, 3L, 4L, 5L, BigDecimal.valueOf(600),
                Timestamp.valueOf("2020-12-11 15:00:00"), Timestamp.valueOf("2020-12-13 15:00:00"), new ArrayList<>());

        BookingExtrasItemDto extrasItemDto = new BookingExtrasItemDto(1L, "BOOSTER", "RED MIDDLE", 2L,
                BigDecimal.valueOf(200), BigDecimal.valueOf(100), 8L, 1L, "/img/icon.jpg");
        List<BookingExtrasItemDto> extrasItemDtoList = Collections.singletonList(extrasItemDto);
        bookingDto.setBookingExtrasList(extrasItemDtoList);
        User user = getUser();
        Car car = getCar();
        BookingStatusCode bookingStatusCode = new BookingStatusCode(4L, "IN RENT");
        Locationn locationn = getLocationn();
        List<BookingExtrasItem> extrasItemList = getBookingExtrasItemList();

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(carService.findById(car.getId())).thenReturn(car);
        when(statusService.findById(bookingStatusCode.getId())).thenReturn(bookingStatusCode);
        when(cityService.findById(locationn.getId())).thenReturn(locationn);
        when(bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList())).thenReturn(extrasItemList);

        //When
        Booking booking = bookingMapper.mapToBooking(bookingDto);

        //Then
        assertNull(booking.getId());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"), booking.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"), booking.getReturnDate());
        assertEquals(BigDecimal.valueOf(600), booking.getTotalCost());
        assertEquals(Long.valueOf(1), booking.getUser().getId());
        assertEquals("firstname", booking.getUser().getFirstname());
        assertEquals("lastname", booking.getUser().getLastname());
        assertEquals("email", booking.getUser().getEmail());
        assertEquals("password", booking.getUser().getPassword());
        assertEquals("username", booking.getUser().getUsername());
        assertEquals("address line 1", booking.getUser().getAddressLine1());
        assertEquals("address line 2", booking.getUser().getAddressLine2());
        assertEquals("Poland", booking.getUser().getCountry());
        assertEquals("Slaskie", booking.getUser().getState());
        assertEquals("44-244", booking.getUser().getZipCode());
        assertEquals("WWA 123456", booking.getCar().getRegistration());
        assertEquals("RENT", booking.getCar().getVehicleStatus());
        assertEquals(BigDecimal.valueOf(250), booking.getCar().getPrice());
        assertEquals(Long.valueOf(3), booking.getCar().getId());
        assertEquals("IN RENT", booking.getBookingStatusCode().getDescription());
        assertEquals(Long.valueOf(4), booking.getBookingStatusCode().getId());
        assertEquals(Long.valueOf(5), booking.getLocation().getId());
        assertEquals("address line 1", booking.getLocation().getAddressLine());
        assertEquals("address line 2", booking.getLocation().getAddressLine2());
        assertEquals("Katowice", booking.getLocation().getCity());
        assertEquals("Poland", booking.getLocation().getCountry());
        assertEquals("Terminal 2, office next to parking", booking.getLocation().getPickUpInstructions());
        assertEquals("Katowice Airport", booking.getLocation().getLocationName());
        assertEquals("email@email.pl", booking.getLocation().getEmail());
        assertEquals("Mon - Fri 10-16", booking.getLocation().getOpeningHours());
        assertEquals("555555555", booking.getLocation().getPhone());
        assertEquals("44-444", booking.getLocation().getPostalCode());
        assertEquals(1, booking.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1), booking.getBookingExtrasList().get(0).getId());
        assertEquals(BigDecimal.valueOf(200), booking.getBookingExtrasList().get(0).getValue());
        assertEquals(BigDecimal.valueOf(100), booking.getBookingExtrasList().get(0).getBookingExtras().getPrice());
        assertEquals(Long.valueOf(8L), booking.getBookingExtrasList().get(0).getBookingExtras().getId());
        assertEquals("RED MIDDLE", booking.getBookingExtrasList().get(0).getBookingExtras().getDescription());
        assertEquals("BOOSTER", booking.getBookingExtrasList().get(0).getBookingExtras().getName());
        assertEquals("/img/icon.jpg", booking.getBookingExtrasList().get(0).getBookingExtras().getIconPath());
        assertEquals(Long.valueOf(2), booking.getBookingExtrasList().get(0).getQuantity());
        //   assertEquals(Long.valueOf(1), booking.getDiscountCode().getId());
        //  assertEquals("IN RENT", booking.getDiscountCode().getDiscountDescription());
    }


    @Test
    public void mapToBookingDto() {
        //Given
        Booking booking = getBooking();
        List<BookingExtrasItemDto> extrasItemDtos = getBookingDto().getBookingExtrasList();

        when(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList())).thenReturn(extrasItemDtos);

        //When
        BookingDto bookingDto = bookingMapper.mapToBookingDto(booking);

        assertEquals(Long.valueOf(1), bookingDto.getId());
        assertEquals(Long.valueOf(3), bookingDto.getCarId());
        assertEquals(Long.valueOf(4), bookingDto.getBookingStatusCodeId());
        assertEquals(Long.valueOf(5), bookingDto.getCityId());
        assertEquals(BigDecimal.valueOf(600), bookingDto.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"), bookingDto.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"), bookingDto.getReturnDate());
        assertEquals(1, bookingDto.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals(BigDecimal.valueOf(200), bookingDto.getBookingExtrasList().get(0).getTotalValue());
        assertEquals(BigDecimal.valueOf(100), bookingDto.getBookingExtrasList().get(0).getPrice());
        assertEquals(Long.valueOf(1L), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals("RED MIDDLE", bookingDto.getBookingExtrasList().get(0).getDescription());
        assertEquals("BOOSTER", bookingDto.getBookingExtrasList().get(0).getName());
        assertEquals("/img/icon.jpg", bookingDto.getBookingExtrasList().get(0).getIconPath());
    }

    @Test
    public void ShouldMapToBookingDtoWithoutBookingId() {
        //Given
        Booking booking = getBooking();
        booking.setId(null);
        List<BookingExtrasItemDto> extrasItemDtos = getBookingDto().getBookingExtrasList();

        when(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList())).thenReturn(extrasItemDtos);

        //When
        BookingDto bookingDto = bookingMapper.mapToBookingDto(booking);

        assertNull(bookingDto.getId());
        assertEquals(Long.valueOf(3), bookingDto.getCarId());
        assertEquals(Long.valueOf(4), bookingDto.getBookingStatusCodeId());
        assertEquals(Long.valueOf(5), bookingDto.getCityId());
        assertEquals(BigDecimal.valueOf(600), bookingDto.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"), bookingDto.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"), bookingDto.getReturnDate());
        assertEquals(1, bookingDto.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals(BigDecimal.valueOf(200), bookingDto.getBookingExtrasList().get(0).getTotalValue());
        assertEquals(BigDecimal.valueOf(100), bookingDto.getBookingExtrasList().get(0).getPrice());
        assertEquals(Long.valueOf(1L), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals("RED MIDDLE", bookingDto.getBookingExtrasList().get(0).getDescription());
        assertEquals("BOOSTER", bookingDto.getBookingExtrasList().get(0).getName());
        assertEquals("/img/icon.jpg", bookingDto.getBookingExtrasList().get(0).getIconPath());
    }

    @Test
    public void mapToBookingDtoList() {
        Booking booking = getBooking();
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        List<BookingExtrasItemDto> extrasItemDtos = getBookingDto().getBookingExtrasList();


        when(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(anyList())).thenReturn(extrasItemDtos);

        //When
        List<BookingDto> bookingDtoList  = bookingMapper.mapToBookingDtoList(bookingList);
        BookingDto bookingDto = bookingDtoList.get(0);

        //Then
        assertEquals(1, bookingDtoList.size());
        assertEquals(Long.valueOf(1), bookingDto.getId());
        assertEquals(Long.valueOf(3), bookingDto.getCarId());
        assertEquals(Long.valueOf(4), bookingDto.getBookingStatusCodeId());
        assertEquals(Long.valueOf(5), bookingDto.getCityId());
        assertEquals(BigDecimal.valueOf(600), bookingDto.getTotalCost());
        assertEquals(Timestamp.valueOf("2020-12-11 15:00:00"), bookingDto.getStartDate());
        assertEquals(Timestamp.valueOf("2020-12-13 15:00:00"), bookingDto.getReturnDate());
        assertEquals(1, bookingDto.getBookingExtrasList().size());
        assertEquals(Long.valueOf(1), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals(BigDecimal.valueOf(200), bookingDto.getBookingExtrasList().get(0).getTotalValue());
        assertEquals(BigDecimal.valueOf(100), bookingDto.getBookingExtrasList().get(0).getPrice());
        assertEquals(Long.valueOf(1L), bookingDto.getBookingExtrasList().get(0).getId());
        assertEquals("RED MIDDLE", bookingDto.getBookingExtrasList().get(0).getDescription());
        assertEquals("BOOSTER", bookingDto.getBookingExtrasList().get(0).getName());
        assertEquals("/img/icon.jpg", bookingDto.getBookingExtrasList().get(0).getIconPath());
    }
}