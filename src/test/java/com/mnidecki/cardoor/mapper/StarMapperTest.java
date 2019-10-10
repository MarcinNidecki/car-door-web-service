package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.car.Star;
import com.mnidecki.cardoor.domain.dto.StarDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StarMapperTest {

    @InjectMocks
    private StarMapper starMapper;

    @Test
    public void mapToStarDto() {
        //Given
        Star star = new Star(1L, 6.50F);

        //When
        StarDto starDto = starMapper.mapToStarDto(star);

        //Then
        assertEquals(Long.valueOf(1),starDto.getModelId());
        assertEquals(Float.valueOf(6.50F), starDto.getRatingAverage());
    }
}