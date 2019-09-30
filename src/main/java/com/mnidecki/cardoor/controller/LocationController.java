package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.dto.LocationDto;
import com.mnidecki.cardoor.mapper.LocationMapper;
import com.mnidecki.cardoor.services.DBService.DBLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/city")

public class LocationController {
    @Autowired
    private DBLocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getCities")
    public List<LocationDto> getLocations() {
        return locationMapper.mapToLocationDtoList(locationService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCities", consumes = APPLICATION_JSON_VALUE)
    public void createCar(@RequestBody LocationDto locationDto) {
        locationService.save(locationMapper.mapToLocation(locationDto));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAllCities")
    public void deleteCity() {
        locationService.deleteById();
    }

}
