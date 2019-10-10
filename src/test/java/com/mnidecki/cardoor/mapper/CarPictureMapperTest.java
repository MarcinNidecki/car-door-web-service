package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CarPictureMapperTest {

    @InjectMocks
    private CarPictureMapper pictureMapper;


    @Test
    public void ShouldMapToCarPicture() {
        //Given
        CarPictureDto carPictureDto = new CarPictureDto(1L, "description", "filename", "thumbnails", "fileNamePath",
                "thumbNailsPath","jpg", LocalDate.now());

        //When
        CarPicture carPicture = pictureMapper.mapToCarPicture(carPictureDto);

        //Then
        assertEquals(Long.valueOf(1),carPicture.getId());
        assertEquals("description",carPicture.getDescriptions());
        assertEquals("filename",carPicture.getFileName());
        assertEquals("thumbnails",carPicture.getThumbnails());
        assertEquals("fileNamePath",carPicture.getFileNamePath());
        assertEquals("thumbNailsPath",carPicture.getThumbnailsPath());
        assertEquals("jpg",carPicture.getFileExtension());
        assertEquals(LocalDate.now(),carPicture.getCreatedDate());
    }

    @Test
    public void ShouldMapToCarPictureDto() {
        //Given
        CarPicture carPicture = new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath",
                "thumbNailsPath","jpg", LocalDate.now());

        //When
        CarPictureDto carPictureDto = pictureMapper.mapToCarPictureDto(carPicture);

        //Then
        assertEquals(Long.valueOf(1),carPictureDto.getId());
        assertEquals("description",carPictureDto.getDescriptions());
        assertEquals("filename",carPictureDto.getFileName());
        assertEquals("thumbnails",carPictureDto.getThumbnails());
        assertEquals("fileNamePath",carPictureDto.getFileNamePath());
        assertEquals("thumbNailsPath",carPictureDto.getThumbnailsPath());
        assertEquals("jpg",carPictureDto.getFileExtension());
        assertEquals(LocalDate.now(),carPictureDto.getCreatedDate());
    }

    @Test
    public void mapToCarPictureDtoList() {
        //Given
        CarPicture carPicture = new CarPicture(1L, "description", "filename", "thumbnails", "fileNamePath",
                "thumbNailsPath","jpg", LocalDate.now());
        List<CarPicture> carPictureList = Collections.singletonList(carPicture);

        //When
        List<CarPictureDto> carPictureDtoList = pictureMapper.mapToCarPictureDtoList(carPictureList);
        CarPictureDto carPictureDto = carPictureDtoList.get(0);
        //Then
        assertEquals(1,carPictureDtoList.size());
        assertEquals(Long.valueOf(1),carPictureDto.getId());
        assertEquals("description",carPictureDto.getDescriptions());
        assertEquals("filename",carPictureDto.getFileName());
        assertEquals("thumbnails",carPictureDto.getThumbnails());
        assertEquals("fileNamePath",carPictureDto.getFileNamePath());
        assertEquals("thumbNailsPath",carPictureDto.getThumbnailsPath());
        assertEquals("jpg",carPictureDto.getFileExtension());
        assertEquals(LocalDate.now(),carPictureDto.getCreatedDate());
    }
}