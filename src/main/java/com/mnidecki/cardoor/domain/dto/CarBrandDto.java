package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class CarBrandDto {

    private Long id;
    private String brand;
    private List<CarBrandModelDto> models = new ArrayList<>();


    public CarBrandDto(String brand) {
        this.brand = brand;
    }

    public CarBrandDto(Long id, String brand, List<CarBrandModelDto> models) {
        this.id = id;
        this.brand = brand;
        this.models = models;
    }

    @Override
    public String toString() {
        return brand;
    }
}
