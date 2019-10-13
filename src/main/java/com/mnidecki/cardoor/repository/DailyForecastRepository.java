package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.DailyForecast;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DailyForecastRepository extends CrudRepository<DailyForecast, Integer> {

    @Override
    List<DailyForecast> findAll();

    Optional<DailyForecast> findById(Long id);

    @Override
    DailyForecast save(DailyForecast location);

    void deleteById(Long id);

    void deleteAll();

    @Modifying
    @Query
    void deleteAllWhereWeatherId(@Param("WEATHERID") Long id);
}
