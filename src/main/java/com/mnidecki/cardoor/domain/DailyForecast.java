package com.mnidecki.cardoor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NamedNativeQuery(
        name = "DailyForecast.deleteAllWhereWeatherId",
        query = "DELETE FROM daily_forecast WHERE weather_id = :WEATHERID"
)



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "daily_forecast")

public class DailyForecast implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "temperature")
    private int temp;
    @Column(name = "icon")
    private String icon;
    @Column(name = "dayName")
    private String dayName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weather_id")
    private Weather weather;

    @Override
    public String toString() {
        return "DailyForecast{" +
                "id=" + id +
                ", temp=" + temp +
                ", icon='" + icon + '\'' +
                ", dayName='" + dayName + '\'' +
                '}';
    }
}
