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
public class Locationn {

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


    public Locationn(Long id, String country, String city, String locationName, String addressLine, String addressLine2, String pickUpInstructions, String postalCode, String email, String phone, String openingHours) {
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

    public Locationn(Long id, String city) {
        this.id = id;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Locationn location = (Locationn) o;

        if (id != null ? !id.equals(location.id) : location.id != null) return false;
        if (country != null ? !country.equals(location.country) : location.country != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        if (locationName != null ? !locationName.equals(location.locationName) : location.locationName != null)
            return false;
        if (addressLine != null ? !addressLine.equals(location.addressLine) : location.addressLine != null)
            return false;
        if (addressLine2 != null ? !addressLine2.equals(location.addressLine2) : location.addressLine2 != null)
            return false;
        if (pickUpInstructions != null ? !pickUpInstructions.equals(location.pickUpInstructions) : location.pickUpInstructions != null)
            return false;
        if (postalCode != null ? !postalCode.equals(location.postalCode) : location.postalCode != null) return false;
        if (email != null ? !email.equals(location.email) : location.email != null) return false;
        if (phone != null ? !phone.equals(location.phone) : location.phone != null) return false;
        if (openingHours != null ? !openingHours.equals(location.openingHours) : location.openingHours != null)
            return false;
        if (cars != null ? !cars.equals(location.cars) : location.cars != null) return false;
        return bookingList != null ? bookingList.equals(location.bookingList) : location.bookingList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (locationName != null ? locationName.hashCode() : 0);
        result = 31 * result + (addressLine != null ? addressLine.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (pickUpInstructions != null ? pickUpInstructions.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (openingHours != null ? openingHours.hashCode() : 0);
        result = 31 * result + (cars != null ? cars.hashCode() : 0);
        result = 31 * result + (bookingList != null ? bookingList.hashCode() : 0);
        return result;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}
