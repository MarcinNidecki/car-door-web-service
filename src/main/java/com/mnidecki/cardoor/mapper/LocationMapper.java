package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.dto.LocationnDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public Locationn mapToLocation(final LocationnDto locationDto) {
        return new Locationn(
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

    public LocationnDto mapToLocationDto(final Locationn location) {
        return new LocationnDto(
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

    public List<LocationnDto> mapToLocationDtoList(final List<Locationn> carList) {
        return carList.stream()
                .map(location -> new LocationnDto(
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
                        location.getOpeningHours()))
                .collect(Collectors.toList());

    }
}