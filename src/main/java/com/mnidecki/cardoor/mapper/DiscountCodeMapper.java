package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.booking.DiscountCode;
import com.mnidecki.cardoor.domain.dto.DiscountCodeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountCodeMapper {

    public DiscountCode mapToDiscountCode(DiscountCodeDto discountCodeDto) {
        return new DiscountCode(
                discountCodeDto.getId(),
                discountCodeDto.getDiscountPercentage(),
                discountCodeDto.getDiscountDescription(),
                discountCodeDto.getValidFrom(),
                discountCodeDto.getValidTo());
    }

    public DiscountCodeDto mapToDiscountCodeDto(DiscountCode discountCode) {
        return new DiscountCodeDto(
                discountCode.getId(),
                discountCode.getDiscountPercentage(),
                discountCode.getDiscountDescription(),
                discountCode.getValidFrom(),
                discountCode.getValidTo());
    }

    public List<DiscountCodeDto> mapToDiscountCodeDtoList(List<DiscountCode> discountCodeList) {
        return discountCodeList.stream()
                .map(this::mapToDiscountCodeDto)
                .collect(Collectors.toList());
    }
}
