package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.DailyForecast;
import com.mnidecki.cardoor.domain.Weather;
import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import com.mnidecki.cardoor.services.AccuWeatherService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMapper {

    @Autowired
    private LocationService locationService;
    @Autowired
    private AccuWeatherService weatherService;

    public Weather mapToWeather(ForecastResponseDto forecast) {

        Weather weather = weatherService.findById(forecast.getLocationId());
        if (weather.getId() == null) weather.setId(forecast.getLocationId());
        weather.setDateOfFirstDay(forecast.getDateOfFirstDay());
        weather.setNameOfFirstDay(forecast.getNameOfFirstDay());
        weather.setLocation(locationService.findById(forecast.getLocationId()));
        weather.setDailyForecastList(mapToDailyForecast(forecast));
        weather.getDailyForecastList().forEach(dailyForecast -> dailyForecast.setWeather(weather));
        return weather;
    }

    public List<Weather> mapToWeatherLIst(List<ForecastResponseDto> forecasts) {
        return forecasts.stream()
                .map(this::mapToWeather)
                .collect(Collectors.toList());
    }

    public List<DailyForecast> mapToDailyForecast(ForecastResponseDto forecast) {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        forecast.getDailyForecastDtoList().forEach(daily -> {

            DailyForecast dailyForecast = new DailyForecast();
            dailyForecast.setDayName(daily.getDayDto().getNameOfDay());
            dailyForecast.setIcon(daily.getDayDto().getIcon());
            float maxTemp = ((((Float.valueOf((daily.getTemperatureDto().getMaximumDto().getTemp()))) - 32) * 5) / 9);
            float minTemp = ((((Float.valueOf((daily.getTemperatureDto().getMinimumDto().getTemp()))) - 32) * 5) / 9);
            int temp = (int) ((maxTemp + minTemp) / 2);
            dailyForecast.setTemp(temp);
            dailyForecasts.add(dailyForecast);
        });
        return dailyForecasts;
    }
}
