package com.mnidecki.cardoor.domain.booking;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking_extras_item")
public class BookingExtrasItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_extras_id")
    private BookingExtras bookingExtras;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public BookingExtrasItem(Long quantity, BigDecimal value, BookingExtras bookingExtras, Booking booking) {
        this.quantity = quantity;
        this.value = value;
        this.bookingExtras = bookingExtras;
        this.booking = booking;
    }

    public BookingExtrasItem(Long quantity, BigDecimal value, BookingExtras bookingExtras) {
        this.quantity = quantity;
        this.value = value;
        this.bookingExtras = bookingExtras;
    }

    public BookingExtrasItem(Long id, Long quantity, BigDecimal value, BookingExtras bookingExtras) {
        this.id = id;
        this.quantity = quantity;
        this.value = value;
        this.bookingExtras = bookingExtras;

    }

    @Override
    public String toString() {
        return "BookingExtrasItem{" +

                ", quantity=" + quantity +

                ", bookingExtras=" + bookingExtras +

                '}';
    }
    public Long getId(){
        return id;
    }
}
