package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.car.Star;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StarRepository extends CrudRepository <Star,Integer> {

    @Override
    List<Star> findAll();

    Optional<Star> findByModelId(Long id);

    @Override
    Star save(Star star);

    void deleteByModelId(Long id);

}
