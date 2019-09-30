package com.mnidecki.cardoor.domain.dto.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto {

    @JsonProperty("Icon")
    private String icon;

    @JsonIgnoreProperties
    private String nameOfDay;

    @Override
    public String toString() {
        return "DayDto{" +
                "icon='" + icon + '\'' +
                ", nameOfDay='" + nameOfDay + '\'' +
                '}';
    }
}
