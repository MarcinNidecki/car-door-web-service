package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookingItemCreationDto implements Serializable {

    private List<BookingExtrasItemDto> items;

}
