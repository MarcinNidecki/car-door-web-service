package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandModelRepository extends CrudRepository<CarBrandModel, Integer> {

    @Override
    List<CarBrandModel> findAll();

    List<CarBrandModel> findCarBrandModelByBrand_Id(Long id);

    Optional<CarBrandModel> findById(Long id);

    @Override
    CarBrandModel save(CarBrandModel carBrandModel);

    void deleteById(Long id);


}
