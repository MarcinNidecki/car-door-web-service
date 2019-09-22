package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {


    private Long id;
    private String country;
    private String city;
    private String locationName;
    private String addressLine;
    private String addressLine2;
    private String pickUpInstructions;
    private String postalCode;
    private String email;
    private String phone;
    private String openingHours;

}
