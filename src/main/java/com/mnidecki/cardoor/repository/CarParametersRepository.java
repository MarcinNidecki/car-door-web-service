package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.CarParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarParametersRepository extends CrudRepository<CarParameters, Integer> {

    @Override
    List<CarParameters> findAll();

    Optional<CarParameters> findById(Long id);

    int countCarParametersByCarPicture_id(Long id);

    int countCarParametersByType_Id(Long id);

    List<CarParameters> findCarParametersByType_Id(Long id);

    @Override
    CarParameters save(CarParameters carParameters);

    void deleteById(Long id);

    void deleteAll();

}
