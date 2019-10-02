package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.domain.dto.CarDto;
import com.mnidecki.cardoor.services.DBService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    @Autowired
    private DBLocationService cityService;
    @Autowired
    private DBCarTypeService typeService;
    @Autowired
    private DBCarPicture carPictureService;
    @Autowired
    private DBCarParameters carParametersService;
    @Autowired
    private DBCarBrandModelService carBrandModelService;
    @Autowired
    private DBStarService starService;

    public Car mapToCar(final CarDto carDto) {
        return new Car.CarBuilder()
                .id(carDto.getId())
                .model(carBrandModelService.findByID(carDto.getModelId()))
                .registration(carDto.getRegistration())
                .vehicleStatus(carDto.getVehicleStatus())
                .carParameters(carParametersService.findById(carDto.getCarParametersId()).orElse(null))
                .price(carDto.getPrice())
                .location(cityService.findById(carDto.getCityId()))
                .build();
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto.CarDtoBuilder()
                .id(car.getId())
                .brand(car.getModel().getBrand().getBrand())
                .model(car.getModel().getModel())
                .modelId(car.getModel().getId())
                .brandId(car.getModel().getBrand().getId())
                .registration(car.getRegistration())
                .vehicleStatus(car.getVehicleStatus())
                .year(car.getCarParameters().getYear())
                .price(car.getPrice())
                .transmissionIsAutomatic(car.getCarParameters().isTransmissionIsAutomatic())
                .airConditioning(car.getCarParameters().isAirConditioning())
                .allWheelDrive(car.getCarParameters().isAllWheelDrive())
                .carTypeName(typeService.getById(car.getCarParameters().getType().getId()).getType())
                .fuelType(car.getCarParameters().getFuelType())
                .doorsNumber(car.getCarParameters().getDoorsNumber())
                .seatsNumber(car.getCarParameters().getSeatsNumber())
                .smallBags(car.getCarParameters().getSmallBags())
                .bigBags(car.getCarParameters().getBigBags())
                .color(car.getCarParameters().getColor())
                .carTypeId(car.getCarParameters().getType().getId())
                .rating(starService.findById(car.getModel().getId()).getRatingAverage())
                .cityId(car.getLocation().getId())
                .carPictureId(car.getCarParameters().getCarPicture().getId())
                .carParametersId(car.getCarParameters().getId())
                .fileNamePath(carPictureService.findById(car.getCarParameters().getCarPicture().getId()).getFileNamePath())
                .thumbnailsPath(carPictureService.findById(car.getCarParameters().getCarPicture().getId()).getThumbnailsPath())
                .build();
    }


    public List<CarDto> mapToCarDtoList(final List<Car> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }

    public CarParameters mapToCarParameters(final CarDto carDto) {
        return new CarParameters(
                carDto.getFuelType(),
                carDto.isAllWheelDrive(),
                carDto.getDoorsNumber(),
                carDto.getSeatsNumber(),
                carDto.getBigBags(),
                carDto.getSmallBags(),
                carDto.getColor(),
                carDto.getYear(),
                carDto.isTransmissionIsAutomatic(),
                carDto.isAirConditioning(),
                typeService.getById(carDto.getCarTypeId()),
                carPictureService.findById(carDto.getCarPictureId()));
    }

}

