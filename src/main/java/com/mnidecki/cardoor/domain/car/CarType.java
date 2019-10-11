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
@Entity(name = "car_types")
public class CarType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;


    @OneToMany(targetEntity = CarParameters.class,
            mappedBy = "type",
            fetch = FetchType.EAGER)
    private List<CarParameters> parameters = new ArrayList<>();

    public CarType(String type) {
        this.type = type;
    }

    public CarType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

}


