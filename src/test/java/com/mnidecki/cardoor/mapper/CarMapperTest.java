package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.*;
import com.mnidecki.cardoor.domain.dto.CarDto;
import com.mnidecki.cardoor.services.DBService.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarMapperTest {

    @InjectMocks
    private CarMapper carMapper;
    @Mock
    private LocationService cityService;
    @Mock
    private CarTypeService typeService;
    @Mock
    private CarPictureService carPictureService;
    @Mock
    private CarBrandModelService carBrandModelService;
    @Mock
    private StarService starService;




    public Car getCar() {
        CarPicture carPicture = new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath", "thumbNailsPath",
                "jpg", LocalDate.now());

        CarType carType = new CarType(1L, "Suv");

        CarBrand carBrand = new CarBrand(1L, "Kia");
        CarBrandModel sorento = new CarBrandModel(1L, "Sorento", carBrand);
        sorento.getBrand().getModels().add(sorento);
        Star star = new Star(1L,10F,sorento);
        sorento.setStar(star);
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
                .year(2011)
                .transmissionIsAutomatic(true)
                .type(carType)
                .color("Black")
                .build();

        Locationn location = new Locationn(1L,"Poland","Katowice","Katowice Airport","address line 1","address line 2",
                "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");
        Car car = new Car.CarBuilder()
                .id(234L)
                .model(sorento)
                .registration("SZO121212")
                .vehicleStatus("IN RENT")
                .carParameters(carParameters)
                .price(new BigDecimal(123))
                .location(location)
                .build();


        carParameters.setCar(car);

        Booking booking = new Booking.BookingBuilder()
                .id(1L)
                .car(car)
                .location(location)
                .totalCost(BigDecimal.valueOf(600))
                .startDate(Timestamp.valueOf("2020-12-11 15:00:00"))
                .returnDate(Timestamp.valueOf("2020-12-13 15:00:00"))
                .createdDate(Timestamp.valueOf("2020-12-10 15:00:00"))
                .build();

        car.setBookingsList(Collections.singleton(booking));
        return car;
    }


    public CarDto getCarDto() {
        return new CarDto.CarDtoBuilder()
                .id(234L)
                .brand("Kia")
                .model("Sorento")
                .modelId(1L)
                .brandId(1L)
                .registration("SZO121212")
                .vehicleStatus("IN RENT")
                .year(2011)
                .price(new BigDecimal(123))
                .transmissionIsAutomatic(true)
                .airConditioning(true)
                .allWheelDrive(true)
                .carTypeName("Suv")
                .fuelType("ON")
                .doorsNumber(2)
                .seatsNumber(5)
                .smallBags(2)
                .bigBags(2)
                .color("Black")
                .carTypeId(1L)
                .cityId(1L)
                .carPictureId(1L)
                .carParametersId(1L)
                .fileNamePath("fileNamePath")
                .thumbnailsPath("thumbnailsPath")
                .build();
    }


    @Test
    public void shouldMapToCarAndCarParameters() {
        //Given
        CarDto carDto = getCarDto();
        Car car = getCar();

        when(carBrandModelService.findByID(carDto.getModelId())).thenReturn(car.getModel());
        when(cityService.findById(carDto.getCityId())).thenReturn(car.getLocation());
        when(carPictureService.findById(carDto.getCarPictureId())).thenReturn(car.getCarParameters().getCarPicture());
        when(typeService.getById(carDto.getCarTypeId())).thenReturn(car.getCarParameters().getType());
        //When
        Car mappedCar = carMapper.mapToCar(carDto);

        //Then
        assertEquals(Long.valueOf(234),mappedCar.getId());
        assertEquals("IN RENT",mappedCar.getVehicleStatus());
        assertEquals("SZO121212",mappedCar.getRegistration());
        assertEquals(BigDecimal.valueOf(123),mappedCar.getPrice());

        assertEquals(Long.valueOf(1),mappedCar.getModel().getId());
        assertEquals(Long.valueOf(1),mappedCar.getModel().getBrand().getId());
        assertEquals("Kia",mappedCar.getModel().getBrand().getBrand());
        assertEquals("Sorento",mappedCar.getModel().getModel());

        assertTrue(mappedCar.getCarParameters().isAirConditioning());
        assertTrue(mappedCar.getCarParameters().isAllWheelDrive());
        assertTrue(mappedCar.getCarParameters().isTransmissionIsAutomatic());
        assertEquals(Integer.valueOf(2),mappedCar.getCarParameters().getBigBags());
        assertEquals(Integer.valueOf(5),mappedCar.getCarParameters().getSeatsNumber());
        assertEquals(Integer.valueOf(2),mappedCar.getCarParameters().getDoorsNumber());
        assertEquals(Integer.valueOf(2),mappedCar.getCarParameters().getSmallBags());
        assertEquals(Integer.valueOf(2011),mappedCar.getCarParameters().getYear());
        assertEquals("Black",mappedCar.getCarParameters().getColor());
        assertEquals("fileNamePath",mappedCar.getCarParameters().getCarPicture().getFileNamePath());
        assertEquals("thumbNailsPath",mappedCar.getCarParameters().getCarPicture().getThumbnailsPath());
        assertEquals("filename",mappedCar.getCarParameters().getCarPicture().getFileName());
        assertEquals("thumbnails",mappedCar.getCarParameters().getCarPicture().getThumbnails());
        assertEquals("description",mappedCar.getCarParameters().getCarPicture().getDescriptions());
        assertEquals("jpg",mappedCar.getCarParameters().getCarPicture().getFileExtension());
        assertEquals(Long.valueOf(1),mappedCar.getCarParameters().getCarPicture().getId());
        assertEquals("Suv",mappedCar.getCarParameters().getType().getType());
        assertEquals(Long.valueOf(1),mappedCar.getCarParameters().getType().getId());

        assertEquals(Long.valueOf(1),mappedCar.getLocation().getId());
        assertEquals("Poland",mappedCar.getLocation().getCountry());
        assertEquals("Katowice",mappedCar.getLocation().getCity());
        assertEquals("Katowice Airport",mappedCar.getLocation().getLocationName());
        assertEquals("address line 1",mappedCar.getLocation().getAddressLine());
        assertEquals("address line 2",mappedCar.getLocation().getAddressLine2());
        assertEquals("Terminal 2, office next to parking",mappedCar.getLocation().getPickUpInstructions());
        assertEquals("44-444",mappedCar.getLocation().getPostalCode());
        assertEquals("email@email.pl",mappedCar.getLocation().getEmail());
        assertEquals("555555555",mappedCar.getLocation().getPhone());
        assertEquals("Mon - Fri 10-16",mappedCar.getLocation().getOpeningHours());

    }

    @Test
    public void mapToCarDto() {
        //Given

        Car car = getCar();

        when(typeService.getById(car.getCarParameters().getType().getId())).thenReturn(car.getCarParameters().getType());
        when(starService.findById(car.getModel().getId())).thenReturn(car.getModel().getStar());
        when(carPictureService.findById(car.getCarParameters().getCarPicture().getId())).thenReturn(car.getCarParameters().getCarPicture());

        //When
        CarDto carDto = carMapper.mapToCarDto(car);

        //Then
        assertEquals(Long.valueOf(234),carDto.getId());
        assertEquals("Kia",carDto.getBrand());
        assertEquals("Sorento",carDto.getModel());
        assertEquals(Long.valueOf(1),carDto.getModelId());
        assertEquals(Long.valueOf(1),carDto.getBrandId());
        assertEquals("IN RENT",carDto.getVehicleStatus());
        assertEquals("SZO121212",carDto.getRegistration());
        assertEquals(2011,carDto.getYear());
        assertEquals(BigDecimal.valueOf(123),carDto.getPrice());
        assertTrue(carDto.isAirConditioning());
        assertTrue(carDto.isAllWheelDrive());
        assertTrue(carDto.isTransmissionIsAutomatic());
        assertEquals("Suv",carDto.getCarTypeName());
        assertEquals("ON",carDto.getFuelType());
        assertEquals(Integer.valueOf(2),carDto.getBigBags());
        assertEquals(Integer.valueOf(5),carDto.getSeatsNumber());
        assertEquals(Integer.valueOf(2),carDto.getDoorsNumber());
        assertEquals(Integer.valueOf(2),carDto.getSmallBags());
        assertEquals(Long.valueOf(1),carDto.getCarTypeId());
        assertEquals(Float.valueOf(10),carDto.getRating());
        assertEquals(Long.valueOf(1),carDto.getCityId());
        assertEquals(Long.valueOf(1),carDto.getCarPictureId());
        assertEquals("Black",carDto.getColor());
        assertEquals("fileNamePath",carDto.getFileNamePath());
        assertEquals("thumbNailsPath",carDto.getThumbnailsPath());
    }

    @Test
    public void mapToCarDtoList() {

        Car car = getCar();
        List<Car> carList = Collections.singletonList(car);

        when(typeService.getById(car.getCarParameters().getType().getId())).thenReturn(car.getCarParameters().getType());
        when(starService.findById(car.getModel().getId())).thenReturn(car.getModel().getStar());
        when(carPictureService.findById(car.getCarParameters().getCarPicture().getId())).thenReturn(car.getCarParameters().getCarPicture());

        //When
        List<CarDto> carDtoList = carMapper.mapToCarDtoList(carList);
        CarDto carDto =carDtoList.get(0);

        //Then
        assertEquals(1,carDtoList.size());
        assertEquals(Long.valueOf(234),carDto.getId());
        assertEquals("Kia",carDto.getBrand());
        assertEquals("Sorento",carDto.getModel());
        assertEquals(Long.valueOf(1),carDto.getModelId());
        assertEquals(Long.valueOf(1),carDto.getBrandId());
        assertEquals("IN RENT",carDto.getVehicleStatus());
        assertEquals("SZO121212",carDto.getRegistration());
        assertEquals(2011,carDto.getYear());
        assertEquals(BigDecimal.valueOf(123),carDto.getPrice());
        assertTrue(carDto.isAirConditioning());
        assertTrue(carDto.isAllWheelDrive());
        assertTrue(carDto.isTransmissionIsAutomatic());
        assertEquals("Suv",carDto.getCarTypeName());
        assertEquals("ON",carDto.getFuelType());
        assertEquals(Integer.valueOf(2),carDto.getBigBags());
        assertEquals(Integer.valueOf(5),carDto.getSeatsNumber());
        assertEquals(Integer.valueOf(2),carDto.getDoorsNumber());
        assertEquals(Integer.valueOf(2),carDto.getSmallBags());
        assertEquals(Long.valueOf(1),carDto.getCarTypeId());
        assertEquals(Float.valueOf(10),carDto.getRating());
        assertEquals(Long.valueOf(1),carDto.getCityId());
        assertEquals(Long.valueOf(1),carDto.getCarPictureId());
        assertEquals("Black",carDto.getColor());
        assertEquals("fileNamePath",carDto.getFileNamePath());
        assertEquals("thumbNailsPath",carDto.getThumbnailsPath());
    }


}