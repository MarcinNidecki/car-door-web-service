package com.mnidecki.cardoor.domain.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {

    @JsonProperty("LocalizedName")
    private String localizedName;
    @JsonProperty("Key")
    private String cityKey;
    @JsonProperty("Country")
    private CountryDto country;

    @Override
    public String toString() {
        return "LocationDto{" +
                "localizedName='" + localizedName + '\'' +
                ", cityKey='" + cityKey + '\'' +
                ", country=" + country +
                '}';
    }
}
