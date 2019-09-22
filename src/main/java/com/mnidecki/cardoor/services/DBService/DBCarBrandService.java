package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBCarBrandService {
    @Autowired
    private CarBrandRepository carBrandRepository;

    public List<CarBrand> getAllBrand() {
        return carBrandRepository.findAll();
    }

    public CarBrand getBrand(final Long id) {
        return carBrandRepository.findById(id).orElseGet(null);
    }

    public CarBrand saveBrand(final CarBrand carBrand) {
        return carBrandRepository.save(carBrand);
    }

    public void deleteBrand(final Long id) {
        carBrandRepository.deleteById(id);
    }

}
