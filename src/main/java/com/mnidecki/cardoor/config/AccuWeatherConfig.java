package com.mnidecki.cardoor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AccuWeatherConfig {

    @Value("${accu.weather.key}")
    private String accuWeatherKey;

    @Value("${accu.weather.endpoint}")
    private String accuWeatherEndpoint;
}
