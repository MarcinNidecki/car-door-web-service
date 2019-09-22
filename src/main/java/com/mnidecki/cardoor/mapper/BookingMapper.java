package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.dto.BookingDto;
import com.mnidecki.cardoor.services.DBService.DBBookingStatusCodeService;
import com.mnidecki.cardoor.services.DBService.DBCarService;
import com.mnidecki.cardoor.services.DBService.DBLocationService;
import com.mnidecki.cardoor.services.DBService.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class BookingMapper {

    @Autowired
    private DBUserService userService;
    @Autowired
    private DBLocationService cityService;
    @Autowired
    private DBCarService carService;
    @Autowired
    private DBBookingStatusCodeService statusService;
    @Autowired
    private BookingExtrasItemMapper bookingExtrasItemMapper;

    public Booking mapToBooking(BookingDto bookingDto) {
        if (bookingDto.getUserId()!=null) {
            return new Booking(
                    bookingDto.getId(),
                    userService.findUserById(bookingDto.getUserId()),
                    carService.getCar(bookingDto.getCarId()),
                    statusService.getBookingStatusCode(bookingDto.getBookingStatusCodeId()),
                    cityService.getLocation(bookingDto.getCityId()),
                    bookingDto.getTotalCost(),
                    bookingDto.getStartDate(),
                    bookingDto.getReturnDate(),
                    bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList()));
        } else {
            return new Booking(
                    userService.findUserById(bookingDto.getUserId()),
                    carService.getCar(bookingDto.getCarId()),
                    statusService.getBookingStatusCode(bookingDto.getBookingStatusCodeId()),
                    cityService.getLocation(bookingDto.getCityId()),
                    bookingDto.getTotalCost(),
                    bookingDto.getStartDate(),
                    bookingDto.getReturnDate(),
                    bookingExtrasItemMapper.mapToBookingExtrasItemList(bookingDto.getBookingExtrasList()));
        }

    }

    public BookingDto mapToBookingDto(Booking booking) {
        if (booking.getUser()!=null) {
            return  new BookingDto(
                    booking.getId(),
                    booking.getUser().getId(),
                    booking.getCar().getId(),
                    booking.getBookingStatusCode().getId(),
                    booking.getLocation().getId(),
                    booking.getTotalCost(),
                    booking.getStartDate(),
                    booking.getReturnDate(),
                    bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList()));
        } else  {
            return  new BookingDto(
                    booking.getId(),
                    booking.getCar().getId(),
                    booking.getBookingStatusCode().getId(),
                    booking.getLocation().getId(),
                    booking.getTotalCost(),
                    booking.getStartDate(),
                    booking.getReturnDate(),
                    bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(booking.getBookingExtrasList()));
        }

    }

    public List<BookingDto> mapToBookingDtoList (List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::mapToBookingDto)
                .collect(Collectors.toList());
    }

    public List<Booking> mapToBookingList (List<BookingDto> bookingList) {
        return bookingList.stream()
                .map(this::mapToBooking)
                .collect(Collectors.toList());
    }
}