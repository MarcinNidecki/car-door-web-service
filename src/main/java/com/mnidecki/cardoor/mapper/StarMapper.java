package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.domain.dto.StarDto;
import org.springframework.stereotype.Component;

@Component
public class StarMapper {

    public StarDto mapToStarDto(Star star) {
        return new StarDto(
                star.getModelId(),
                star.getRatingAverage());
    }
}
