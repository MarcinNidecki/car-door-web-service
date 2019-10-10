package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.CarType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarTypeRepository extends CrudRepository<CarType, Integer> {

    @Override
    List<CarType> findAll();

    Optional<CarType> findById(Long id);

    @Override
    CarType save(CarType carType);

    void deleteById(Long id);

    CarType findCarTypeByType(String descriptions);


}
