package com.mnidecki.cardoor.repository;


import com.mnidecki.cardoor.domain.car.CarPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarPictureRepository extends CrudRepository<CarPicture, Integer> {

    @Override
    List<CarPicture> findAll();

    @Override
    CarPicture save(CarPicture carPicture);

    Optional<CarPicture> findById(Long id);

    void deleteById(Long id);

    CarPicture findCarPictureByDescriptions(String descriptions);
}
