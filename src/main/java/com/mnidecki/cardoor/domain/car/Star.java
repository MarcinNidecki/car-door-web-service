package com.mnidecki.cardoor.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Star")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Star {

    @Id
    @Column(name = "id")
    private Long modelId;

    @Column(name = "rating_average")
    private Float ratingAverage;

    @OneToOne
    @MapsId
    private CarBrandModel carBrandModel;

    public Star(Long modelId, Float ratingAverage) {
        this.modelId = modelId;
        this.ratingAverage = ratingAverage;
    }
}
