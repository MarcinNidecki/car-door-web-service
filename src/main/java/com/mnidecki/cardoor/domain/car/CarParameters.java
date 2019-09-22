package com.mnidecki.cardoor.domain.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CarParameters")
public class CarParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "all_wheel_drive")
    private boolean allWheelDrive;

    @Column(name = "doors_number")
    private Integer doorsNumber;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @Column(name = "big_bags")
    private Integer bigBags;

    @Column(name = "small_bags")
    private Integer smallBags;

    @Column(name = "color")
    private String color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "transmission_automatic")
    private boolean transmissionIsAutomatic;

    @Column(name = "air_conditioning")
    private boolean airConditioning;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_Id")
    private CarType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_picture_id")
    private CarPicture carPicture;

    @JsonIgnore
    @OneToOne(mappedBy = "carParameters", cascade = CascadeType.ALL)
    private Car car;

    public CarParameters(String fuelType, boolean allWheelDrive, Integer doorsNumber, Integer seatsNumber, String color, Integer year, @NotNull boolean transmissionIsAutomatic, @NotNull boolean airConditioning, CarType type, CarPicture carPicture, Car car) {
        this.fuelType = fuelType;
        this.allWheelDrive = allWheelDrive;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.color = color;
        this.year = year;
        this.transmissionIsAutomatic = transmissionIsAutomatic;
        this.airConditioning = airConditioning;
        this.type = type;
        this.carPicture = carPicture;
        this.car = car;
    }

    public CarParameters(String fuelType, boolean allWheelDrive, Integer doorsNumber, Integer seatsNumber, Integer bigBags, Integer smallBags, String color, Integer year, @NotNull boolean transmissionIsAutomatic, @NotNull boolean airConditioning, CarType type, CarPicture carPicture) {
        this.fuelType = fuelType;
        this.allWheelDrive = allWheelDrive;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.bigBags = bigBags;
        this.smallBags = smallBags;
        this.color = color;
        this.year = year;
        this.transmissionIsAutomatic = transmissionIsAutomatic;
        this.airConditioning = airConditioning;
        this.type = type;
        this.carPicture = carPicture;
    }
}
