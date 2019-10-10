package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.Locationn;
import com.mnidecki.cardoor.domain.dto.LocationnDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LocationMapperTest {

    @InjectMocks
    private LocationMapper locationMapper;

    @Test
    public void shouldMapToLocation() {        //Given
        LocationnDto locationDto = new LocationnDto(1L,"Poland","Katowice","Katowice Airport","address line 1",
                "address line 2",
                "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");

        //When
        Locationn location = locationMapper.mapToLocation(locationDto);

        //Then
        assertEquals(Long.valueOf(1),location.getId());
        assertEquals("Poland",location.getCountry());
        assertEquals("Katowice",location.getCity());
        assertEquals("Katowice Airport",location.getLocationName());
        assertEquals("address line 1",location.getAddressLine());
        assertEquals("address line 2",location.getAddressLine2());
        assertEquals("Terminal 2, office next to parking",location.getPickUpInstructions());
        assertEquals("44-444",location.getPostalCode());
        assertEquals("email@email.pl",location.getEmail());
        assertEquals("555555555",location.getPhone());
        assertEquals("Mon - Fri 10-16",location.getOpeningHours());
    }

    @Test
    public void shouldMapToLocationDto() {
        //Given
        Locationn location = new Locationn(1L,"Poland","Katowice","Katowice Airport","address line 1","address line 2",
                "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");

        //When
        LocationnDto locationDto = locationMapper.mapToLocationDto(location);

        //Then
        assertEquals(Long.valueOf(1),locationDto.getId());
        assertEquals("Poland",locationDto.getCountry());
        assertEquals("Katowice",locationDto.getCity());
        assertEquals("Katowice Airport",locationDto.getLocationName());
        assertEquals("address line 1",locationDto.getAddressLine());
        assertEquals("address line 2",locationDto.getAddressLine2());
        assertEquals("Terminal 2, office next to parking",locationDto.getPickUpInstructions());
        assertEquals("44-444",locationDto.getPostalCode());
        assertEquals("email@email.pl",locationDto.getEmail());
        assertEquals("555555555",locationDto.getPhone());
        assertEquals("Mon - Fri 10-16",locationDto.getOpeningHours());
    }

    @Test
    public void shouldMapToLocationDtoList() {
        //Given
        Locationn location = new Locationn(1L,"Poland","Katowice","Katowice Airport","address line 1","address line 2",
                "Terminal 2, office next to parking","44-444","email@email.pl","555555555","Mon - Fri 10-16");
        List<Locationn> locationList = Collections.singletonList(location);

        //When
        List<LocationnDto> locationDtoList = locationMapper.mapToLocationDtoList(locationList);
        LocationnDto locationDto = locationDtoList.get(0);

        //Then
        assertEquals(1,locationDtoList.size());
        assertEquals(Long.valueOf(1),locationDto.getId());
        assertEquals("Poland",locationDto.getCountry());
        assertEquals("Katowice",locationDto.getCity());
        assertEquals("Katowice Airport",locationDto.getLocationName());
        assertEquals("address line 1",locationDto.getAddressLine());
        assertEquals("address line 2",locationDto.getAddressLine2());
        assertEquals("Terminal 2, office next to parking",locationDto.getPickUpInstructions());
        assertEquals("44-444",locationDto.getPostalCode());
        assertEquals("email@email.pl",locationDto.getEmail());
        assertEquals("555555555",locationDto.getPhone());
        assertEquals("Mon - Fri 10-16",locationDto.getOpeningHours());
    }
}