package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.repository.CarParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarParametersService {

    @Autowired
    private CarParametersRepository carParametersRepository;


    public List<CarParameters> findAll() {
        return carParametersRepository.findAll();
    }

    public Optional<CarParameters> findById(final Long id) {
        return carParametersRepository.findById(id);
    }

    public CarParameters saveCarParameters(final CarParameters car) {
        return carParametersRepository.save(car);
    }

    public void deleteById(final Long id) {
        carParametersRepository.deleteById(id);
    }

    public int countCarParametersByCarPictureId(final Long id) {
        return carParametersRepository.countCarParametersByCarPicture_id(id);

    }


}

