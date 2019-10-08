package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccuWeatherService {
    @Autowired
    private DateTimeService dateTimeService;

    public ForecastResponseDto setDisplayNameOfDay(ForecastResponseDto forecastResponseDto) {

        forecastResponseDto.setNameOfFirstDay(dateTimeService.getLongDisplayNameOfDay(forecastResponseDto.getDailyForecastDtoList().get(0).getDate()));
        forecastResponseDto.setDateOfFirstDay(dateTimeService.getDateFromString(forecastResponseDto.getDailyForecastDtoList().get(0).getDate()));

        forecastResponseDto.getDailyForecastDtoList()
                .forEach(dailyForecast -> dailyForecast.getDayDto().setNameOfDay(dateTimeService.getShortDisplayNameOfDay(dailyForecast.getDate())));
        return forecastResponseDto;
    }
}
