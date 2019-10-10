package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer> {

    @Override
    List<Weather> findAll();

    Optional<Weather> findById(Long id);

    @Override
    Weather save(Weather location);

    void deleteById(Long id);

    void deleteAll();
}








