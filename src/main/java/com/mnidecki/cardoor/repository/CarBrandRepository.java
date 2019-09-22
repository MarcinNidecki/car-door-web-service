package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.CarBrand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandRepository extends CrudRepository<CarBrand, Integer> {

    @Override
    List<CarBrand> findAll();

    Optional<CarBrand> findById(Long id);

    @Override
    CarBrand save(CarBrand carBrand);

    void deleteById(Long id);


}
