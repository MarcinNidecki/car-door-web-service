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


}
