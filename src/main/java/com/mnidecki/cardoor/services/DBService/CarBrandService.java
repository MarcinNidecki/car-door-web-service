package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarBrandService {

    @Autowired
    private CarBrandRepository carBrandRepository;

    public List<CarBrand> findAll() {
        return carBrandRepository.findAll();
    }

    public Optional<CarBrand> findByID(final Long id) {
        return carBrandRepository.findById(id);
    }

    public CarBrand save(final CarBrand carBrand) {
        return carBrandRepository.save(carBrand);
    }

    public void deleteById(final Long id) {
        carBrandRepository.deleteById(id);
    }

}
