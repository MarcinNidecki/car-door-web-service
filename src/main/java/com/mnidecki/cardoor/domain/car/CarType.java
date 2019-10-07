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
@Entity(name = "car_types")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;


    @OneToMany(targetEntity = CarParameters.class,
            mappedBy = "type",
            fetch = FetchType.EAGER)
    private List<CarParameters> cars = new ArrayList<>();

    public CarType(String type) {
        this.type = type;
    }

    public CarType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarType carType = (CarType) o;

        if (id != null ? !id.equals(carType.id) : carType.id != null) return false;
        if (type != null ? !type.equals(carType.type) : carType.type != null) return false;
        return cars != null ? cars.equals(carType.cars) : carType.cars == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cars != null ? cars.hashCode() : 0);
        return result;
    }
}


