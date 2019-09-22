package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.repository.CarBrandModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBCarBrandModelService {
    @Autowired
    private CarBrandModelRepository carBrandModelRepository;

    public List<CarBrandModel> getAllModel() {
        return carBrandModelRepository.findAll();
    }

    public CarBrandModel getModel(final Long id) {
        return carBrandModelRepository.findById(id).orElseGet(null);
    }

    public CarBrandModel saveModel(final CarBrandModel carBrandModel) {
        return carBrandModelRepository.save(carBrandModel);
    }

    public void deleteModel(final Long id) {
        carBrandModelRepository.deleteById(id);
    }

    public List<CarBrandModel> getAllModelByBrand_Id(Long id) {
        return carBrandModelRepository.findCarBrandModelByBrand_Id(id);
    }

}
