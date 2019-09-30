package com.mnidecki.cardoor.domain.booking;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
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
}
