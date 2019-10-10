package com.mnidecki.cardoor.domain.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.car.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carId")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingStatusCodeId")
    private BookingStatusCode bookingStatusCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId")
    private Locationn location;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "receiptDate")
    private Timestamp startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "returnDate")
    private Timestamp returnDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(targetEntity = BookingExtrasItem.class,
            mappedBy = "booking",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<BookingExtrasItem> bookingExtrasList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discountCodeId")
    private DiscountCode discountCode;


    public Booking(Long id, User user, Car car, BookingStatusCode bookingStatusCode, Locationn location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, Timestamp createdDate, List<BookingExtrasItem> bookingExtrasList, DiscountCode discountCode) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.createdDate = createdDate;
        this.bookingExtrasList = bookingExtrasList;
        this.discountCode = discountCode;
    }

    public static class BookingBuilder {
        private Long id;
        private User user;
        private Car car;
        private BookingStatusCode bookingStatusCode;
        private Locationn location;
        private BigDecimal totalCost;
        private Timestamp startDate;
        private Timestamp returnDate;
        private Timestamp createdDate;
        private List<BookingExtrasItem> bookingExtrasList;
        private DiscountCode discountCode;

        public BookingBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingBuilder user(User user) {
            this.user = user;
            return this;
        }

        public BookingBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public BookingBuilder bookingStatusCode(BookingStatusCode bookingStatusCode) {
            this.bookingStatusCode = bookingStatusCode;
            return this;
        }

        public BookingBuilder location(Locationn location) {
            this.location = location;
            return this;
        }

        public BookingBuilder totalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public BookingBuilder startDate(Timestamp startDate) {
            this.startDate = startDate;
            return this;
        }

        public BookingBuilder returnDate(Timestamp returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BookingBuilder createdDate(Timestamp createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BookingBuilder bookingExtrasList(List<BookingExtrasItem> bookingExtrasList) {
            this.bookingExtrasList = bookingExtrasList;
            return this;
        }

        public BookingBuilder discountCode(DiscountCode discountCode) {
            this.discountCode = discountCode;
            return this;
        }

        public Booking build() {
            return new Booking(id, user, car, bookingStatusCode, location, totalCost, startDate, returnDate, createdDate, bookingExtrasList, discountCode);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (id != null ? !id.equals(booking.id) : booking.id != null) return false;
        if (user != null ? !user.equals(booking.user) : booking.user != null) return false;
        if (car != null ? !car.equals(booking.car) : booking.car != null) return false;
        if (startDate != null ? !startDate.equals(booking.startDate) : booking.startDate != null) return false;
        if (returnDate != null ? !returnDate.equals(booking.returnDate) : booking.returnDate != null) return false;
        return  createdDate != null ? createdDate.equals(booking.createdDate) : booking.createdDate == null;


    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (bookingStatusCode != null ? bookingStatusCode.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (bookingExtrasList != null ? bookingExtrasList.hashCode() : 0);
        result = 31 * result + (discountCode != null ? discountCode.hashCode() : 0);
        return result;
    }
}
