package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class CarTypeDto {

    private Long id;
    private String type;


    public CarTypeDto(String type) {
        this.type = type;
    }

    public CarTypeDto(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
