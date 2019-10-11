package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.repository.CarPictureRepository;
import com.mnidecki.cardoor.services.PictureUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarPictureService {

    @Autowired
    private CarPictureRepository carPictureRepository;
    @Autowired
    private PictureUploadService pictureUploadService;

    public CarPicture getDefaultCarPicture() {
        CarPicture unknown = carPictureRepository.findCarPictureByDescriptions("Unknown").orElse(new CarPicture());
        if (unknown.getId() == null) {
            unknown = save(new CarPicture("Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg", "/img" +
                    "/car/unknown-small.jpg", "jpg", LocalDate.now()), true);
        }
        return unknown;
    }

    public List<CarPicture> findAll() {
        return carPictureRepository.findAll();
    }

    public CarPicture save(CarPicture carPicture) throws IOException {
        carPicture = pictureUploadService.uploadPicture(carPicture);
        return carPictureRepository.save(carPicture);
    }

    public CarPicture save(CarPicture carPicture, boolean isDefaultPicture) {
        if (isDefaultPicture) {
            return carPictureRepository.save(carPicture);
        } else {
            return new CarPicture();
        }
    }

    public CarPicture findById(final Long id)  {
        return carPictureRepository.findById(id).orElseGet(this::getDefaultCarPicture);
    }

    public void deleteById(final Long id)  {
        Optional<CarPicture> carPicture = carPictureRepository.findById(id);
        if (carPicture.isPresent()) {

            carPicture.get().getCarList()
                    .forEach(carParameters -> carParameters.setCarPicture(getDefaultCarPicture()));
            save(carPicture.get(),true);
        }
        carPictureRepository.deleteById(id);
    }

    public boolean isFileNameTheSameLikeFileNamePath(CarPicture picture) {

        if(picture.getFileName() !=null && picture.getFileExtension()!=null) {
            return (picture.getFileName()+"." + picture.getFileExtension()).equals(picture.getFile().getOriginalFilename());
        }
        return false;
    }
}
