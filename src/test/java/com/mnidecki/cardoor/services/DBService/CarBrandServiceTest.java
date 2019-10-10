package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.repository.CarBrandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarBrandServiceTest {

    @InjectMocks
    private CarBrandService carBrandService;
    @Mock
    private CarBrandRepository carBrandRepository;


    private CarBrand getBrand() {
        CarBrand carBrand = new CarBrand(1L,"Kia");
        CarBrandModel sorento = new CarBrandModel(1L,"Sorento",carBrand);
        CarBrandModel sportage = new CarBrandModel(2L,"Sportage",carBrand);

        carBrand.getModels().add(sorento);
        carBrand.getModels().add(sportage);

        return carBrand;
    }

    @Test
    public void shouldFindAllCarBrands() {
        //When
        List<CarBrand> carBrands = new ArrayList<>();
        carBrands.add(getBrand());

        when(carBrandRepository.findAll()).thenReturn(carBrands);

        //When
        List<CarBrand> founded = carBrandService.findAll();
        CarBrand carBrand = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(Long.valueOf(1),carBrand.getId());
        assertEquals("Kia",carBrand.getBrand());
        assertEquals(Long.valueOf(1),carBrand.getModels().get(0).getId());
        assertEquals(Long.valueOf(2),carBrand.getModels().get(1).getId());
        assertEquals("Sorento",carBrand.getModels().get(0).getModel());
        assertEquals("Sportage",carBrand.getModels().get(1).getModel());


    }

    @Test
    public void ShouldFindBrandById() {
        //When
        Optional<CarBrand> optionalBrand = Optional.of(getBrand());

        when(carBrandRepository.findById(anyLong())).thenReturn(optionalBrand);

        //When
        CarBrand foundedBrand = carBrandService.findByID(1L);

        //Then
        assertEquals(Long.valueOf(1),foundedBrand.getId());
        assertEquals("Kia",foundedBrand.getBrand());
        assertEquals(Long.valueOf(1),foundedBrand.getModels().get(0).getId());
        assertEquals(Long.valueOf(2),foundedBrand.getModels().get(1).getId());
        assertEquals("Sorento",foundedBrand.getModels().get(0).getModel());
        assertEquals("Sportage",foundedBrand.getModels().get(1).getModel());
    }

    @Test
    public void shouldFindEmptyOptionalOfCarBrand() {
        //Given
        when(carBrandRepository.findById(anyLong())).thenReturn(Optional.empty());

        //When
        CarBrand foundedBrand = carBrandService.findByID(1L);

        //Then
        assertEquals(foundedBrand, new CarBrand());

    }

    @Test
    public void shouldSaveCarBrand() {
        //When
        CarBrand carBrand =getBrand();

        when(carBrandRepository.save(carBrand)).thenReturn(carBrand);

        //When
        CarBrand  savedBrand = carBrandService.save(carBrand);

        //Then
        assertEquals(Long.valueOf(1),savedBrand.getId());
        assertEquals("Kia",savedBrand.getBrand());
        assertEquals(Long.valueOf(1),savedBrand.getModels().get(0).getId());
        assertEquals(Long.valueOf(2),savedBrand.getModels().get(1).getId());
        assertEquals("Sorento",savedBrand.getModels().get(0).getModel());
        assertEquals("Sportage",savedBrand.getModels().get(1).getModel());

    }

    @Test
    public void shouldDelete() {
        //Given
        doNothing().when(carBrandRepository).deleteById(anyLong());

        //When
        carBrandService.deleteById(1L);

        //Then
        verify(carBrandRepository, times(1)).deleteById(1L);
    }
}