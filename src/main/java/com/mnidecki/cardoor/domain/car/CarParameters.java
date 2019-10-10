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


    public static class CarParametersBuilder {
        private String fuelType;
        private boolean allWheelDrive;
        private Integer doorsNumber;
        private Integer seatsNumber;
        private String color;
        private Integer year;
        private boolean transmissionIsAutomatic;
        private boolean airConditioning;
        private CarType type;
        private CarPicture carPicture;
        private Car car;
        private Integer bigBags;
        private Integer smallBags;
        private Long id;


        public CarParametersBuilder fuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public CarParametersBuilder allWheelDrive(boolean allWheelDrive) {
            this.allWheelDrive = allWheelDrive;
            return this;
        }

        public CarParametersBuilder doorsNumber(Integer doorsNumber) {
            this.doorsNumber = doorsNumber;
            return this;
        }

        public CarParametersBuilder seatsNumber(Integer seatsNumber) {
            this.seatsNumber = seatsNumber;
            return this;
        }

        public CarParametersBuilder color(String color) {
            this.color = color;
            return this;
        }

        public CarParametersBuilder year(Integer year) {
            this.year = year;
            return this;
        }

        public CarParametersBuilder transmissionIsAutomatic(boolean transmissionIsAutomatic) {
            this.transmissionIsAutomatic = transmissionIsAutomatic;
            return this;
        }

        public CarParametersBuilder airConditioning(boolean airConditioning) {
            this.airConditioning = airConditioning;
            return this;
        }

        public CarParametersBuilder type(CarType type) {
            this.type = type;
            return this;
        }

        public CarParametersBuilder carPicture(CarPicture carPicture) {
            this.carPicture = carPicture;
            return this;
        }

        public CarParametersBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public CarParametersBuilder bigBags(Integer bigBags) {
            this.bigBags = bigBags;
            return this;
        }

        public CarParametersBuilder smallBags(Integer smallBags) {
            this.smallBags = smallBags;
            return this;
        }

        public CarParametersBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarParameters build() {
            return new CarParameters(id, fuelType, allWheelDrive, doorsNumber, seatsNumber, bigBags, smallBags, color,
                    year, transmissionIsAutomatic, airConditioning, type, carPicture, car);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarParameters that = (CarParameters) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (carPicture != null ? !carPicture.equals(that.carPicture) : that.carPicture != null) return false;
        return car != null ? car.equals(that.car) : that.car == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (allWheelDrive ? 1 : 0);
        result = 31 * result + (doorsNumber != null ? doorsNumber.hashCode() : 0);
        result = 31 * result + (seatsNumber != null ? seatsNumber.hashCode() : 0);
        result = 31 * result + (bigBags != null ? bigBags.hashCode() : 0);
        result = 31 * result + (smallBags != null ? smallBags.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (transmissionIsAutomatic ? 1 : 0);
        result = 31 * result + (airConditioning ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (carPicture != null ? carPicture.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        return result;
    }
}

