package com.mnidecki.cardoor.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class LocationnDto implements Serializable {


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

    public LocationnDto(Long id, String country, String city, String locationName, String addressLine, String addressLine2, String pickUpInstructions, String postalCode, String email, String phone, String openingHours) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.locationName = locationName;
        this.addressLine = addressLine;
        this.addressLine2 = addressLine2;
        this.pickUpInstructions = pickUpInstructions;
        this.postalCode = postalCode;
        this.email = email;
        this.phone = phone;
        this.openingHours = openingHours;
    }
}
