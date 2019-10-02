package com.mnidecki.cardoor.domain.car;

import com.mnidecki.cardoor.domain.Location;
import com.mnidecki.cardoor.domain.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelId")
    private CarBrandModel model;

    @Column(name = "registration")
    private String registration;

    @Column(name = "vehicleStatus")
    private String vehicleStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carParametersId", referencedColumnName = "id")
    private CarParameters carParameters;

    @Column(name = "daily_price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId")
    private Location location;

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "car",
            fetch = FetchType.EAGER)
    private Set<Booking> bookingsList;


    public Car(Long id, CarBrandModel model, String registration, String vehicleStatus, CarParameters carParameters, BigDecimal price, Location location) {
        this.id = id;
        this.model = model;
        this.registration = registration;
        this.vehicleStatus = vehicleStatus;
        this.carParameters = carParameters;
        this.price = price;
        this.location = location;
    }

    public static class CarBuilder {
        private Long id;
        private CarBrandModel model;
        private String registration;
        private String vehicleStatus;
        private CarParameters carParameters;
        private BigDecimal price;
        private Location location;
        private Set<Booking> bookingsList;

        public CarBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder model(CarBrandModel model) {
            this.model = model;
            return this;
        }

        public CarBuilder registration(String registration) {
            this.registration = registration;
            return this;
        }

        public CarBuilder vehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
            return this;
        }

        public CarBuilder carParameters(CarParameters carParameters) {
            this.carParameters = carParameters;
            return this;
        }

        public CarBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CarBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public CarBuilder bookingsList(Set<Booking> bookingsList) {
            this.bookingsList = bookingsList;
            return this;
        }

        public Car build() {
            return new Car(id, model, registration, vehicleStatus, carParameters, price, location);
        }
    }
}

