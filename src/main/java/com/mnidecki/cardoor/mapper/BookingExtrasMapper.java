package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.domain.dto.BookingExtrasDto;

import java.util.List;
import java.util.stream.Collectors;

public class BookingExtrasMapper {

    public BookingExtrasDto mapToBookingExtrasDto (BookingExtras bookingExtras) {
        return new BookingExtrasDto(
                bookingExtras.getId(),
                bookingExtras.getName(),
                bookingExtras.getDescription(),
                bookingExtras.getPrice(),
                bookingExtras.getIconPath()
        );
    }

    public List<BookingExtrasDto> mapToBookingExtrasDtoList (List<BookingExtras> bookingExtrasList){
      return bookingExtrasList.stream()
              .map(this::mapToBookingExtrasDto)
              .collect(Collectors.toList());
    }
}
