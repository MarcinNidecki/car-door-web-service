package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.repository.CarBrandModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandModelService {
    @Autowired
    private CarBrandModelRepository carBrandModelRepository;

    @Autowired
    private StarService starService;

    public List<CarBrandModel> findAll() {
        return carBrandModelRepository.findAll();
    }

    public CarBrandModel findByID(final Long id) {
        return carBrandModelRepository.findById(id).orElseGet(null);
    }

    public CarBrandModel save(final CarBrandModel carBrandModel) {
        Star star = new Star(carBrandModel.getId(),10F);
        if (carBrandModel.getId() != null && starService.findById(carBrandModel.getId()) != null) {
           star = starService.findById(carBrandModel.getId());
        }
        carBrandModel.setStar(star);
        star.setCarBrandModel(carBrandModel);
        return carBrandModelRepository.save(carBrandModel); }

    public void delete(final Long id) {
        carBrandModelRepository.deleteById(id);
    }

    public List<CarBrandModel> getAllModelByBrand_Id(Long id) {
        return carBrandModelRepository.findCarBrandModelByBrand_Id(id);
    }

}
