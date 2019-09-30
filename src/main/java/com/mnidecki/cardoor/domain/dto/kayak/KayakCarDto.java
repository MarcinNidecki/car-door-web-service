package com.mnidecki.cardoor.domain.dto.kayak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KayakCarDto {

    @JsonProperty("totalPrice")
    private String totalPrice;

    @Override
    public String toString() {
        return "KayakCarDto{" +
                "totalPrice=" + totalPrice +
                '}';
    }
}
