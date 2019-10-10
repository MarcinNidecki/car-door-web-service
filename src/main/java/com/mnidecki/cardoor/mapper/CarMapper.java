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
    private LocationService cityService;
    @Autowired
    private CarTypeService typeService;
    @Autowired
    private CarPictureService carPictureService;
    @Autowired
    private CarBrandModelService carBrandModelService;
    @Autowired
    private StarService starService;

    public Car mapToCar(final CarDto carDto) {
        Car car = new Car.CarBuilder()
                .model(carBrandModelService.findByID(carDto.getModelId()))
                .registration(carDto.getRegistration())
                .vehicleStatus(carDto.getVehicleStatus())
                .carParameters(mapToCarParameters(carDto))
                .price(carDto.getPrice())
                .location(cityService.findById(carDto.getCityId()))
                .build();

        if(carDto.getId()!=null) {
            car.setId(carDto.getId());
        }
        return car;
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
        CarParameters carParameters = new CarParameters.CarParametersBuilder()
                .fuelType(carDto.getFuelType())
                .allWheelDrive(carDto.isAllWheelDrive())
                .doorsNumber(carDto.getDoorsNumber())
                .seatsNumber(carDto.getSeatsNumber())
                .bigBags(carDto.getBigBags())
                .smallBags(carDto.getSmallBags())
                .color(carDto.getColor())
                .year(carDto.getYear())
                .transmissionIsAutomatic(carDto.isTransmissionIsAutomatic())
                .airConditioning(carDto.isAirConditioning())
                .type(typeService.getById(carDto.getCarTypeId()))
                .carPicture(carPictureService.findById(carDto.getCarPictureId()))
                .build();

        if (carDto.getCarParametersId()!=null) {
            carParameters.setId(carDto.getCarParametersId());
        }
        return carParameters;
    }

}

