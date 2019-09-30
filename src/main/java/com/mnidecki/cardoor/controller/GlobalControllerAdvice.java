package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.mapper.CommentMapper;
import com.mnidecki.cardoor.services.DBService.DBBookingService;
import com.mnidecki.cardoor.services.DBService.DBCarService;
import com.mnidecki.cardoor.services.DBService.DBCommentService;
import com.mnidecki.cardoor.services.DBService.DBLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private DBCommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private DBCarService carService;
    @Autowired
    private DBBookingService bookingService;
    @Autowired
    private DBLocationService locationService;

    @ModelAttribute("lastComments")
    public List<CommentDto> getLast4Comments() {
        return commentMapper.mapToCommentDtoList(commentService.findTop4ByCreationDate());
    }

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

    
}
