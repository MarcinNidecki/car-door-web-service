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
public class MinimumDto {

    @JsonProperty("Value")
    private String temp;

    @Override
    public String toString() {
        return "MaximumDto{" +
                "temp='" + temp + '\'' +
                '}';
    }
}
