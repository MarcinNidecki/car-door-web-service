package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private Long id;
    private String brand;
    private String model;
    @Min(1)
    @NotNull(message = "The model name must not be empty")
    private Long modelId;
    private Long brandId;
    @Length(min = 2, max = 20, message = "The license plate '${validatedValue}' must be between {min} and {max} characters long")
    private String registration;
    private String vehicleStatus;
    @Max(2020)
    @Min(2000)
    private int year;
    @DecimalMin(value = "10.00", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;
    private boolean transmissionIsAutomatic;
    private boolean airConditioning;
    private boolean allWheelDrive;
    private String carTypeName;
    private String fuelType;
    @Min(1)
    @Max(5)
    private Integer doorsNumber;
    @Min(1)
    @Max(7)
    private Integer seatsNumber;
    @Min(1)
    @Max(5)
    private Integer smallBags;
    @Min(1)
    @Max(5)
    private Integer bigBags;
    @NotEmpty(message = "Color must be not empty")
    private String color;
    private Long carTypeId;
    private Float rating;
    private Long cityId;
    private Long carPictureId;
    private Long carParametersId;
    private String fileNamePath;
    private String thumbnailsPath;

    public CarDto(Long id) {
        this.id = id;
    }

    public static class CarDtoBuilder {
        private Long id;
        private String brand;
        private String model;
        private Long modelId;
        private Long brandId;
        private String registration;
        private String vehicleStatus;
        private int year;
        private BigDecimal price;
        private boolean transmissionIsAutomatic;
        private boolean airConditioning;
        private boolean allWheelDrive;
        private String carTypeName;
        private String fuelType;
        private Integer doorsNumber;
        private Integer seatsNumber;
        private Integer smallBags;
        private Integer bigBags;
        private String color;
        private Long carTypeId;
        private Float rating;
        private Long cityId;
        private Long carPictureId;
        private Long carParametersId;
        private String fileNamePath;
        private String thumbnailsPath;

        public CarDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarDtoBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarDtoBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarDtoBuilder modelId(Long modelId) {
            this.modelId = modelId;
            return this;
        }

        public CarDtoBuilder brandId(Long brandId) {
            this.brandId = brandId;
            return this;
        }

        public CarDtoBuilder registration(String registration) {
            this.registration = registration;
            return this;
        }

        public CarDtoBuilder vehicleStatus(String vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
            return this;
        }

        public CarDtoBuilder year(int year) {
            this.year = year;
            return this;
        }

        public CarDtoBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CarDtoBuilder transmissionIsAutomatic(boolean transmissionIsAutomatic) {
            this.transmissionIsAutomatic = transmissionIsAutomatic;
            return this;
        }

        public CarDtoBuilder airConditioning(boolean airConditioning) {
            this.airConditioning = airConditioning;
            return this;
        }

        public CarDtoBuilder allWheelDrive(boolean allWheelDrive) {
            this.allWheelDrive = allWheelDrive;
            return this;
        }

        public CarDtoBuilder carTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
            return this;
        }

        public CarDtoBuilder fuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }

        public CarDtoBuilder doorsNumber(Integer doorsNumber) {
            this.doorsNumber = doorsNumber;
            return this;
        }

        public CarDtoBuilder seatsNumber(Integer seatsNumber) {
            this.seatsNumber = seatsNumber;
            return this;
        }

        public CarDtoBuilder smallBags(Integer smallBags) {
            this.smallBags = smallBags;
            return this;
        }

        public CarDtoBuilder bigBags(Integer bigBags) {
            this.bigBags = bigBags;
            return this;
        }

        public CarDtoBuilder color(String color) {
            this.color = color;
            return this;
        }

        public CarDtoBuilder carTypeId(Long carTypeId) {
            this.carTypeId = carTypeId;
            return this;
        }

        public CarDtoBuilder rating(Float rating) {
            this.rating = rating;
            return this;
        }

        public CarDtoBuilder cityId(Long cityId) {
            this.cityId = cityId;
            return this;
        }

        public CarDtoBuilder carPictureId(Long carPictureId) {
            this.carPictureId = carPictureId;
            return this;
        }

        public CarDtoBuilder carParametersId(Long carParametersId) {
            this.carParametersId = carParametersId;
            return this;
        }

        public CarDtoBuilder fileNamePath(String fileNamePath) {
            this.fileNamePath = fileNamePath;
            return this;
        }

        public CarDtoBuilder thumbnailsPath(String thumbnailsPath) {
            this.thumbnailsPath = thumbnailsPath;
            return this;
        }

        public CarDto build() {
            return new CarDto(id, brand, model, modelId, brandId, registration, vehicleStatus, year, price, transmissionIsAutomatic, airConditioning, allWheelDrive, carTypeName, fuelType, doorsNumber, seatsNumber, smallBags, bigBags, color, carTypeId, rating, cityId, carPictureId, carParametersId, fileNamePath, thumbnailsPath);
        }
    }
}
