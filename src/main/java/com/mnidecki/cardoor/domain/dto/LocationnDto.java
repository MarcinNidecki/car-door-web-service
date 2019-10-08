package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationnDto that = (LocationnDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (locationName != null ? !locationName.equals(that.locationName) : that.locationName != null) return false;
        if (addressLine != null ? !addressLine.equals(that.addressLine) : that.addressLine != null) return false;
        if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null) return false;
        if (pickUpInstructions != null ? !pickUpInstructions.equals(that.pickUpInstructions) : that.pickUpInstructions != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return openingHours != null ? openingHours.equals(that.openingHours) : that.openingHours == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (locationName != null ? locationName.hashCode() : 0);
        result = 31 * result + (addressLine != null ? addressLine.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (pickUpInstructions != null ? pickUpInstructions.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (openingHours != null ? openingHours.hashCode() : 0);
        return result;
    }
}
