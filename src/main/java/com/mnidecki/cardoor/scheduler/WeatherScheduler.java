package com.mnidecki.cardoor.scheduler;

import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import com.mnidecki.cardoor.services.AccuWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherScheduler {

    @Autowired
    private AccuWeatherService weatherService;

    @Scheduled(cron ="0 */10 * ? * *")
    public void  fetchWeather() {

        List<ForecastResponseDto> forecastResponseDtoList = weatherService.get5DayForecastsForAllLocation();
        weatherService.saveAll(forecastResponseDtoList);

    }


}
