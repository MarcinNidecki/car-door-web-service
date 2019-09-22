package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandModelDto {

    private Long id;
    private String model;
    private Long brandId;


    public CarBrandModelDto(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return model;
    }
}
