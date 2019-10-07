package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import com.mnidecki.cardoor.services.DBService.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarBrandModelMapper {

    @Autowired
    CarBrandService carBrandService;

    public CarBrandModel mapToCarBrandModel(final CarBrandModelDto carBrandModelDto) {
        return new CarBrandModel(
                carBrandModelDto.getId(),
                carBrandModelDto.getModel(),
                carBrandService.findByID(carBrandModelDto.getBrandId()).orElse(new CarBrand())
        );
    }

    public CarBrandModelDto mapToCarBrandModelDto(final CarBrandModel carBrandModel) {
        return new CarBrandModelDto(
                carBrandModel.getId(),
                carBrandModel.getModel(),
                carBrandModel.getBrand().getId());
    }

    public List<CarBrandModelDto> mapToCarBrandModelDtoList(final List<CarBrandModel> carBrandModels) {
        return carBrandModels.stream()
                .map(this::mapToCarBrandModelDto)
                .collect(Collectors.toList());
    }

    public List<CarBrandModel> mapToCarBrandModelList(final List<CarBrandModelDto> carBrandModelDtoList) {
        return carBrandModelDtoList.stream()
                .map(this::mapToCarBrandModel)
                .collect(Collectors.toList());
    }
}
