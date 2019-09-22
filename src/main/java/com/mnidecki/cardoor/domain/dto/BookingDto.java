package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long id;
    private Long userId;
    private Long carId;
    private Long bookingStatusCodeId;
    private Long cityId;
    private BigDecimal totalCost;
    private Timestamp startDate;
    private Timestamp returnDate;
    private List<BookingExtrasItemDto> bookingExtrasList;

    public BookingDto(Long userId, Long carId, Long bookingStatusCodeId, Long cityId, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, List<BookingExtrasItemDto> bookingExtrasList) {
        this.userId = userId;
        this.carId = carId;
        this.bookingStatusCodeId = bookingStatusCodeId;
        this.cityId = cityId;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.bookingExtrasList = bookingExtrasList;
    }
    public BookingDto(Long carId, Long bookingStatusCodeId, Long cityId, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, List<BookingExtrasItemDto> bookingExtrasList) {
        this.carId = carId;
        this.bookingStatusCodeId = bookingStatusCodeId;
        this.cityId = cityId;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.bookingExtrasList = bookingExtrasList;
    }

    public BookingDto(Long userId, Long carId, Long bookingStatusCodeId, Long cityId, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate) {
        this.userId = userId;
        this.carId = carId;
        this.bookingStatusCodeId = bookingStatusCodeId;
        this.cityId = cityId;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }
}

