package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.repository.CarTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarTypeServiceTest {

    @InjectMocks
    private CarTypeService carTypeService;
    @Mock
    private CarTypeRepository carTypeRepository;

    public CarType getCarType() {
        CarParameters carParameters = new CarParameters.CarParametersBuilder()
                .id(1L)
                .airConditioning(true)
                .bigBags(2)
                .seatsNumber(5)
                .allWheelDrive(true)
                .doorsNumber(2)
                .smallBags(2)
                .fuelType("ON")
                .year(2015)
                .transmissionIsAutomatic(true)
                .color("Black")
                .build();

        CarType carType = new CarType(1L, "Suv", Collections.singletonList(carParameters));
        carParameters.setType(carType);
        return carType;
    }

    @Test
    public void shouldFindAllCarType() {
        //Given
        List<CarType> carTypeList = new ArrayList<>();
        carTypeList.add(getCarType());

        when(carTypeRepository.findAll()).thenReturn(carTypeList);

        //When
        List<CarType> founded = carTypeService.findAll();
        CarType foundedType = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals("Suv",foundedType.getType());
        assertEquals(Long.valueOf(1), foundedType.getId());
        assertEquals(1, foundedType.getParameters().size());
        assertEquals(Long.valueOf(1),foundedType.getParameters().get(0).getId());
        assertTrue(foundedType.getParameters().get(0).isAirConditioning());
        assertTrue(foundedType.getParameters().get(0).isAllWheelDrive());
        assertTrue(foundedType.getParameters().get(0).isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),foundedType.getParameters().get(0).getBigBags());
        assertEquals(Integer.valueOf(5),foundedType.getParameters().get(0).getSeatsNumber());
        assertEquals(Integer.valueOf(2),foundedType.getParameters().get(0).getDoorsNumber());
        assertEquals(Integer.valueOf(2),foundedType.getParameters().get(0).getSmallBags());
        assertEquals(Integer.valueOf(2015),foundedType.getParameters().get(0).getYear());
        assertEquals("Black",foundedType.getParameters().get(0).getColor());
        assertEquals("ON",foundedType.getParameters().get(0).getFuelType());

    }


    @Test
    public void shouldSavePicture() {
        //Given
        CarType carType = getCarType();

        when(carTypeRepository.save(carType)).thenReturn(carType);

        //When
        CarType founded = carTypeService.save(carType);


        //Then
        assertEquals("Suv",founded.getType());
        assertEquals(Long.valueOf(1), founded.getId());
        assertEquals(1, founded.getParameters().size());
        assertEquals(Long.valueOf(1),founded.getParameters().get(0).getId());
        assertTrue(founded.getParameters().get(0).isAirConditioning());
        assertTrue(founded.getParameters().get(0).isAllWheelDrive());
        assertTrue(founded.getParameters().get(0).isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getBigBags());
        assertEquals(Integer.valueOf(5),founded.getParameters().get(0).getSeatsNumber());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getDoorsNumber());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getSmallBags());
        assertEquals(Integer.valueOf(2015),founded.getParameters().get(0).getYear());
        assertEquals("Black",founded.getParameters().get(0).getColor());
        assertEquals("ON",founded.getParameters().get(0).getFuelType());
    }

    @Test
    public void findById() {
         //Given
        Optional<CarType> carType = Optional.of(getCarType());

        when(carTypeRepository.findById(carType.get().getId())).thenReturn(carType);

        //When
        CarType founded = carTypeService.getById(carType.get().getId());

        //Then
        assertEquals("Suv",founded.getType());
        assertEquals(Long.valueOf(1), founded.getId());
        assertEquals(1, founded.getParameters().size());
        assertEquals(Long.valueOf(1),founded.getParameters().get(0).getId());
        assertTrue(founded.getParameters().get(0).isAirConditioning());
        assertTrue(founded.getParameters().get(0).isAllWheelDrive());
        assertTrue(founded.getParameters().get(0).isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getBigBags());
        assertEquals(Integer.valueOf(5),founded.getParameters().get(0).getSeatsNumber());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getDoorsNumber());
        assertEquals(Integer.valueOf(2),founded.getParameters().get(0).getSmallBags());
        assertEquals(Integer.valueOf(2015),founded.getParameters().get(0).getYear());
        assertEquals("Black",founded.getParameters().get(0).getColor());
        assertEquals("ON",founded.getParameters().get(0).getFuelType());
    }

    @Test
    public void ShoulGetDefaultCarPictureByFindByUnexistingId() {
        //Given
        CarType unknownType = new CarType(1L,"Unknown");
        String unknown = "Unknown";
        when(carTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(carTypeRepository.findCarTypeByType(unknown)).thenReturn(unknownType);

        //When
        CarType foundedType = carTypeService.getById(0L);

        //Then
        assertEquals(Long.valueOf(1),foundedType.getId());
        assertEquals("Unknown",foundedType.getType());
    }

    @Test
    public void deleteById() {
        //Given
        doNothing().when(carTypeRepository).deleteById(anyLong());

        //When
        carTypeService.deleteById(1L);

        //Then
        verify(carTypeRepository, times(1)).deleteById(1L);
    }

}