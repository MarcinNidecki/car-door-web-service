package com.mnidecki.cardoor.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
public class CarTypeDto {

    private Long id;
    @Length(min = 3, message = "Car type name must be minimum {min} characters long")
    @NotEmpty
    private String type;

    public CarTypeDto(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
