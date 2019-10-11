package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarBrand;
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
    private CarBrandService carBrandService;

    @Autowired
    private StarService starService;

    public CarBrandModel getDefaultCarBrandModel() {
        CarBrand unknown = carBrandService.findByBrandName("Unknown");
        CarBrandModel unknownModel = carBrandModelRepository.findCarBrandModelByModel("Unknown")
                .orElse(new CarBrandModel("Unknown",unknown));
        unknown.getModels().add(unknownModel);
        if (unknownModel.getId()!=null) {
            unknownModel = carBrandModelRepository.save(unknownModel);
        }

        return save(unknownModel);
    }


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
        CarBrandModel carModel = findByID(id);
        if(carModel.getCars()!=null) {
            carModel.getCars().forEach(model -> model.setModel(getDefaultCarBrandModel()));
            save(carModel);
        }
        carBrandModelRepository.deleteById(id);
    }

    public List<CarBrandModel> getAllModelByBrand_Id(Long id) {
        return carBrandModelRepository.findCarBrandModelByBrand_Id(id);
    }
}
