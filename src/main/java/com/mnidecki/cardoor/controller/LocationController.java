package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.dto.LocationnDto;
import com.mnidecki.cardoor.mapper.LocationMapper;
import com.mnidecki.cardoor.services.DBService.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
