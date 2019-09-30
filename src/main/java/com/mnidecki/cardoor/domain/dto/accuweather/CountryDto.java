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
public class CountryDto {

    @JsonProperty("LocalizedName")
    private String localizedName;

    @Override
    public String toString() {
        return "CountryDto{" +
                "localizedName='" + localizedName + '\'' +
                '}';
    }
}
