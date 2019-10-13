package com.mnidecki.cardoor.domain.car;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.booking.Booking;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@Setter
@Entity(name = "cars")
@Table(name = "cars")
@Where(clause = "deleted = false")
public class Car implements Serializable {

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
    private Locationn location;

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "car",
            fetch = FetchType.EAGER)
    private List<Booking> bookingsList;

    @Column(name = "deleted")
    private boolean deleted = false;


    public Car(Long id, CarBrandModel model, String registration, String vehicleStatus, CarParameters carParameters, BigDecimal price, Locationn location) {
        this.id = id;
        this.model = model;
        this.registration = registration;
        this.vehicleStatus = vehicleStatus;
        this.carParameters = carParameters;
        this.price = price;
        this.location = location;
    }

    public Car(Long id, CarBrandModel model, String registration, String vehicleStatus, CarParameters carParameters,
               BigDecimal price, Locationn location, List<Booking> bookingsList) {
        this.id = id;
        this.model = model;
        this.registration = registration;
        this.vehicleStatus = vehicleStatus;
        this.carParameters = carParameters;
        this.price = price;
        this.location = location;
        this.bookingsList = bookingsList;
    }

    public Car(String registration) {
        this.registration = registration;
    }

    public Long getId() {
        return id;
    }

    public CarBrandModel getModel() {
        return model;
    }

    public String getRegistration() {
        return registration;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public CarParameters getCarParameters() {
        return carParameters;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Locationn getLocation() {
        return location;
    }

    public List<Booking> getBookingsList() {
        return bookingsList;
    }

    public static class CarBuilder {
        private Long id;
        private CarBrandModel model;
        private String registration;
        private String vehicleStatus;
        private CarParameters carParameters;
        private BigDecimal price;
        private Locationn location;
        private List<Booking> bookingsList;

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

        public CarBuilder location(Locationn location) {
            this.location = location;
            return this;
        }

        public CarBuilder bookingsList(List<Booking> bookingsList) {
            this.bookingsList = bookingsList;
            return this;
        }

        public Car build() {
            return new Car(id, model, registration, vehicleStatus, carParameters, price, location, bookingsList);
        }
    }


}

