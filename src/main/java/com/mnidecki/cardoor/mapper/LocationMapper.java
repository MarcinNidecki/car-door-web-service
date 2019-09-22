package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.Location;
import com.mnidecki.cardoor.domain.dto.LocationDto;
import com.mnidecki.cardoor.services.DBService.DBLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    @Autowired
    DBLocationService cityService;

    public Location mapToLocation(final LocationDto locationDto) {
        return new Location(
                locationDto.getId(),
                locationDto.getCountry(),
                locationDto.getCity(),
                locationDto.getLocationName(),
                locationDto.getAddressLine(),
                locationDto.getAddressLine2(),
                locationDto.getPickUpInstructions(),
                locationDto.getPostalCode(),
                locationDto.getEmail(),
                locationDto.getPhone(),
                locationDto.getOpeningHours());
    }

    public LocationDto mapToLocationDto(final Location location) {
        return new LocationDto(
                location.getId(),
                location.getCountry(),
                location.getCity(),
                location.getLocationName(),
                location.getAddressLine(),
                location.getAddressLine2(),
                location.getPickUpInstructions(),
                location.getPostalCode(),
                location.getEmail(),
                location.getPhone(),
                location.getOpeningHours());
    }

    public List<LocationDto> mapToLocationDtoList(final List<Location> carList) {
        return carList.stream()
                .map(this::mapToLocationDto)
                .collect(Collectors.toList());
    }
}
