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

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocation(final Long id) {
        return locationRepository.findById(id).orElseGet(null);
    }

    public Location saveLocation(final Location location) {
        return locationRepository.save(location);
    }

    public void deleteAllLocations() {
        locationRepository.deleteAll();
    }

}
