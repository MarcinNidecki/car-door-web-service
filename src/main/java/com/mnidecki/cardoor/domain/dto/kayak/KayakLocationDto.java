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
public class KayakLocationDto {

    @JsonProperty(value = "ctid")
    private String ctid;
    @JsonProperty("displayname")
    private String cityonly;

    @Override
    public String toString() {
        return "KayakLocationDto{" +
                "ctid='" + ctid + '\'' +
                ", cityonly='" + cityonly + '\'' +
                '}';
    }
}
