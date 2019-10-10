package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.domain.dto.CarTypeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CarTypeMapperTest {

    @InjectMocks
    private CarTypeMapper carTypeMapper;


    @Test
    public void ShouldMapToCarType() {
        //Given
        CarTypeDto carTypeDto = new CarTypeDto(1L,"Suv");

        //When
        CarType carType = carTypeMapper.mapToCarType(carTypeDto);

        //Then
        assertEquals(Long.valueOf(1),carType.getId());
        assertEquals("Suv",carType.getType());
    }

    @Test
    public void mapToCarTypeDto() {
        //Given
        CarType carType = new CarType(1L,"Suv");

        //When
        CarTypeDto carTypeDto = carTypeMapper.mapToCarTypeDto(carType);

        //Then
        assertEquals(Long.valueOf(1),carTypeDto.getId());
        assertEquals("Suv",carTypeDto.getType());
    }

    @Test
    public void shouldMapToCarTypeDtoList() {
        //Given
        CarType carType = new CarType(1L,"Suv");
        CarType carType2 = new CarType(2L,"ECO");
        List<CarType> carTypeList = new ArrayList<>();
        carTypeList.add(carType);
        carTypeList.add(carType2);

        //When
        List<CarTypeDto> carTypeDtoList = carTypeMapper.mapToCarTypeDtoList(carTypeList);

        //Then
        assertEquals(2,carTypeDtoList.size());
        assertEquals(Long.valueOf(1),carTypeDtoList.get(0).getId());
        assertEquals("Suv",carTypeDtoList.get(0).getType());
        assertEquals(Long.valueOf(2),carTypeDtoList.get(1).getId());
        assertEquals("ECO",carTypeDtoList.get(1).getType());
    }
}