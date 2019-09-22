package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.domain.dto.BookingExtrasItemDto;
import com.mnidecki.cardoor.services.DBService.DBBookingExtrasService;
import com.mnidecki.cardoor.services.DBService.DBBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class BookingExtrasItemMapper {

    private Long id;
    private String name;
    private String description;
    private Long quantity;
    private BigDecimal totalValue;
    private BigDecimal price;
    private Long bookingExtrasId;
    private Long bookingId;
    private String iconPath;
    @Autowired
    private DBBookingExtrasService bookingExtrasService;
    @Autowired
    private DBBookingService bookingService;

    public BookingExtrasItemDto mapToBookingExtrasItemDto (BookingExtrasItem bookingExtrasItem) {
        if(bookingExtrasItem.getBooking()==null) {
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
        } else  {
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

    public List<BookingExtrasItemDto> mapToBookingExtrasItemDtoList (List<BookingExtrasItem> bookingExtrasItemList) {
        return bookingExtrasItemList.stream()
                .map(this::mapToBookingExtrasItemDto)
                .collect(Collectors.toList());
    }

    public BookingExtrasItem mapToBookingExtrasItem (BookingExtrasItemDto bookingExtrasItemDto) {
        if (bookingExtrasItemDto.getBookingId() !=null) {
            return new BookingExtrasItem(
                    bookingExtrasItemDto.getId(),
                    bookingExtrasItemDto.getQuantity(),
                    bookingExtrasItemDto.getTotalValue(),
                    bookingExtrasService.findById(bookingExtrasItemDto.getBookingExtrasId()).orElse(null),
                    bookingService.getBooking(bookingExtrasItemDto.getBookingId())
            );
        } else {
            return new BookingExtrasItem(
                    bookingExtrasItemDto.getId(),
                    bookingExtrasItemDto.getQuantity(),
                    bookingExtrasItemDto.getTotalValue(),
                    bookingExtrasService.findById(bookingExtrasItemDto.getBookingExtrasId()).orElse(null));

        }

    }
    public List<BookingExtrasItem> mapToBookingExtrasItemList (List<BookingExtrasItemDto> bookingExtrasItemList) {
        return bookingExtrasItemList.stream()
                .map(this::mapToBookingExtrasItem)
                .collect(Collectors.toList());
    }
}
