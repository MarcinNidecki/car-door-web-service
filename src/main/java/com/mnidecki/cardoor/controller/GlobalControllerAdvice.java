package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.domain.dto.LocationnDto;
import com.mnidecki.cardoor.mapper.CommentMapper;
import com.mnidecki.cardoor.mapper.LocationMapper;
import com.mnidecki.cardoor.services.DBService.BookingService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.CommentService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CarService carService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationMapper locationMapper;

    @ModelAttribute("lastComments")
    public List<CommentDto> getLast4Comments() {
        return commentMapper.mapToCommentDtoList(commentService.findTop4ByCreationDate()); }

    @ModelAttribute("happyClients")
    public int getNumbersOfHappyClients() {
        return bookingService.countHappyClients();
    }

    @ModelAttribute("numberOfCarsInStock")
    public int countAllCarsInStock() {
        return carService.findAll().size();
    }

    @ModelAttribute("numberOfLocation")
    public int countAllLocation() {
        return locationService.findAll().size();
    }

    @ModelAttribute("allCity")
    public List<LocationnDto> allCity() {
        return locationMapper.mapToLocationDtoList(locationService.findAll());
    }



}
