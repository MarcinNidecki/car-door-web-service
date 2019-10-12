package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.car.Comment;
import com.mnidecki.cardoor.domain.dto.CarDto;
import com.mnidecki.cardoor.domain.dto.CommentDto;
import com.mnidecki.cardoor.mapper.CarMapper;
import com.mnidecki.cardoor.mapper.CommentMapper;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController("UserCarController")
@RequestMapping("/car")
public class UserCarController {

    @Autowired
    private CarService carService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;


    @PostMapping(value = "/brand/{brandId}/model/{modelId}/comment")
    public ModelAndView saveComment(@PathVariable Long brandId, @PathVariable Long modelId, @Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult, Long carId) {
        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            Comment comment = commentService.save(commentMapper.mapToComment(commentDto));
            if (comment != null) {
                modelAndView.addObject("msg", "Your comment has been successfully submitted.");
            } else {
                modelAndView.addObject("msg", "Sorry something went wrong try again!");
                modelAndView.addObject("commentDto", commentDto);
            }
        }
        CarDto carDto = carMapper.mapToCarDto(carService.findById(carId));
        List<CommentDto> commentDtoList = commentMapper.mapToCommentDtoList(commentService.findAllByModel_Id(carDto.getModelId()));
        modelAndView.addObject("commentDtoList", commentDtoList);
        modelAndView.addObject("carDto", carDto);
        modelAndView.setViewName("car");
        return modelAndView;
    }

    @GetMapping(value = "/{carId}")
    public ModelAndView type(@PathVariable Long carId) {
        ModelAndView modelAndView = new ModelAndView();
        CarDto carDto = carMapper.mapToCarDto(carService.findById(carId));
        List<CommentDto> commentDtoList = commentMapper.mapToCommentDtoList(commentService.findAllByModel_Id(carDto.getModelId()));
        modelAndView.addObject("commentDtoList", commentDtoList);
        modelAndView.addObject("carDto", carDto);
        modelAndView.addObject("commentDto", new CommentDto());
        modelAndView.setViewName("car");
        return modelAndView;
    }
}
