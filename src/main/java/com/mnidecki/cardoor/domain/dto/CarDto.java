package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Serializable {

    private Long id;
    private String brand;
    private String model;
    @Min(1)
    @NotNull(message = "The model name must not be empty")
    private Long modelId;
    private Long brandId;
    @Length(min = 2, max = 10, message = "The license plate must be between {min} and {max} " +
            "characters long")
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
    private Integer doorsNumber;
    private Integer seatsNumber;
    private Integer smallBags;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarDto carDto = (CarDto) o;

        if (year != carDto.year) return false;
        if (transmissionIsAutomatic != carDto.transmissionIsAutomatic) return false;
        if (airConditioning != carDto.airConditioning) return false;
        if (allWheelDrive != carDto.allWheelDrive) return false;
        if (id != null ? !id.equals(carDto.id) : carDto.id != null) return false;
        if (brand != null ? !brand.equals(carDto.brand) : carDto.brand != null) return false;
        if (model != null ? !model.equals(carDto.model) : carDto.model != null) return false;
        if (modelId != null ? !modelId.equals(carDto.modelId) : carDto.modelId != null) return false;
        if (brandId != null ? !brandId.equals(carDto.brandId) : carDto.brandId != null) return false;
        if (registration != null ? !registration.equals(carDto.registration) : carDto.registration != null)
            return false;
        if (vehicleStatus != null ? !vehicleStatus.equals(carDto.vehicleStatus) : carDto.vehicleStatus != null)
            return false;
        if (price != null ? !price.equals(carDto.price) : carDto.price != null) return false;
        if (carTypeName != null ? !carTypeName.equals(carDto.carTypeName) : carDto.carTypeName != null) return false;
        if (fuelType != null ? !fuelType.equals(carDto.fuelType) : carDto.fuelType != null) return false;
        if (doorsNumber != null ? !doorsNumber.equals(carDto.doorsNumber) : carDto.doorsNumber != null) return false;
        if (seatsNumber != null ? !seatsNumber.equals(carDto.seatsNumber) : carDto.seatsNumber != null) return false;
        if (smallBags != null ? !smallBags.equals(carDto.smallBags) : carDto.smallBags != null) return false;
        if (bigBags != null ? !bigBags.equals(carDto.bigBags) : carDto.bigBags != null) return false;
        if (color != null ? !color.equals(carDto.color) : carDto.color != null) return false;
        if (carTypeId != null ? !carTypeId.equals(carDto.carTypeId) : carDto.carTypeId != null) return false;
        if (rating != null ? !rating.equals(carDto.rating) : carDto.rating != null) return false;
        if (cityId != null ? !cityId.equals(carDto.cityId) : carDto.cityId != null) return false;
        if (carPictureId != null ? !carPictureId.equals(carDto.carPictureId) : carDto.carPictureId != null)
            return false;
        if (carParametersId != null ? !carParametersId.equals(carDto.carParametersId) : carDto.carParametersId != null)
            return false;
        if (fileNamePath != null ? !fileNamePath.equals(carDto.fileNamePath) : carDto.fileNamePath != null)
            return false;
        return thumbnailsPath != null ? thumbnailsPath.equals(carDto.thumbnailsPath) : carDto.thumbnailsPath == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        result = 31 * result + (vehicleStatus != null ? vehicleStatus.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (transmissionIsAutomatic ? 1 : 0);
        result = 31 * result + (airConditioning ? 1 : 0);
        result = 31 * result + (allWheelDrive ? 1 : 0);
        result = 31 * result + (carTypeName != null ? carTypeName.hashCode() : 0);
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (doorsNumber != null ? doorsNumber.hashCode() : 0);
        result = 31 * result + (seatsNumber != null ? seatsNumber.hashCode() : 0);
        result = 31 * result + (smallBags != null ? smallBags.hashCode() : 0);
        result = 31 * result + (bigBags != null ? bigBags.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (carTypeId != null ? carTypeId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (carPictureId != null ? carPictureId.hashCode() : 0);
        result = 31 * result + (carParametersId != null ? carParametersId.hashCode() : 0);
        result = 31 * result + (fileNamePath != null ? fileNamePath.hashCode() : 0);
        result = 31 * result + (thumbnailsPath != null ? thumbnailsPath.hashCode() : 0);
        return result;
    }
}
