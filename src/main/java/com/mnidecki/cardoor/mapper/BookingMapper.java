package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.dto.BookingDto;
import com.mnidecki.cardoor.services.DBService.BookingStatusCodeService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private LocationService cityService;
    @Autowired
    private CarService carService;
    @Autowired
    private BookingStatusCodeService statusService;
    @Autowired
    private BookingExtrasItemMapper bookingExtrasItemMapper;

    public Booking mapToBooking(BookingDto bookingDto) {
        if (bookingDto.getUserId() != null) {
            return new Booking.BookingBuilder()
                    .id(bookingDto.getId())
                    .user(userService.findUserById(bookingDto.getUserId()))
                    .car(carService.findById(bookingDto.getCarId()))
                    .bookingStatusCode(statusService.findById(bookingDto.getBookingStatusCodeId()).orElseGet(null))
                    .location( cityService.findById(bookingDto.getCityId()))
                    .totalCost(bookingDto.getTotalCost())
                    .startDate(bookingDto.getStartDate())
                    .returnDate(bookingDto.getReturnDate())
                    .bookingExtrasList(bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList()))
                    .build();
        } else {
            return new Booking.BookingBuilder()
                    .user(userService.findUserById(bookingDto.getUserId()))
                    .car(carService.findById(bookingDto.getCarId()))
                    .bookingStatusCode(statusService.findById(bookingDto.getBookingStatusCodeId()).orElseGet(null))
                    .location(cityService.findById(bookingDto.getCityId()))
                    .totalCost( bookingDto.getTotalCost())
                    .startDate(bookingDto.getStartDate())
                    .returnDate(bookingDto.getReturnDate())
                    .bookingExtrasList( bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList()))
                    .build();
        }

    }

    public BookingDto mapToBookingDto(Booking booking) {
        if (booking.getUser() != null) {
            return new BookingDto.BookingDtoBuilder()
                    .id(booking.getId())
                    .userId(booking.getUser().getId())
                    .carId(booking.getCar().getId())
                    .bookingStatusCodeId(booking.getBookingStatusCode().getId())
                    .cityId(booking.getLocation().getId())
                    .totalCost(booking.getTotalCost())
                    .startDate(booking.getStartDate())
                    .returnDate(booking.getReturnDate())
                    .bookingExtrasList(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList()))
                    .build();
        } else {
            return new BookingDto.BookingDtoBuilder()
                    .userId(booking.getId())
                    .carId(booking.getCar().getId())
                    .bookingStatusCodeId(booking.getBookingStatusCode().getId())
                    .cityId(booking.getLocation().getId())
                    .totalCost(booking.getTotalCost())
                    .startDate(booking.getStartDate())
                    .returnDate(booking.getReturnDate())
                    .bookingExtrasList(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList()))
                    .build();
        }

    }



    public List<BookingDto> mapToBookingDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .collect(Collectors.toList());
    }

    public List<Booking> mapToBookingList(List<BookingDto> bookingList) {
        return bookingList.stream()
                .map(this::mapToBooking)
                .collect(Collectors.toList());
    }
}