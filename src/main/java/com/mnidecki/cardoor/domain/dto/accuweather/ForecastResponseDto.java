package com.mnidecki.cardoor.domain.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponseDto {

    @JsonProperty("DailyForecasts")
    List<DailyForecastDto> dailyForecastDtoList;

    @JsonIgnoreProperties
    private Long locationId;
    @JsonIgnoreProperties
    private String nameOfFirstDay;
    @JsonIgnoreProperties
    private String dateOfFirstDay;
    @Override
    public String toString() {
        return "ForecastResponeDto{" +
                "dailyForecastDtoList=" + dailyForecastDtoList +
                '}';
    }
}
