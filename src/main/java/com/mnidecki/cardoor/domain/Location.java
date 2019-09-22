package com.mnidecki.cardoor.domain;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "cities")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "location_name")
    private String locationName;
    @Column(name = "address_line")
    private String addressLine;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "pick_up_instructions")
    private String pickUpInstructions;
    @Column(name = "postalCode")
    private String postalCode;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "opening_hours")
    private String openingHours;


    @OneToMany(targetEntity = Car.class,
            mappedBy = "location",
            fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "location",
            fetch = FetchType.EAGER)
    private Set<Booking> bookingList = new HashSet<>();


    public Location(Long id, String country, String city, String locationName, String addressLine, String addressLine2, String pickUpInstructions, String postalCode, String email, String phone, String openingHours) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.locationName = locationName;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.pickUpInstructions = pickUpInstructions;
        this.postalCode = postalCode;
        this.email = email;
        this.phone = phone;
        this.openingHours = openingHours;
    }
}
