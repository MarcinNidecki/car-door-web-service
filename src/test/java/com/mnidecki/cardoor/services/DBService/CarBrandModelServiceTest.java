package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.domain.car.*;
import com.mnidecki.cardoor.repository.CarBrandModelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CarBrandModelServiceTest {

    @InjectMocks
    private CarBrandModelService carBrandModelService;
    @Mock
    private CarBrandModelRepository carBrandModelRepository;
    @Mock
    private StarService starService;

    private CarBrandModel getBrandModel() {
        CarBrand carBrand = new CarBrand(1L,"Kia");
        CarBrandModel carBrandModel = new CarBrandModel(1L,"Sorento",carBrand);
        carBrand.getModels().add(carBrandModel);
        Star star = new Star(1L,10F,carBrandModel);
        carBrandModel.setStar(star);
        Car car = new Car.CarBuilder()
                .id(1L)
                .registration("WWA 123456")
                .vehicleStatus("RENT")
                .price(BigDecimal.valueOf(250))
                .build();

        carBrandModel.getCars().add(car);
        Comment comment = new Comment(1L,"text",10, Timestamp.valueOf("2020-12-11 15:00:00"),null,carBrandModel);
        carBrandModel.setComments(Collections.singleton(comment));


        return carBrandModel;
    }

    @Test
    public void shouldFindAllCarModels() {
        //When
        List<CarBrandModel> carBrandModels = new ArrayList<>();
        carBrandModels.add(getBrandModel());

        when(carBrandModelRepository.findAll()).thenReturn(carBrandModels);

        //When
        List<CarBrandModel> founded = carBrandModelService.findAll();
        CarBrandModel carBrandModel = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),carBrandModel.getId());
        assertEquals("Sorento",carBrandModel.getModel());
        assertEquals(Long.valueOf(1),carBrandModel.getBrand().getId());
        assertEquals("Kia",carBrandModel.getBrand().getBrand());
        assertEquals(Long.valueOf(1),carBrandModel.getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),carBrandModel.getCars().get(0).getPrice());
        assertEquals("WWA 123456",carBrandModel.getCars().get(0).getRegistration());
        assertEquals("RENT",carBrandModel.getCars().get(0).getVehicleStatus());
        assertEquals(Float.valueOf(10),carBrandModel.getStar().getRatingAverage());
        assertEquals(Long.valueOf(1),carBrandModel.getStar().getModelId());

    }

    @Test
    public void findByID() {
        //When
        Optional<CarBrandModel> optionalModel = Optional.of(getBrandModel());

        when(carBrandModelRepository.findById(anyLong())).thenReturn(optionalModel);

        //When
        Optional<CarBrandModel> foundedModel = carBrandModelService.findByID(1L);

        //Then
        assertTrue( foundedModel.isPresent());
        assertEquals(Long.valueOf(1),foundedModel.get().getId());
        assertEquals("Sorento",foundedModel.get().getModel());
        assertEquals(Long.valueOf(1),foundedModel.get().getBrand().getId());
        assertEquals("Kia",foundedModel.get().getBrand().getBrand());
        assertEquals(Long.valueOf(1),foundedModel.get().getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),foundedModel.get().getCars().get(0).getPrice());
        assertEquals("WWA 123456",foundedModel.get().getCars().get(0).getRegistration());
        assertEquals("RENT",foundedModel.get().getCars().get(0).getVehicleStatus());
        assertEquals(Float.valueOf(10),foundedModel.get().getStar().getRatingAverage());
        assertEquals(Long.valueOf(1),foundedModel.get().getStar().getModelId());
    }

    @Test
    public void shouldFindEmptyOptionalOfBookingStatusCode() {
        //Given
        when(carBrandModelRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        Optional<CarBrandModel> foundedModel = carBrandModelService.findByID(1L);

        //Then
        assertFalse(foundedModel.isPresent());

    }

    @Test
    public void shouldSaveCarBrandModel() {
        //When
        CarBrandModel carBrandModel =getBrandModel();

        when(carBrandModelRepository.save(carBrandModel)).thenReturn(carBrandModel);

        //When
        CarBrandModel  savedModel = carBrandModelService.save(carBrandModel);

        //Then
        assertEquals(Long.valueOf(1),savedModel.getId());
        assertEquals("Sorento",savedModel.getModel());
        assertEquals(Long.valueOf(1),savedModel.getBrand().getId());
        assertEquals("Kia",savedModel.getBrand().getBrand());
        assertEquals(Long.valueOf(1),savedModel.getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),savedModel.getCars().get(0).getPrice());
        assertEquals("WWA 123456",savedModel.getCars().get(0).getRegistration());
        assertEquals("RENT",savedModel.getCars().get(0).getVehicleStatus());
        assertEquals(Float.valueOf(10),savedModel.getStar().getRatingAverage());
        assertEquals(Long.valueOf(1),savedModel.getStar().getModelId());

    }
    @Test
    public void shouldSaveCarBrandModelAndCreateStarRakingWithDefaultZeroPoints() {
        //When
        CarBrandModel carBrandModel =getBrandModel();
        carBrandModel.setStar(null);
        Star star = new Star(carBrandModel.getId(), 0F);
        when(carBrandModelRepository.save(carBrandModel)).thenReturn(carBrandModel);


        //When
        CarBrandModel  savedModel = carBrandModelService.save(carBrandModel);

        //Then
        assertEquals(Long.valueOf(1),savedModel.getId());
        assertEquals("Sorento",savedModel.getModel());
        assertEquals(Long.valueOf(1),savedModel.getBrand().getId());
        assertEquals("Kia",savedModel.getBrand().getBrand());
        assertEquals(Long.valueOf(1),savedModel.getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),savedModel.getCars().get(0).getPrice());
        assertEquals("WWA 123456",savedModel.getCars().get(0).getRegistration());
        assertEquals("RENT",savedModel.getCars().get(0).getVehicleStatus());
        assertEquals(Float.valueOf(0),savedModel.getStar().getRatingAverage());
        assertEquals(Long.valueOf(1),savedModel.getStar().getModelId());
    }


    @Test
    public void shouldDelete() {
    //Given
    doNothing().when(carBrandModelRepository).deleteById(anyLong());

    //When
    carBrandModelService.delete(1L);

    //Then
    verify(carBrandModelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllModelByBrand_Id() {
        //Given
        CarBrandModel carBrandModel =getBrandModel();
        CarBrandModel carBrandModel2 =getBrandModel();
        carBrandModel2.setModel("Sportage");
        carBrandModel2.setId(2L);
        List<CarBrandModel> modelList = new ArrayList<>();
        modelList.add(carBrandModel);
        modelList.add(carBrandModel2);

        when(carBrandModelRepository.findCarBrandModelByBrand_Id(carBrandModel.getBrand().getId())).thenReturn(modelList);

        //When
        List<CarBrandModel>  foundedList = carBrandModelService.getAllModelByBrand_Id(carBrandModel.getBrand().getId());

        //Then
        assertEquals(2,foundedList.size());
        assertEquals(Long.valueOf(1),foundedList.get(0).getId());
        assertEquals("Sorento",foundedList.get(0).getModel());
        assertEquals(Long.valueOf(1),foundedList.get(0).getBrand().getId());
        assertEquals("Kia",foundedList.get(0).getBrand().getBrand());
        assertEquals(Long.valueOf(1),foundedList.get(0).getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),foundedList.get(0).getCars().get(0).getPrice());
        assertEquals("WWA 123456",foundedList.get(0).getCars().get(0).getRegistration());
        assertEquals("RENT",foundedList.get(0).getCars().get(0).getVehicleStatus());
        assertEquals(Long.valueOf(2),foundedList.get(1).getId());
        assertEquals("Sportage",foundedList.get(1).getModel());
        assertEquals(Long.valueOf(1),foundedList.get(1).getBrand().getId());
        assertEquals("Kia",foundedList.get(1).getBrand().getBrand());
        assertEquals(Long.valueOf(1),foundedList.get(1).getCars().get(0).getId());
        assertEquals(BigDecimal.valueOf(250),foundedList.get(1).getCars().get(0).getPrice());
        assertEquals("WWA 123456",foundedList.get(1).getCars().get(0).getRegistration());
        assertEquals("RENT",foundedList.get(1).getCars().get(0).getVehicleStatus());

    }
}