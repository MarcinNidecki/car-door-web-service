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

    public static class BookingDtoBuilder {
        private Long userId;
        private Long carId;
        private Long bookingStatusCodeId;
        private Long cityId;
        private BigDecimal totalCost;
        private Timestamp startDate;
        private Timestamp returnDate;
        private List<BookingExtrasItemDto> bookingExtrasList;
        private Long id;

        public BookingDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BookingDtoBuilder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public BookingDtoBuilder bookingStatusCodeId(Long bookingStatusCodeId) {
            this.bookingStatusCodeId = bookingStatusCodeId;
            return this;
        }

        public BookingDtoBuilder cityId(Long cityId) {
            this.cityId = cityId;
            return this;
        }

        public BookingDtoBuilder totalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public BookingDtoBuilder startDate(Timestamp startDate) {
            this.startDate = startDate;
            return this;
        }

        public BookingDtoBuilder returnDate(Timestamp returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BookingDtoBuilder bookingExtrasList(List<BookingExtrasItemDto> bookingExtrasList) {
            this.bookingExtrasList = bookingExtrasList;
            return this;
        }

        public BookingDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingDto build() {
            return new BookingDto(userId, carId, bookingStatusCodeId, cityId, totalCost, startDate, returnDate, bookingExtrasList);
        }
    }

}

