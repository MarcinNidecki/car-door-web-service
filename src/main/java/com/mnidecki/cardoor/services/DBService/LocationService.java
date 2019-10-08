package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Locationn> findAll() {
        return locationRepository.findAll();
    }

    public Locationn findById(final Long id) {
        return locationRepository.findById(id).orElseGet(null);
    }

    public Locationn save(final Locationn location) {
        return locationRepository.save(location);
    }

    public void deleteById() {
        locationRepository.deleteAll();
    }

}
