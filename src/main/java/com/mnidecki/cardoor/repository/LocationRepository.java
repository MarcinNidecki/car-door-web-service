package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    @Override
    List<Location> findAll();

    Optional<Location> findById(Long id);

    @Override
    Location save(Location location);

    void deleteById(Long id);

    void deleteAll();
}








