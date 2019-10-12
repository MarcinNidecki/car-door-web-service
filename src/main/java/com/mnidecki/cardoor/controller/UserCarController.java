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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.SUCCESSMESSAGE;

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

    @PostMapping(value = "/brand/{brandId}/model/{model}/comment")
    public ModelAndView saveComment(@PathVariable Long brandId, @PathVariable Long model, @Valid @ModelAttribute CommentDto commentDto,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes, Long carId) {
        ModelAndView modelAndView = new ModelAndView();
        CarDto carDto = carMapper.mapToCarDto(carService.findById(carId));
        List<CommentDto> commentDtoList = commentMapper.mapToCommentDtoList(commentService.findAllByModel_Id(carDto.getModelId()));
        modelAndView.addObject("commentDtoList", commentDtoList);
        modelAndView.addObject("carDto", carDto);
        commentDto.setModelId(model);
        if (!bindingResult.hasErrors()) {
            Comment comment = commentService.save(commentMapper.mapToComment(commentDto));
            if (comment != null) {
                redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Your comment has been successfully submitted.");
                modelAndView.setViewName("redirect:/car/"+carId.toString());
                return modelAndView;
            }
        } else {
            modelAndView.addObject("commentDto", commentDto);

            modelAndView.setViewName("car");
        }
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
