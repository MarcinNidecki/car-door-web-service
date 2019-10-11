package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandService {

    @Autowired
    private CarBrandRepository carBrandRepository;

    public CarBrand getDefaultCarBrand() {
        CarBrand unknown = carBrandRepository.findByBrand("Unknown")
                .orElse(new CarBrand("Unknown"));
        return save(unknown);
    }

    public List<CarBrand> findAll() {
        return carBrandRepository.findAll();
    }

    public CarBrand findByID(final Long id) {
        return carBrandRepository.findById(id).orElse(new CarBrand());
    }

    public CarBrand save(final CarBrand carBrand) {
        return carBrandRepository.save(carBrand);
    }

    public void deleteById(final Long id) {
        CarBrand carBrand = findByID(id);
        carBrand.getModels().forEach(model -> model.setBrand(getDefaultCarBrand()));
        save(carBrand);
        carBrandRepository.deleteById(id);
    }
}

