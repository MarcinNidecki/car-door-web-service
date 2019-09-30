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
        return new Car(
                carDto.getId(),
                carBrandModelService.findByID(carDto.getModelId()),
                carDto.getRegistration(),
                carDto.getVehicleStatus(),
                carParametersService.findById(carDto.getCarParametersId()).orElse(null),
                carDto.getPrice(),
                cityService.findById(carDto.getCityId()));
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getId(),
                car.getModel().getBrand().getBrand(),
                car.getModel().getModel(),
                car.getModel().getId(),
                car.getModel().getBrand().getId(),
                car.getRegistration(),
                car.getVehicleStatus(),
                car.getCarParameters().getYear(),
                car.getPrice(),
                car.getCarParameters().isTransmissionIsAutomatic(),
                car.getCarParameters().isAirConditioning(),
                car.getCarParameters().isAllWheelDrive(),
                typeService.getById(car.getCarParameters().getType().getId()).getType(),
                car.getCarParameters().getFuelType(),
                car.getCarParameters().getDoorsNumber(),
                car.getCarParameters().getSeatsNumber(),
                car.getCarParameters().getSmallBags(),
                car.getCarParameters().getBigBags(),
                car.getCarParameters().getColor(),
                car.getCarParameters().getType().getId(),
                starService.findById(car.getModel().getId()).getRatingAverage(),
                car.getLocation().getId(),
                car.getCarParameters().getCarPicture().getId(),
                car.getCarParameters().getId(),
                carPictureService.findById(car.getCarParameters().getCarPicture().getId()).getFileNamePath(),
                carPictureService.findById(car.getCarParameters().getCarPicture().getId()).getThumbnailsPath());
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

