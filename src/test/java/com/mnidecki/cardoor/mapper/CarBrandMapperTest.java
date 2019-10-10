package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandDto;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarBrandMapperTest {

    @InjectMocks
    private CarBrandMapper carBrandMapper;
    @Mock
    private CarBrandModelMapper carModelMapper;


    public CarBrandDto getBrandDto() {

        CarBrandDto brandDto = new CarBrandDto(1L, "Kia", new ArrayList<>());
        CarBrandModelDto sorentoDto = new CarBrandModelDto(2L, "Sorento", 1L);
        brandDto.getModels().add(sorentoDto);
        return brandDto;
    }

    private CarBrand getCarBarand() {

        CarBrand carBrand = new CarBrand(1L, "Kia");
        CarBrandModel sorento = new CarBrandModel(2L, "Sorento", carBrand);
        carBrand.getModels().add(sorento);
        return carBrand;
    }


    @Test
    public void ShouldMapToCarBrandDto() {
        //Given
        CarBrand carBrand = getCarBarand();
        List<CarBrandModel> modelList = carBrand.getModels();
        List<CarBrandModelDto> modelListDto = getBrandDto().getModels();

        when(carModelMapper.mapToCarBrandModelDtoList(modelList)).thenReturn(modelListDto);
        //When
        CarBrandDto carBrandDto = carBrandMapper.mapToCarBrandDto(carBrand);

        //Then
        assertEquals(Long.valueOf(1),carBrandDto.getId());
        assertEquals("Kia",carBrandDto.getBrand());
        assertEquals(1,carBrandDto.getModels().size());
        assertEquals(Long.valueOf(2),carBrandDto.getModels().get(0).getId());
        assertEquals("Sorento",carBrandDto.getModels().get(0).getModel());
        assertEquals(Long.valueOf(1),carBrandDto.getModels().get(0).getBrandId());
    }


    @Test
    public void ShouldMapToCarBrand() {
        //Given
        CarBrandDto carBrandDto = getBrandDto();
        List<CarBrandModel> modelList = getCarBarand().getModels();
        List<CarBrandModelDto> modelListDto = getBrandDto().getModels();

        when(carModelMapper.mapToCarBrandModelList(carBrandDto.getModels())).thenReturn(modelList);
        //When
        CarBrand carBrand = carBrandMapper.mapToCarBrand(carBrandDto);

        //Then
        assertEquals(Long.valueOf(1),carBrand.getId());
        assertEquals("Kia",carBrand.getBrand());
        assertEquals(1,carBrand.getModels().size());
        assertEquals(Long.valueOf(2),carBrand.getModels().get(0).getId());
        assertEquals("Sorento",carBrand.getModels().get(0).getModel());
            }

    @Test
    public void ShouldMapToCarBrandDtoList() {
        //Given
        List<CarBrand> brandList = Collections.singletonList(getCarBarand());
        List<CarBrandModelDto> modelListDto = getBrandDto().getModels();

        when(carModelMapper.mapToCarBrandModelDtoList(brandList.get(0).getModels())).thenReturn(modelListDto);

        //When
        List<CarBrandDto> carBrandDtoList = carBrandMapper.mapToCarBrandDtoList(brandList);
        CarBrandDto carBrandDto = carBrandDtoList.get(0);

        //Then
        assertEquals(1,carBrandDto.getModels().size());
        assertEquals(Long.valueOf(1),carBrandDto.getId());
        assertEquals("Kia",carBrandDto.getBrand());
        assertEquals(1,carBrandDto.getModels().size());
        assertEquals(Long.valueOf(2),carBrandDto.getModels().get(0).getId());
        assertEquals("Sorento",carBrandDto.getModels().get(0).getModel());
        assertEquals(Long.valueOf(1),carBrandDto.getModels().get(0).getBrandId());
    }
}