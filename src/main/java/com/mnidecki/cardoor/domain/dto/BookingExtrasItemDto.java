package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class BookingExtrasItemDto {

    private Long id;
    private String name;
    private String description;
    private Long quantity;
    private BigDecimal totalValue;
    private BigDecimal price;
    private Long bookingExtrasId;
    private Long bookingId;
    private String iconPath;

    public BookingExtrasItemDto(Long id, String name, String description, Long quantity, BigDecimal totalValue, BigDecimal price, Long bookingExtrasId, Long bookingId, String iconPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.price = price;
        this.bookingExtrasId = bookingExtrasId;
        this.bookingId = bookingId;
        this.iconPath = iconPath;
    }

    public BookingExtrasItemDto(Long id, String name, String description, Long quantity, BigDecimal totalValue, BigDecimal price, Long bookingExtrasId, String iconPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.price = price;
        this.bookingExtrasId = bookingExtrasId;
        this.iconPath = iconPath;
    }

    public BookingExtrasItemDto(String name, String description, Long quantity, BigDecimal totalValue, BigDecimal price, Long bookingExtrasId, String iconPath) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.price = price;
        this.bookingExtrasId = bookingExtrasId;
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return "BookingExtrasItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", totalValue=" + totalValue +
                ", price=" + price +
                ", bookingExtrasId=" + bookingExtrasId +
                ", bookingId=" + bookingId +
                ", iconPath='" + iconPath + '\'' +
                '}';
    }
}
