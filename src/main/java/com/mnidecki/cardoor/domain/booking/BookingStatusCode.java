package com.mnidecki.cardoor.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking_status")
public class BookingStatusCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "bookingStatusCode",
            fetch = FetchType.EAGER)
    private List<Booking> bookingList = new ArrayList<>();

    public BookingStatusCode(Long id, String description, List<Booking> bookingList) {
        this.id = id;
        this.description = description;
        this.bookingList = bookingList;
    }

    public BookingStatusCode(String description, List<Booking> bookingList) {
        this.description = description;
        this.bookingList = bookingList;
    }

    public BookingStatusCode(Long id, String description) {
        this.id = id;
        this.description = description;


    }
}
