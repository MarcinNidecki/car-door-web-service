package com.mnidecki.cardoor.domain.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.Location;
import com.mnidecki.cardoor.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

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
    private Location location;

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
            fetch = FetchType.EAGER)
    private List<BookingExtrasItem> bookingExtrasList = new ArrayList<>();

    public Booking(Long id, User user, Car car, BookingStatusCode bookingStatusCode, Location location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }

    public Booking(User user, Car car, BookingStatusCode bookingStatusCode, Location location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, List<BookingExtrasItem> bookingExtrasList) {
        this.user = user;
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.bookingExtrasList = bookingExtrasList;
    }
    public Booking(Long id, User user, Car car, BookingStatusCode bookingStatusCode, Location location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, List<BookingExtrasItem> bookingExtrasList) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.bookingExtrasList = bookingExtrasList;
    }
    public Booking(Car car, BookingStatusCode bookingStatusCode, Location location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate, List<BookingExtrasItem> bookingExtrasList) {
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.bookingExtrasList = bookingExtrasList;
    }


    public Booking(User user, Car car, BookingStatusCode bookingStatusCode, Location location, BigDecimal totalCost, Timestamp startDate, Timestamp returnDate) {
        this.user = user;
        this.car = car;
        this.bookingStatusCode = bookingStatusCode;
        this.location = location;
        this.totalCost = totalCost;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }


}
