package com.mnidecki.cardoor.client;

import com.mnidecki.cardoor.config.AccuWeatherConfig;
import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import com.mnidecki.cardoor.domain.dto.accuweather.LocationDto;
import com.mnidecki.cardoor.services.AccuWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Optional.ofNullable;

@Component
public class AccuWeatherClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccuWeatherClient.class);

    @Autowired
    private AccuWeatherConfig accuWeatherConfig;
    @Autowired
    private AccuWeatherService accuWeatherService;
    @Autowired
    private RestTemplate restTemplate;


    public String getCityId(Locationn location) {
        URI uri = UriComponentsBuilder.fromHttpUrl(accuWeatherConfig.getAccuWeatherEndpoint() + "/locations/v1/cities/search")
                .queryParam("apikey", accuWeatherConfig.getAccuWeatherKey())
                .queryParam("q", location.getCity())
                .queryParam("language", "pl-pl")
                .build().encode().toUri();

        try {
            LocationDto[] boardsResponse = restTemplate.getForObject(uri, LocationDto[].class);
            if (boardsResponse != null && location.getCountry().equals(boardsResponse[0].getCountry().getLocalizedName()))
                return ofNullable(boardsResponse[0].getCityKey()).orElse("");
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public ForecastResponseDto get5DayForecasts(Locationn location) {
        String locationId = getCityId(location);
        if(locationId.equals("")) {
            return new ForecastResponseDto();
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(accuWeatherConfig.getAccuWeatherEndpoint() + "/forecasts/v1/daily/5day/" + locationId)
                .queryParam("apikey", accuWeatherConfig.getAccuWeatherKey())
                .queryParam("language", "en-us")
                .build().encode().toUri();

        try {
            ForecastResponseDto boardsResponse = restTemplate.getForObject(uri, ForecastResponseDto.class);
            System.out.println(boardsResponse.toString());
            boardsResponse = accuWeatherService.setDisplayNameOfDay(boardsResponse);
            return ofNullable(boardsResponse).orElse(new ForecastResponseDto());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ForecastResponseDto();
        }

    }
}
