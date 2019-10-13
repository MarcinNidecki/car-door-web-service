package com.mnidecki.cardoor.domain.car;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "car_brand")
public class CarBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "brand_name")
    private String brand;


    @OneToMany(targetEntity = CarBrandModel.class,
            mappedBy = "brand",
            fetch = FetchType.EAGER)
    private List<CarBrandModel> models = new ArrayList<>();

    public CarBrand(Long id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    public CarBrand(String brand) {
        this.brand = brand;
    }

    public CarBrand(Long id, String brand, List<CarBrandModel> models) {
        this.id = id;
        this.brand = brand;
        this.models = models;
    }
}


