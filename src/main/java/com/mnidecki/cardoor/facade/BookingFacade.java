package com.mnidecki.cardoor.facade;

import com.mnidecki.cardoor.client.KayakClient;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.BookingService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import com.mnidecki.cardoor.services.DBService.UserService;
import com.mnidecki.cardoor.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingFacade {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final BookingExtrasItemMapper bookingExtrasItemMapper;
    private final DateTimeService  dateTimeService;
    private final CarMapper carMapper;
    private final CarService carService;
    private final KayakClient kayakClient;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public BookingFacade(BookingService bookingService, BookingMapper bookingMapper, BookingExtrasItemMapper bookingExtrasItemMapper, DateTimeService dateTimeService, CarMapper carMapper, CarService carService, KayakClient kayakClient, LocationService locationService, LocationMapper locationMapper, UserService userService, UserMapper userMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
        this.bookingExtrasItemMapper = bookingExtrasItemMapper;
        this.dateTimeService = dateTimeService;
        this.carMapper = carMapper;
        this.carService = carService;
        this.kayakClient = kayakClient;
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public BookingItemCreationDto getEmptyExtrasItemList() {
       return  new BookingItemCreationDto(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(bookingService.prepareEmptyExtrasItemList()));
    }

    public int countBookingDays(String startDate, String startTime, String endDate, String endTime) {
       return bookingService.countBookingDays(bookingService.stringTimeToTimestampConverter(startDate, startTime),
                bookingService.stringTimeToTimestampConverter(endDate, endTime));
    }

    public String reformatTime(String time) {
        return dateTimeService.reformatTime(time);
    }

    public boolean isDateValid(String startDate, String startTime, String endDate, String endTime) {
        return dateTimeService.isBookingDateValid(startDate,startTime,endDate,endTime);
    }

    public List<CarDto> getAllAvailableCar(Specification<Car> carSpecification,String startDate, String startTime,
                                           String endDate, String endTime, Long cityId) {
        return carMapper.mapToCarDtoList(carService.getAllAvailableCar(carSpecification, startDate,
                startTime, endDate, endTime, cityId));
    }

    public long getKayakAverageTotalCarPrice(String cityName, String startDate, String startTime,
                                             String endDate, String endTime) {
        return kayakClient.getKayakAverageTotalCarPrice(cityName, startDate, startTime, endDate, endTime);
    }

    public CarDto getCar(Long carId) {
        return carMapper.mapToCarDto(carService.findById(carId));
    }

    public LocationnDto getLocation(Long locationId) {
        return locationMapper.mapToLocationDto(locationService.findById(locationId));
    }
    
    public BookingDto prepareBooking(Long carId, Long cityId, String startTime,
                                     String endTime, String startDate, String endDate, BookingItemCreationDto bookingExtras) {

       return bookingMapper.mapToBookingDto(bookingService.setAllBookingCostFields(bookingMapper.mapToBooking(
               new BookingDto.BookingDtoBuilder()
                    .carId(carId)
                    .bookingStatusCodeId(1L)
                    .cityId(cityId)
                    .totalCost(BigDecimal.ZERO)
                    .startDate(bookingService.stringTimeToTimestampConverter(startDate, startTime))
                    .returnDate(bookingService.stringTimeToTimestampConverter(endDate, endTime))
                    .bookingExtrasList(bookingExtras.getItems())
                    .build())));
    }

    public void registerUserAndOrder(BookingDto bookingDto, UserDto userDto) {
        User user = userService.save(userMapper.mapToUser(userDto));
        Booking booking = bookingMapper.mapToBooking(bookingDto);
        booking.setUser(user);
        bookingService.save(booking);
    }

    public String getLocationName(Long cityId) {
        return locationService.findById(cityId).getCity();
    }

    public UserEditFormDto getUser() {
        if (userService.getUserFromAuthentication().getEmail() != null) {
            return userMapper.mapToEditFormUserDto(userService.getUserFromAuthentication());
        } else {
            return new UserEditFormDto();
        }
    }

    public boolean isUserExist(String email) {
        return userService.isUserExist(email);
    }
}
