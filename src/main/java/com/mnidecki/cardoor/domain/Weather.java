package com.mnidecki.cardoor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "weather")
public class Weather implements Serializable {

    @Id
    private Long id;
    @Column(name = "name_of_first_day")
    private String nameOfFirstDay;
    @Column(name = "date_of_first_day")
    private String dateOfFirstDay;
    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Locationn location;

    @OneToMany(targetEntity = DailyForecast.class,
            mappedBy = "weather",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<DailyForecast> dailyForecastList;

    public void setLocation(Locationn location) {
        this.location = location;
        location.setWeather(this);

    }

}
