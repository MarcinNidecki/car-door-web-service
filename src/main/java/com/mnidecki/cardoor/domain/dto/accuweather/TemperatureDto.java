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
public class TemperatureDto {

    @JsonProperty("Maximum")
    private MaximumDto maximumDto;

    @Override
    public String toString() {
        return "TemperatureDto{" +
                "maximumDto=" + maximumDto +
                '}';
    }
}
