package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.repository.CarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarTypeService {

    @Autowired
    private CarTypeRepository carTypeRepository;
    @Autowired
    private CarParametersService carParametersService;

    public CarType getDefaultCarType() {
        CarType unknown = carTypeRepository.findCarTypeByType("Unknown");
        if (unknown == null) {
            unknown = new CarType("Unknown");
            save(unknown);
        }
        return unknown;
    }

    public List<CarType> findAll() {
        return carTypeRepository.findAll();
    }

    public CarType getById(final Long id) {
        return carTypeRepository.findById(id).orElseGet(this::getDefaultCarType);
    }

    public CarType save(final CarType carType) {
        return carTypeRepository.save(carType);
    }

    public void deleteById(final Long id) {
        CarType defaulfType = getDefaultCarType();
        CarType carType = getById(id);
        carType.getParameters().forEach(parameters -> parameters.setType(defaulfType));
        save(carType);
        carTypeRepository.deleteById(id);
    }
}