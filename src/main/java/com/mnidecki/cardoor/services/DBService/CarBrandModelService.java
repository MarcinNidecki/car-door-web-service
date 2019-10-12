package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.repository.CarBrandModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarBrandModelService {

    @Autowired
    private CarBrandModelRepository carBrandModelRepository;
    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private StarService starService;


    public List<CarBrandModel> findAll() {
        return carBrandModelRepository.findAll();
    }

    public CarBrandModel findByID(final Long id) {
        return carBrandModelRepository.findById(id).orElse(new CarBrandModel());
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
