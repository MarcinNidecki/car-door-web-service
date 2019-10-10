package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.repository.CarPictureRepository;
import com.mnidecki.cardoor.services.PictureUploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarPictureServiceTest {

    @InjectMocks
    private CarPictureService carPictureService;
    @Mock
    private CarPictureRepository carPictureRepository;
    @Mock
    private PictureUploadService pictureUploadService;

    public CarPicture getCarPicture() {
        return new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath", "thumbNailsPath",
                "jpg", LocalDate.of(2020,12,12));
    }

    @Test
    public void getDefaultCarPictureWhenUnknownIsInDB() {
        //Given
        Optional<CarPicture> defaulfPicture =  Optional.of(new CarPicture(1L,
                "Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg", "/img/car/unknown-small.jpg", "jpg",
                LocalDate.of(2020,12,12)));

        String unknown = "Unknown";

        when(carPictureRepository.findCarPictureByDescriptions(unknown)).thenReturn(defaulfPicture);

        //When
        CarPicture foundedPicture = carPictureService.getDefaultCarPicture();

        //Then
        assertEquals(LocalDate.of(2020,12,12), foundedPicture.getCreatedDate());
        assertEquals("/img/car/unknown.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/unknown-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("unknown", foundedPicture.getFileName());
        assertEquals("unknown-small", foundedPicture.getThumbnails());
        assertEquals("Unknown", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());
    }

    @Test
    public void ShouldGetNewDefaultCarPictureWithDateCreatedNow() throws IOException {
        //Given
        Optional<CarPicture> defaulfPicture =  Optional.of(new CarPicture());
        CarPicture unknownPicture = new CarPicture("Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg",
                "/img/car/unknown-small.jpg", "jpg",
                LocalDate.now());

        String unknown = "Unknown";

        when(carPictureRepository.findCarPictureByDescriptions(unknown)).thenReturn(defaulfPicture);

        when(carPictureService.save(unknownPicture,true)).thenReturn(unknownPicture);
        when(carPictureRepository.save(unknownPicture)).thenReturn(unknownPicture);

        //When
        CarPicture foundedPicture = carPictureService.getDefaultCarPicture();

        //Then
        assertEquals(LocalDate.now(), foundedPicture.getCreatedDate());
        assertEquals("/img/car/unknown.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/unknown-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("unknown", foundedPicture.getFileName());
        assertEquals("unknown-small", foundedPicture.getThumbnails());
        assertEquals("Unknown", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());

    }
    @Test
    public void shouldFindAllPicture() {
        //Given
        List<CarPicture> carPictureList = new ArrayList<>();
        carPictureList.add(getCarPicture());

        when(carPictureRepository.findAll()).thenReturn(carPictureList);

        //When
        List<CarPicture> founded = carPictureService.findAll();
        CarPicture foundedPicture = founded.get(0);

        //Then
        assertEquals(1, founded.size());
        assertEquals(LocalDate.of(2020,12,12), foundedPicture.getCreatedDate());
        assertEquals("fileNamePath", foundedPicture.getFileNamePath());
        assertEquals("thumbNailsPath", foundedPicture.getThumbnailsPath());
        assertEquals("filename", foundedPicture.getFileName());
        assertEquals("thumbnails", foundedPicture.getThumbnails());
        assertEquals("description", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());
    }


        @Test
    public void shouldSavePicture() throws IOException {
        //Given
        MultipartFile image =  new MockMultipartFile("car-1","car-1.jpg",
                    "img",new byte[]{1,2,3,4,5,66,7,7,8,9,77,8,9, 0});
        CarPicture picture = new CarPicture("Ford Focus", image);

        CarPicture uploaded = new CarPicture("Ford Focus", "car-1", "car-1-small", "/img/car/car-1.jpg",
                "/img/car/car-1-small.jpg", "jpg", LocalDate.now(), image);

        CarPicture savedPicture = new CarPicture(1L, "Ford Focus", "car-1", "car-1-small", "/img/car/car-1.jpg",
                    "/img/car/car-1-small.jpg", "jpg", LocalDate.now(), image);

        when(pictureUploadService.uploadPicture(picture)).thenReturn(uploaded);
        when(carPictureRepository.save(uploaded)).thenReturn(savedPicture);

        //When
        CarPicture foundedPicture = carPictureService.save(picture);

        //Then
        assertEquals(LocalDate.now(), foundedPicture.getCreatedDate());
        assertEquals("/img/car/car-1.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/car-1-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("car-1", foundedPicture.getFileName());
        assertEquals("car-1-small", foundedPicture.getThumbnails());
        assertEquals("Ford Focus", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());
    }

    @Test
    public void shouldSaveDefaultPicture() {
        //Given

        CarPicture defaultPicture = new CarPicture("Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg",
                "/img/car/unknown-small.jpg", "jpg", LocalDate.now());

        CarPicture savedPicture = new CarPicture(1L, "Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg",
                "/img/car/unknown-small.jpg", "jpg", LocalDate.now());

        when(carPictureRepository.save(defaultPicture)).thenReturn(savedPicture);

        //When
        CarPicture foundedPicture = carPictureService.save(defaultPicture,true);

        //Then
        assertEquals(LocalDate.now(), foundedPicture.getCreatedDate());
        assertEquals("/img/car/unknown.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/unknown-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("unknown", foundedPicture.getFileName());
        assertEquals("unknown-small", foundedPicture.getThumbnails());
        assertEquals("Unknown", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());
    }

    @Test
    public void shouldNotSavePictureWhenIsNotDefault() {
        //Given

        CarPicture picture = new CarPicture("Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg",
                "/img/car/unknown-small.jpg", "jpg", LocalDate.now());

        //When
        CarPicture foundedPicture = carPictureService.save(picture,false);

        //Then
        assertEquals(foundedPicture, new CarPicture());

    }


    @Test
    public void findById() {
        //Given
        Optional<CarPicture> defaulfPicture =  Optional.of( new CarPicture(1L,
                "Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg", "/img/car/unknown-small.jpg",
                "jpg", LocalDate.now()));


        when(carPictureService.findById(defaulfPicture.get().getId())).thenReturn(defaulfPicture.get());

        //When
        CarPicture foundedPicture = carPictureService.findById(defaulfPicture.get().getId());

        //Then
        assertEquals(LocalDate.now(), foundedPicture.getCreatedDate());
        assertEquals("/img/car/unknown.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/unknown-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("unknown", foundedPicture.getFileName());
        assertEquals("unknown-small", foundedPicture.getThumbnails());
        assertEquals("Unknown", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());
    }

    @Test
    public void ShoulGetDefaultCarPictureByFindByUnexistingId() {
        //Given
        CarPicture defaulfPicture =   new CarPicture(1L,
                "Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg", "/img/car/unknown-small.jpg", "jpg",
                LocalDate.now());

        when(carPictureRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(carPictureService.getDefaultCarPicture()).thenReturn(defaulfPicture);

        //When
        CarPicture foundedPicture = carPictureService.findById(12313L);

        //Then
        assertEquals(LocalDate.now(), foundedPicture.getCreatedDate());
        assertEquals("/img/car/unknown.jpg", foundedPicture.getFileNamePath());
        assertEquals("/img/car/unknown-small.jpg", foundedPicture.getThumbnailsPath());
        assertEquals("unknown", foundedPicture.getFileName());
        assertEquals("unknown-small", foundedPicture.getThumbnails());
        assertEquals("Unknown", foundedPicture.getDescriptions());
        assertEquals("jpg", foundedPicture.getFileExtension());
        assertEquals(Long.valueOf(1), foundedPicture.getId());

    }

    @Test
    public void deleteById() {
        //Given
        doNothing().when(carPictureRepository).deleteById(anyLong());

        //When
        carPictureService.deleteById(1L);

        //Then
        verify(carPictureRepository, times(1)).deleteById(1L);
        //verify(carPictureService, times(1)).save(any)
    }
}