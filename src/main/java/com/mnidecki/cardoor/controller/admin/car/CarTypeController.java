package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarType;
import com.mnidecki.cardoor.domain.dto.CarTypeDto;
import com.mnidecki.cardoor.mapper.CarTypeMapper;
import com.mnidecki.cardoor.services.DBService.DBCarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/car/")
public class CarTypeController {

    @Autowired
    private DBCarTypeService carTypeService;

    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired


    @ModelAttribute("allType")
    public List<CarTypeDto> allType() {
        return carTypeMapper.mapToCarTypeDtoList(this.carTypeService.getAllTypes());
    }

    @GetMapping("/type")
    public ModelAndView cars(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        List<CarTypeDto> carTypes = carTypeMapper.mapToCarTypeDtoList(carTypeService.getAllTypes());
        model.addAttribute("carTypes", carTypes);
        model.addAttribute("carTypeDto", new CarTypeDto());
        model.addAttribute("title", "Cars Type");
        model.addAttribute("isAdd", true);
        modelAndView.setViewName("carType");
        return modelAndView;
    }


    @PostMapping(value = "/type")
    public ModelAndView save(@ModelAttribute CarTypeDto carTypeDto, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarType carType = carTypeService.saveType(carTypeMapper.mapToCarType(carTypeDto));
        if (carType != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car type is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/type");

        } else {
            model.addAttribute("errormessage", "Car type is not save, Please try again");
            model.addAttribute("carTypeDto", carTypeDto);
            modelAndView.setViewName("carType");
        }
        return modelAndView;
    }


    @GetMapping(value = "/type/{id}")
    public ModelAndView type(@PathVariable Long id, Model model)  {
        ModelAndView modelAndView = new ModelAndView();
        CarTypeDto carTypeDto  = carTypeMapper.mapToCarTypeDto(carTypeService.getType(id));
        List<CarTypeDto> carTypes = carTypeMapper.mapToCarTypeDtoList(carTypeService.getAllTypes());
        model.addAttribute("carTypes", carTypes);
        model.addAttribute("carTypeDto", carTypeDto);
        model.addAttribute("title", "Cars Type");
        model.addAttribute("isAdd", false);
        modelAndView.setViewName("carType");
        return modelAndView;
    }

    @PutMapping(value = "/type")
    public ModelAndView update(@ModelAttribute CarTypeDto carTypeDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        CarType carType = carTypeService.saveType(carTypeMapper.mapToCarType(carTypeDto));
        if (carType != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car type is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/type");
        } else {
            model.addAttribute("errormessage", "Car type is not update, Please try again");
            model.addAttribute("carTypeDto", carTypeDto);
            modelAndView.setViewName("carType");
        }
        return modelAndView;
    }
    @Transactional
    @DeleteMapping(value = "/type/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        carTypeService.deleteType(id);
        model.addAttribute("carTypeDto", new CarTypeDto());
        redirectAttributes.addFlashAttribute("successmessage", "Car type is deleted successfully");
        modelAndView.setViewName("carType");
        return modelAndView;
    }
}
