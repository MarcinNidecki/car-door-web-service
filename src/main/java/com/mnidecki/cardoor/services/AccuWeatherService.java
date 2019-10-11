package com.mnidecki.cardoor.services;

import com.mnidecki.cardoor.client.AccuWeatherClient;
import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.Weather;
import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import com.mnidecki.cardoor.mapper.WeatherMapper;
import com.mnidecki.cardoor.repository.WeatherRepository;
import com.mnidecki.cardoor.services.DBService.DailyForecastService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccuWeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private DateTimeService dateService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AccuWeatherClient accuWeatherClient;
    @Autowired
    private WeatherMapper weatherMapper;
    @Autowired
    private DailyForecastService dailyForecastService;





    public List<ForecastResponseDto> get5DayForecastsForAllLocation () {
        List<Long> idList = locationService.findAll().stream().map(Locationn::getId).collect(Collectors.toList());
        List<ForecastResponseDto> weather = new ArrayList<>();

        for(Long id: idList) {
            ForecastResponseDto forecast = getLocation5DayForecasts(id);
            if(forecast.getDailyForecastDtoList() !=null && forecast.getDailyForecastDtoList().size()>=5) {
                weather.add(getLocation5DayForecasts(id));
            }

        }
        return weather;
    }


    public ForecastResponseDto getLocation5DayForecasts(Long cityId) {

        ForecastResponseDto forecast = accuWeatherClient.get5DayForecasts(locationService.findById(cityId));
        if(forecast.getDailyForecastDtoList() !=null && forecast.getDailyForecastDtoList().size()>=5) {
            forecast = setDisplayNameOfDay(forecast);
            forecast.setLocationId(cityId);
        }
        return forecast;
    }

    public ForecastResponseDto setDisplayNameOfDay(ForecastResponseDto forecast) {

        forecast.setNameOfFirstDay(dateService.getLongDisplayNameOfDay(forecast.getDailyForecastDtoList().get(0).getDate()));
        forecast.setDateOfFirstDay(dateService.getDateFromString(forecast.getDailyForecastDtoList().get(0).getDate()));
        forecast.getDailyForecastDtoList()
                .forEach(dailyForecast -> dailyForecast.getDayDto().setNameOfDay(dateService.getShortDisplayNameOfDay(dailyForecast.getDate())));

        return forecast;
    }

    public Iterable<Weather> saveAll (List<ForecastResponseDto> weather) {
        List<Weather> weatherList = weatherMapper.mapToWeatherLIst(weather);
        weatherList.forEach( location -> dailyForecastService.deleteAllByWeather_Id(location.getId()));
        return weatherRepository.saveAll(weatherList);
    }

    public Weather  save (ForecastResponseDto forecast) {
        Weather weather = weatherMapper.mapToWeather(forecast);
        System.out.println(weather);
        return weatherRepository.save(weather);
    }

    public Weather findById(Long id) {
        return weatherRepository.findById(id).orElse(new Weather());
    }
}
