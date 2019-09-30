package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.dto.CarBrandDto;
import com.mnidecki.cardoor.services.DBService.DBCarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarBrandMapper {

    @Autowired
    DBCarBrandService carBrandService;
    @Autowired
    CarBrandMapper brandMapper;
    @Autowired
    CarBrandModelMapper modelMapper;

    public CarBrand mapToCarBrand(final CarBrandDto carBrandDto) {
        return new CarBrand(
                carBrandDto.getId(),
                carBrandDto.getBrand(),
                modelMapper.mapToCarBrandModelList(carBrandDto.getModels()));
    }

    public CarBrandDto mapToCarBrandDto(final CarBrand carBrand) {
        return new CarBrandDto(
                carBrand.getId(),
                carBrand.getBrand(),
                modelMapper.mapToCarBrandModelDtoList(carBrand.getModels()));
    }

    public List<CarBrandDto> mapToCarBrandDtoList(final List<CarBrand> carBrands) {
        return carBrands.stream()
                .map(b -> new CarBrandDto(b.getId(),
                        b.getBrand(),
                        modelMapper.mapToCarBrandModelDtoList(b.getModels())
                ))
                .collect(Collectors.toList());
    }

    public List<CarBrand> mapToCarBrandList(final List<CarBrandDto> carBrandDtoList) {
        return carBrandDtoList.stream()
                .map(b -> new CarBrand(b.getId(),
                        b.getBrand(),
                        modelMapper.mapToCarBrandModelList(b.getModels())))
                .collect(Collectors.toList());
    }
}
