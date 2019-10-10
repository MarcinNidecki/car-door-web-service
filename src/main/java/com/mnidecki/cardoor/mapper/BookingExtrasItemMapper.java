package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.dto.BookingExtrasItemDto;
import com.mnidecki.cardoor.services.DBService.BookingExtrasService;
import com.mnidecki.cardoor.services.DBService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingExtrasItemMapper {

    @Autowired
    private BookingExtrasService bookingExtrasService;
    @Autowired
    private BookingService bookingService;

    public BookingExtrasItemDto mapToBookingExtrasItemDto(BookingExtrasItem bookingExtrasItem) {
        if (bookingExtrasItem.getBooking() == null) {
            return new BookingExtrasItemDto(
                    bookingExtrasItem.getId(),
                    bookingExtrasItem.getBookingExtras().getName(),
                    bookingExtrasItem.getBookingExtras().getDescription(),
                    bookingExtrasItem.getQuantity(),
                    bookingExtrasItem.getValue(),
                    bookingExtrasItem.getBookingExtras().getPrice(),
                    bookingExtrasItem.getBookingExtras().getId(),
                    bookingExtrasItem.getBookingExtras().getIconPath()
            );
        } else {
            return new BookingExtrasItemDto(
                    bookingExtrasItem.getId(),
                    bookingExtrasItem.getBookingExtras().getName(),
                    bookingExtrasItem.getBookingExtras().getDescription(),
                    bookingExtrasItem.getQuantity(),
                    bookingExtrasItem.getValue(),
                    bookingExtrasItem.getBookingExtras().getPrice(),
                    bookingExtrasItem.getBookingExtras().getId(),
                    bookingExtrasItem.getBooking().getId(),
                    bookingExtrasItem.getBookingExtras().getIconPath());
        }
    }

    public List<BookingExtrasItemDto> mapToBookingExtrasItemDtoList(List<BookingExtrasItem> bookingExtrasItemList) {
        return bookingExtrasItemList.stream()
                .map(this::mapToBookingExtrasItemDto)
                .collect(Collectors.toList());
    }

    public BookingExtrasItem mapToBookingExtrasItem(BookingExtrasItemDto bookingExtrasItemDto) {
        if (bookingExtrasItemDto.getBookingId() != null) {
            return new BookingExtrasItem(
                    bookingExtrasItemDto.getId(),
                    bookingExtrasItemDto.getQuantity(),
                    bookingExtrasItemDto.getTotalValue(),
                    bookingExtrasService.findById(bookingExtrasItemDto.getBookingExtrasId()),
                    bookingService.findById(bookingExtrasItemDto.getBookingId())
            );
        } else {
            return new BookingExtrasItem(
                    bookingExtrasItemDto.getId(),
                    bookingExtrasItemDto.getQuantity(),
                    bookingExtrasItemDto.getTotalValue(),
                    bookingExtrasService.findById(bookingExtrasItemDto.getBookingExtrasId()));

        }

    }

    public List<BookingExtrasItem> mapToBookingExtrasItemList(List<BookingExtrasItemDto> bookingExtrasItemList) {
        return bookingExtrasItemList.stream()
                .map(this::mapToBookingExtrasItem)
                .collect(Collectors.toList());
    }
}
