package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.DiscountCode;
import com.mnidecki.cardoor.domain.dto.DiscountCodeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DiscountCodeMapperTest {

    @InjectMocks
    private DiscountCodeMapper discountCodeMapper;

    @Test
    public void shouldMapToDiscountCode() {
        //Given
        DiscountCodeDto discountCodeDto = new DiscountCodeDto(1L, 20,"October sale", Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),Timestamp.valueOf("2019-10-31 00:00:00"));

        //When
        DiscountCode discountCode = discountCodeMapper.mapToDiscountCode(discountCodeDto);

        //Then
        assertEquals(Long.valueOf(1),discountCode.getId());
        assertEquals(Integer.valueOf(20),discountCode.getDiscountPercentage());
        assertEquals("October sale",discountCode.getDiscountDescription());
        assertEquals(Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),discountCode.getValidFrom());
        assertEquals(Timestamp.valueOf("2019-10-31 00:00:00"),discountCode.getValidTo());
    }

    @Test
    public void shouldMapToDiscountCodeDto() {
        //Given
        DiscountCode discountCode = new DiscountCode(1L, 20,"October sale", Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),Timestamp.valueOf("2019-10-31 00:00:00"));

        //When
        DiscountCodeDto discountCodeDto = discountCodeMapper.mapToDiscountCodeDto(discountCode);

        //Then
        assertEquals(Long.valueOf(1),discountCodeDto.getId());
        assertEquals(Integer.valueOf(20),discountCodeDto.getDiscountPercentage());
        assertEquals("October sale",discountCodeDto.getDiscountDescription());
        assertEquals(Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),discountCodeDto.getValidFrom());
        assertEquals(Timestamp.valueOf("2019-10-31 00:00:00"),discountCodeDto.getValidTo());
    }

    @Test
    public void mapToDiscountCodeDtoList() {
        //Given
        DiscountCode discountCode = new DiscountCode(1L, 20,"October sale", Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),Timestamp.valueOf("2019-10-31 00:00:00"));
        List<DiscountCode> discountCodeList = Collections.singletonList(discountCode);
        //When
        List<DiscountCodeDto> discountCodeDtoList = discountCodeMapper.mapToDiscountCodeDtoList(discountCodeList);
        DiscountCodeDto discountCodeDto = discountCodeDtoList.get(0);

        //Then
        assertEquals(1,discountCodeDtoList.size());
        assertEquals(Long.valueOf(1),discountCodeDto.getId());
        assertEquals(Integer.valueOf(20),discountCodeDto.getDiscountPercentage());
        assertEquals("October sale",discountCodeDto.getDiscountDescription());
        assertEquals(Timestamp.valueOf("2019-10-12 " +
                "00:00:00"),discountCodeDto.getValidFrom());
        assertEquals(Timestamp.valueOf("2019-10-31 00:00:00"),discountCodeDto.getValidTo());
    }
}