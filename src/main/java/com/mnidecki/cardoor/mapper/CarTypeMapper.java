package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.domain.dto.CarTypeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarTypeMapper {

    public CarType mapToCarType(final CarTypeDto carTypeDto) {
        return new CarType(
                carTypeDto.getId(),
                carTypeDto.getType()
        );
    }

    public CarTypeDto mapToCarTypeDto(final CarType carType) {
        return new CarTypeDto(
                carType.getId(),
                carType.getType());
    }

    public List<CarTypeDto> mapToCarTypeDtoList(final List<CarType> carList) {
        return carList.stream()
                .map(c -> new CarTypeDto(c.getId(),
                        c.getType()))
                .collect(Collectors.toList());
    }
}
