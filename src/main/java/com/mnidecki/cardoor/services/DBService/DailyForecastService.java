package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.repository.DailyForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyForecastService {

    @Autowired
    private DailyForecastRepository repository;


    public void deleteAllByWeather_Id(Long id) {
        repository.deleteAllWhereWeatherId(id);
    }
}
