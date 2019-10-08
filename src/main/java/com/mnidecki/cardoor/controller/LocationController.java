package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.dto.LocationnDto;
import com.mnidecki.cardoor.mapper.LocationMapper;
import com.mnidecki.cardoor.services.DBService.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/city")

public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @GetMapping(value = "getCities")
    public List<LocationnDto> getLocations() {
        return locationMapper.mapToLocationDtoList(locationService.findAll());
    }

    @PostMapping(value = "createCities", consumes = APPLICATION_JSON_VALUE)
    public void createCar(@RequestBody LocationnDto locationDto) {
        locationService.save(locationMapper.mapToLocation(locationDto));
    }

    @DeleteMapping(value = "deleteAllCities")
    public void deleteCity() {
        locationService.deleteById();
    }

}
