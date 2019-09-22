package com.mnidecki.cardoor.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "car_brand_model")
public class CarBrandModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "model_name")
    private String model;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brandId")
    private CarBrand brand;

    @OneToMany(targetEntity = Car.class,
            mappedBy = "model",
            fetch = FetchType.EAGER)
    private List<Car> cars = new ArrayList<>();

    public CarBrandModel(String model) {
        this.model = model;
    }

    public CarBrandModel(Long id, String model, CarBrand brand) {
        this.id = id;
        this.model = model;
        this.brand = brand;
    }

}


