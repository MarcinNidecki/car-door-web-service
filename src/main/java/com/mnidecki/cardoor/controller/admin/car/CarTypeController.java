package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.controller.ControllerConstant;
import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.domain.dto.CarTypeDto;
import com.mnidecki.cardoor.mapper.CarTypeMapper;
import com.mnidecki.cardoor.services.DBService.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.ERRORMESSAGE;
import static com.mnidecki.cardoor.controller.ControllerConstant.SUCCESSMESSAGE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/car/")
public class CarTypeController {

    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private CarTypeMapper carTypeMapper;


    @GetMapping("/type")
    public ModelAndView cars() {
        ModelAndView modelAndView = new ModelAndView();
        List<CarTypeDto> carTypes = carTypeMapper.mapToCarTypeDtoList(carTypeService.findAll());
        modelAndView.addObject("carTypes", carTypes);
        modelAndView.addObject("carTypeDto", new CarTypeDto());
        modelAndView.addObject("title", "Cars Type");
        modelAndView.addObject("isAdd", true);
        modelAndView.setViewName("carType");
        return modelAndView;
    }

    @PostMapping(value = "/type")
    public ModelAndView save(@Valid @ModelAttribute CarTypeDto carTypeDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("carType");
            return modelAndView;
        }
        CarType carType = carTypeService.save(carTypeMapper.mapToCarType(carTypeDto));
        if (carType != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car type is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/type");
            return modelAndView;

        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car type is not save, Please try again");
            modelAndView.addObject("carTypeDto", carTypeDto);
            modelAndView.setViewName("carType");
            return modelAndView;

        }

    }


    @GetMapping(value = "/type/{id}")
    public ModelAndView type(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        CarTypeDto carTypeDto = carTypeMapper.mapToCarTypeDto(carTypeService.getById(id));
        List<CarTypeDto> carTypes = carTypeMapper.mapToCarTypeDtoList(carTypeService.findAll());
        modelAndView.addObject("carTypes", carTypes);
        modelAndView.addObject("carTypeDto", carTypeDto);
        modelAndView.addObject("title", "Cars Type");
        modelAndView.addObject("isAdd", true);
        modelAndView.setViewName("carType");
        return modelAndView;
    }

    @PutMapping(value = "/type")
    public ModelAndView update(@Valid @ModelAttribute CarTypeDto carTypeDto,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("carType");
            return modelAndView;
        }
        CarType carType = carTypeService.save(carTypeMapper.mapToCarType(carTypeDto));
        if (carType != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car type is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/type");
        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car type is not update, Please try again");
            modelAndView.addObject("carTypeDto", carTypeDto);
            modelAndView.setViewName("carType");
        }
        return modelAndView;
    }

    @Transactional
    @DeleteMapping(value = "/type/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        carTypeService.deleteById(id);
        modelAndView.addObject("carTypeDto", new CarTypeDto());
        redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car type is deleted successfully");
        modelAndView.setViewName("redirect:/admin/car/type");
        return modelAndView;
    }
}
