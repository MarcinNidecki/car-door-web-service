package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarPictureMapper {

    public CarPicture mapToCarPicture(CarPictureDto carPictureDto) {
        CarPicture carPicture = new CarPicture();
        if(carPictureDto.getId()>0) carPicture.setId(carPictureDto.getId());
        if(carPictureDto.getDescriptions()!=null) carPicture.setDescriptions(carPictureDto.getDescriptions());
        if(carPictureDto.getFileName()!=null) carPicture.setFileName(carPictureDto.getFileName());
        if(carPictureDto.getThumbnails()!=null) carPicture.setThumbnails(carPictureDto.getThumbnails());
        if(carPictureDto.getThumbnailsPath()!=null) carPicture.setThumbnailsPath(carPictureDto.getThumbnailsPath());
        if(carPictureDto.getFileNamePath()!=null) carPicture.setFileNamePath(carPictureDto.getFileNamePath());
        if(carPictureDto.getFileExtension()!=null) carPicture.setFileExtension(carPictureDto.getFileExtension());
        if(carPictureDto.getCreatedDate()!=null) carPicture.setCreatedDate(carPictureDto.getCreatedDate());
        if(carPictureDto.getFile()!=null) carPicture.setFile(carPictureDto.getFile());
        return carPicture;
    }

    public CarPictureDto mapToCarPictureDto(CarPicture carPicture) {
        return new CarPictureDto(
                carPicture.getId(),
                carPicture.getDescriptions(),
                carPicture.getFileName(),
                carPicture.getThumbnails(),
                carPicture.getFileNamePath(),
                carPicture.getThumbnailsPath(),
                carPicture.getFileExtension(),
                carPicture.getCreatedDate(),
                null);

    }

    public List<CarPictureDto> mapToCarPictureDtoList(final List<CarPicture> carPictureList) {

        return carPictureList.stream()
                .map(carPicture -> new CarPictureDto(
                        carPicture.getId(),
                        carPicture.getDescriptions(),
                        carPicture.getFileName(),
                        carPicture.getThumbnails(),
                        carPicture.getFileNamePath(),
                        carPicture.getThumbnailsPath(),
                        carPicture.getFileExtension(),
                        carPicture.getCreatedDate()))
                .collect(Collectors.toList());
    }
}

