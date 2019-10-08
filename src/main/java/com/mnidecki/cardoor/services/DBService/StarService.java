package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarService {

    @Autowired
    private StarRepository starRepository;

    public List<Star> findAll() {
        return starRepository.findAll();
    }

    public Star findById(final Long id) {
        return starRepository.findByModelId(id).orElse(new Star());
    }

    public Star save(final Star star) {
        return starRepository.save(star);
    }

    public void deleteById(final Long id) {
        starRepository.deleteByModelId(id);
    }


}
