package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import com.mnidecki.cardoor.services.DBService.CarBrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class CarBrandModelMapperTest {

    @InjectMocks
    private CarBrandModelMapper carBrandModelMapper;
    @Mock
    private CarBrandService  carBrandService;

    public CarBrandModelDto getCarBrandModelDto() {
        return new CarBrandModelDto(2L, "Sorento", 1L);
    }

    private CarBrandModel getCarBarandModel() {
        CarBrand carBrand = new CarBrand(1L, "Kia");
        CarBrandModel sorento = new CarBrandModel(2L, "Sorento", carBrand);
        sorento.getBrand().getModels().add(sorento);
        return sorento;
    }

    @Test
    public void mapToCarBrandModel() {
        //Given
        CarBrandModelDto carBrandModelDto = getCarBrandModelDto();
        CarBrand brand = getCarBarandModel().getBrand();

        when(carBrandService.findByID(carBrandModelDto.getBrandId())).thenReturn(brand);

        //When
        CarBrandModel carBrandModel = carBrandModelMapper.mapToCarBrandModel(carBrandModelDto);

        //Then
        assertEquals(Long.valueOf(2),carBrandModel.getId());
        assertEquals(Long.valueOf(1),carBrandModel.getBrand().getId());
        assertEquals("Kia",carBrandModel.getBrand().getBrand());
        assertEquals("Sorento",carBrandModel.getModel());
    }

    @Test
    public void ShouldMapToCarBrandModelDto() {
        //Given
        CarBrandModel carBrandModel = getCarBarandModel();

        //When
        CarBrandModelDto carBrandModelDto = carBrandModelMapper.mapToCarBrandModelDto(carBrandModel);

        //
        assertEquals(Long.valueOf(2),carBrandModelDto.getId());
        assertEquals(Long.valueOf(1),carBrandModelDto.getBrandId());
        assertEquals("Sorento",carBrandModelDto.getModel());
    }

    @Test
    public void mapToCarBrandModelDtoList() {
        List<CarBrandModel> carBrandModelList = Collections.singletonList(getCarBarandModel());

        //When
        List<CarBrandModelDto> carBrandModelDtoList = carBrandModelMapper.mapToCarBrandModelDtoList(carBrandModelList);
        CarBrandModelDto carBrandModelDto = carBrandModelDtoList.get(0);

        //Then
        assertEquals(1,carBrandModelDtoList.size());
        assertEquals(Long.valueOf(2),carBrandModelDto.getId());
        assertEquals(Long.valueOf(1),carBrandModelDto.getBrandId());
        assertEquals("Sorento",carBrandModelDto.getModel());

    }

    @Test
    public void mapToCarBrandModelList() {
        List<CarBrandModelDto> carBrandModelDtoList = Collections.singletonList(getCarBrandModelDto());
        CarBrand brand = getCarBarandModel().getBrand();

        when(carBrandService.findByID(carBrandModelDtoList.get(0).getBrandId())).thenReturn(brand);

        //When
        List<CarBrandModel> carBrandModelList = carBrandModelMapper.mapToCarBrandModelList(carBrandModelDtoList);
        CarBrandModel carBrandModel = carBrandModelList.get(0);

        //Then
        assertEquals(1,carBrandModelList.size());
        assertEquals(Long.valueOf(2),carBrandModel.getId());
        assertEquals(Long.valueOf(1),carBrandModel.getBrand().getId());
        assertEquals("Kia",carBrandModel.getBrand().getBrand());
        assertEquals("Sorento",carBrandModel.getModel());

    }
}