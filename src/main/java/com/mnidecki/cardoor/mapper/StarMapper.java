package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.domain.dto.StarDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class StarMapper {

    public Star mapToStar(StarDto starDto) {
        return new Star(
                starDto.getModelId(),
                starDto.getRatingAverage());
    }

    public StarDto mapToStarDto(Star star) {
        return new StarDto(
                star.getModelId(),
                star.getRatingAverage());
    }

    public List<StarDto> mapToStarDtoList (List<Star> starsList) {
        return starsList.stream()
                .map(this::mapToStarDto)
                .collect(Collectors.toList());
    }
}
