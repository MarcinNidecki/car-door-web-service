package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarPictureMapper {

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
                carPictureDto.getFile());

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

