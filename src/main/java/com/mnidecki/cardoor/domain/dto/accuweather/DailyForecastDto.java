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
public class DailyForecastDto {

    @JsonProperty("Date")
    private String date;
    @JsonProperty("Temperature")
    private TemperatureDto temperatureDto;
    @JsonProperty("Day")
    private DayDto dayDto;

    @Override
    public String toString() {
        return "DailyForecastDto{" +
                "date='" + date + '\'' +
                ", temperatureDto=" + temperatureDto +
                ", dayDto=" + dayDto +
                '}';
    }
}
