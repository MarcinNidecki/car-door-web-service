package com.mnidecki.cardoor.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "car_brand_model")
public class CarBrandModel implements Serializable {

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
            fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class,
            mappedBy = "model",
            fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "carBrandModel", cascade = CascadeType.ALL)
    private Star star;

    public CarBrandModel(String model) {
        this.model = model;
    }

    public CarBrandModel(String model, CarBrand brand) {
        this.model = model;
        this.brand = brand;
    }

    public CarBrandModel(Long id, String model, CarBrand brand) {
        this.id = id;
        this.model = model;
        this.brand = brand;
    }
    public CarBrandModel(Long id, String model) {
        this.id = id;
        this.model = model;
    }

}


