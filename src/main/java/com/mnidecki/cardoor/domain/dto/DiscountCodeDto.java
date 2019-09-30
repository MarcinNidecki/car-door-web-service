package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiscountCodeDto {

    private Long id;
    private Integer discountPercentage;
    private String discountDescription;
    private Timestamp validFrom;
    private Timestamp validTo;

}
