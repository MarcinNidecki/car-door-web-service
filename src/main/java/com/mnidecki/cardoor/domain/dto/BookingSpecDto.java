package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingSpecDto {

    private Long cityId;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String fuel;
    private Integer minDoor;
    private Integer maxDoor;
    private Integer minSeat;
    private Integer maxSeat;
    private Integer minLargeBag;
    private Integer maxLargeBag;
    private Integer minSmallBag;
    private Integer maxSmallBag;
    private boolean airCon;
    private boolean automatic;

}
