package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.repository.CarParametersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarParametersServiceTest {

    @InjectMocks
    private CarParametersService carParametersService;
    @Mock
    private CarParametersRepository carParametersRepository;

    public CarParameters getCarParameters() {
        CarPicture carPicture = new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath", "thumbNailsPath",
                "jpg", LocalDate.now());

        CarType carType = new CarType(1L, "Suv");

        CarParameters carParameters = new CarParameters.CarParametersBuilder()
                .id(1L)
                .carPicture(carPicture)
                .airConditioning(true)
                .bigBags(2)
                .seatsNumber(5)
                .allWheelDrive(true)
                .doorsNumber(2)
                .smallBags(2)
                .fuelType("ON")
                .year(2015)
                .transmissionIsAutomatic(true)
                .type(carType)
                .color("Black")
                .build();

        Car car = new Car.CarBuilder()
                .id(234L)
                .price(new BigDecimal(123))
                .vehicleStatus("Good")
                .registration("SZO121212")
                .carParameters(carParameters)
                .build();

        carParameters.setCar(car);

        return carParameters;
    }

    @Test
    public void shouldFindAllCarParameters() {
        //Given
        List<CarParameters> carParameters = new ArrayList<>();
        carParameters.add(getCarParameters());

        when(carParametersRepository.findAll()).thenReturn(carParameters);

        //When
        List<CarParameters> founded = carParametersService.findAll();
        CarParameters foundedParameters = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),foundedParameters.getId());
        assertTrue(foundedParameters.isAirConditioning());
        assertTrue(foundedParameters.isAllWheelDrive());
        assertTrue(foundedParameters.isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),foundedParameters.getBigBags());
        assertEquals(Integer.valueOf(5),foundedParameters.getSeatsNumber());
        assertEquals(Integer.valueOf(2),foundedParameters.getDoorsNumber());
        assertEquals(Integer.valueOf(2),foundedParameters.getSmallBags());
        assertEquals(Integer.valueOf(2015),foundedParameters.getYear());
        assertEquals("Black",foundedParameters.getColor());
        assertEquals("fileNamePath",foundedParameters.getCarPicture().getFileNamePath());
        assertEquals("thumbNailsPath",foundedParameters.getCarPicture().getThumbnailsPath());
        assertEquals("filename",foundedParameters.getCarPicture().getFileName());
        assertEquals("thumbnails",foundedParameters.getCarPicture().getThumbnails());
        assertEquals("description",foundedParameters.getCarPicture().getDescriptions());
        assertEquals("jpg",foundedParameters.getCarPicture().getFileExtension());
        assertEquals(Long.valueOf(1),foundedParameters.getCarPicture().getId());
        assertEquals("Suv",foundedParameters.getType().getType());
        assertEquals(Long.valueOf(1),foundedParameters.getType().getId());
        assertEquals("Good",foundedParameters.getCar().getVehicleStatus());
        assertEquals("SZO121212",foundedParameters.getCar().getRegistration());
        assertEquals(Long.valueOf(234),foundedParameters.getCar().getId());
        assertEquals(BigDecimal.valueOf(123),foundedParameters.getCar().getPrice());

    }

    @Test
    public void ShouldFindOptionalOfCarParametersById() {
        //Given
        Optional<CarParameters> optionalParameters = Optional.of(getCarParameters());

        when(carParametersRepository.findById(anyLong())).thenReturn(optionalParameters);

        //When
        CarParameters foundedParameters = carParametersService.findById(1L);

        //Then
        assertEquals(Long.valueOf(1),foundedParameters.getId());
        assertTrue(foundedParameters.isAirConditioning());
        assertTrue(foundedParameters.isAllWheelDrive());
        assertTrue(foundedParameters.isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),foundedParameters.getBigBags());
        assertEquals(Integer.valueOf(5),foundedParameters.getSeatsNumber());
        assertEquals(Integer.valueOf(2),foundedParameters.getDoorsNumber());
        assertEquals(Integer.valueOf(2),foundedParameters.getSmallBags());
        assertEquals(Integer.valueOf(2015),foundedParameters.getYear());
        assertEquals("Black",foundedParameters.getColor());
        assertEquals("fileNamePath",foundedParameters.getCarPicture().getFileNamePath());
        assertEquals("thumbNailsPath",foundedParameters.getCarPicture().getThumbnailsPath());
        assertEquals("filename",foundedParameters.getCarPicture().getFileName());
        assertEquals("thumbnails",foundedParameters.getCarPicture().getThumbnails());
        assertEquals("description",foundedParameters.getCarPicture().getDescriptions());
        assertEquals("jpg",foundedParameters.getCarPicture().getFileExtension());
        assertEquals(Long.valueOf(1),foundedParameters.getCarPicture().getId());
        assertEquals("Suv",foundedParameters.getType().getType());
        assertEquals(Long.valueOf(1),foundedParameters.getType().getId());
        assertEquals("Good",foundedParameters.getCar().getVehicleStatus());
        assertEquals("SZO121212",foundedParameters.getCar().getRegistration());
        assertEquals(Long.valueOf(234),foundedParameters.getCar().getId());
        assertEquals(BigDecimal.valueOf(123),foundedParameters.getCar().getPrice());
    }

    @Test
    public void ShouldFindEmptyOptionalOfCarParametersById() {
        //Given
        when(carParametersRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        CarParameters foundedParameters = carParametersService.findById(1L);

        //Then
        assertEquals(foundedParameters, new CarParameters());

    }

    @Test
    public void shouldSaveCarParameters() {
        //Given
        CarParameters carParameters = getCarParameters();

        when(carParametersRepository.save(carParameters)).thenReturn(carParameters);

        //When
        CarParameters savedParameters = carParametersService.saveCarParameters(carParameters);


        //Then
        assertNotNull(savedParameters);
        assertEquals(Long.valueOf(1),savedParameters.getId());
        assertTrue(savedParameters.isAirConditioning());
        assertTrue(savedParameters.isAllWheelDrive());
        assertTrue(savedParameters.isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),savedParameters.getBigBags());
        assertEquals(Integer.valueOf(5),savedParameters.getSeatsNumber());
        assertEquals(Integer.valueOf(2),savedParameters.getDoorsNumber());
        assertEquals(Integer.valueOf(2),savedParameters.getSmallBags());
        assertEquals(Integer.valueOf(2015),savedParameters.getYear());
        assertEquals("Black",savedParameters.getColor());
        assertEquals("fileNamePath",savedParameters.getCarPicture().getFileNamePath());
        assertEquals("thumbNailsPath",savedParameters.getCarPicture().getThumbnailsPath());
        assertEquals("filename",savedParameters.getCarPicture().getFileName());
        assertEquals("thumbnails",savedParameters.getCarPicture().getThumbnails());
        assertEquals("description",savedParameters.getCarPicture().getDescriptions());
        assertEquals("jpg",savedParameters.getCarPicture().getFileExtension());
        assertEquals(Long.valueOf(1),savedParameters.getCarPicture().getId());
        assertEquals("Suv",savedParameters.getType().getType());
        assertEquals(Long.valueOf(1),savedParameters.getType().getId());
        assertEquals("Good",savedParameters.getCar().getVehicleStatus());
        assertEquals("SZO121212",savedParameters.getCar().getRegistration());
        assertEquals(Long.valueOf(234),savedParameters.getCar().getId());
        assertEquals(BigDecimal.valueOf(123),savedParameters.getCar().getPrice());
    }

    @Test
    public void deleteById() {
        //Given
        doNothing().when(carParametersRepository).deleteById(anyLong());

        //When
        carParametersService.deleteById(1L);

        //Then
        verify(carParametersRepository, times(1)).deleteById(1L);
    }

    @Test
    public void countCarParametersByCarPictureId() {
        //Given
        CarParameters carParameters = getCarParameters();
        CarParameters carParameters2 = getCarParameters();
        carParameters.setId(2L);

        List<CarParameters> carParametersList = new ArrayList<>();
        carParametersList.add(carParameters);
        carParametersList.add(carParameters2);

        when(carParametersRepository.countCarParametersByCarPicture_id(carParameters.getCarPicture().getId())).thenReturn(2);

        //When
        int result = carParametersService.countCarParametersByCarPictureId(carParameters.getCarPicture().getId());

        //Then
        assertEquals(2,result);

    }
}