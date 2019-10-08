package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.Locationn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Locationn, Integer> {

    @Override
    List<Locationn> findAll();

    Optional<Locationn> findById(Long id);

    @Override
    Locationn save(Locationn location);

    void deleteById(Long id);

    void deleteAll();
}








