package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.Location;
import com.mnidecki.cardoor.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBLocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findById(final Long id) {
        return locationRepository.findById(id).orElseGet(null);
    }

    public Location save(final Location location) {
        return locationRepository.save(location);
    }

    public void deleteById() {
        locationRepository.deleteAll();
    }

}
