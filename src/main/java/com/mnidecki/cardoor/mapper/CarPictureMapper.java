package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import com.mnidecki.cardoor.services.DBService.DBCarPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarPictureMapper {

    @Autowired
    private DBCarPicture carPictureService;

    public CarPicture mapToCarPicture(CarPictureDto carPictureDto) {
        return new CarPicture(
                carPictureDto.getId(),
                carPictureDto.getDescriptions(),
                carPictureDto.getFileName(),
                carPictureDto.getThumbnails(),
                carPictureDto.getFileNamePath(),
                carPictureDto.getThumbnailsPath(),
                carPictureDto.getFileExtension(),
                carPictureDto.getCreatedDate(),
                carPictureDto.getUpdatedDate());
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
                        carPicture.getCreatedDate(),
                        null))
                .collect(Collectors.toList());
    }

    public List<CarPicture> mapToCarPictureList(final List<CarPictureDto> carPictureDtoList) {
        return carPictureDtoList.stream()
                .map(carPictureDto -> new CarPicture(
                        carPictureDto.getId(),
                        carPictureDto.getDescriptions(),
                        carPictureDto.getFileName(),
                        carPictureDto.getThumbnails(),
                        carPictureDto.getFileNamePath(),
                        carPictureDto.getThumbnailsPath(),
                        carPictureDto.getFileExtension(),
                        carPictureDto.getCreatedDate(),
                        carPictureDto.getUpdatedDate()))
                .collect(Collectors.toList());
    }


}

