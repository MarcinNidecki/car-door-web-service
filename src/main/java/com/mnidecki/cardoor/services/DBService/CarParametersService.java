package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.repository.CarParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  CarParametersService {

    @Autowired
    private CarParametersRepository carParametersRepository;


    public List<CarParameters> findAll() {
        return carParametersRepository.findAll();
    }

    public CarParameters findById(final Long id) {
        return carParametersRepository.findById(id).orElse(new CarParameters());
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

    public int countCarParametersByCarTypeId(final Long id) {
        return carParametersRepository.countCarParametersByType_Id(id);
    }


    public List<CarParameters> findCarParametersByType_Id(final Long id) {
        return carParametersRepository.findCarParametersByType_Id(id);
    }

    public Iterable saveAll(List<CarParameters> parametersList) {
        return carParametersRepository.saveAll(parametersList);
    }


}

