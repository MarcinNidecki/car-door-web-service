package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.repository.CarBrandModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarBrandModelService {

    @Autowired
    private CarBrandModelRepository carBrandModelRepository;

    @Autowired
    private StarService starService;

    public List<CarBrandModel> findAll() {
        return carBrandModelRepository.findAll();
    }

    public Optional<CarBrandModel> findByID(final Long id) {
        return carBrandModelRepository.findById(id);
    }

    public CarBrandModel save(final CarBrandModel carBrandModel) {
        if (carBrandModel.getStar() == null) {
            Star star = new Star(carBrandModel.getId(), 0F);
            star.setCarBrandModel(carBrandModel);
            starService.save(star);
            carBrandModel.setStar(star);
        }
        return carBrandModelRepository.save(carBrandModel);
    }

    public void delete(final Long id) {
        carBrandModelRepository.deleteById(id);
    }

    public List<CarBrandModel> getAllModelByBrand_Id(Long id) {
        return carBrandModelRepository.findCarBrandModelByBrand_Id(id);
    }
}
