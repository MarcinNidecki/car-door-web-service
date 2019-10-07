package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.repository.CarPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarPictureService {

    @Autowired
    private CarPictureRepository carPictureRepository;

    public CarPicture getDefaultCarPicture() {
        CarPicture unknown = carPictureRepository.findCarPictureByDescriptions("Unknown");
        if (unknown == null) {
            unknown = new CarPicture(
                    "Unknown", "unknown", "unknown-small", "/img/car/unknown.jpg", "/img/car/unknown-small.jpg", "jpg", LocalDate.now());
            save(unknown);
        }
        return unknown;
    }

    public List<CarPicture> findAll() {
        return carPictureRepository.findAll();
    }

    public CarPicture save(final CarPicture carPicture) {
        return carPictureRepository.save(carPicture);
    }

    public CarPicture findById(final Long id) {

        return carPictureRepository.findById(id).orElseGet(this::getDefaultCarPicture);
    }

    public void deleteById(final Long id) {
        Optional<CarPicture> carPicture = carPictureRepository.findById(id);
        if (carPicture.isPresent()) {
            carPicture.get().getCarList()
                    .forEach(carParameters -> carParameters.setCarPicture(getDefaultCarPicture()));
            save(carPicture.get());
        }

        carPictureRepository.deleteById(id);

    }


}
