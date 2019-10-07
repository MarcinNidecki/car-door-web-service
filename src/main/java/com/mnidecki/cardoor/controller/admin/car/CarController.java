package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class CarController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private CarService carService;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private CarPictureService carPictureService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired
    private CarPictureMapper carPictureMapper;
    @Autowired
    private CarBrandMapper carBrandMapper;
    @Autowired
    private CarBrandService carBrandService;
    @Autowired
    private CarBrandModelMapper carBrandModelMapper;
    @Autowired
    private CarBrandModelService carBrandModelService;

    @ModelAttribute("allCity")
    public List<LocationDto> getAllCity() {
        return locationMapper.mapToLocationDtoList(this.locationService.findAll());
    }

    @ModelAttribute("allType")
    public List<CarTypeDto> getAllType() {
        return carTypeMapper.mapToCarTypeDtoList(this.carTypeService.findAll());
    }

    @ModelAttribute("allCar")
    public List<CarDto> getAllCars() {
        return carMapper.mapToCarDtoList(this.carService.findAll());
    }

    @ModelAttribute("allCarPicture")
    public List<CarPictureDto> allCarPicture() {
        return carPictureMapper.mapToCarPictureDtoList(this.carPictureService.findAll());
    }

    @ModelAttribute("allBrand")
    public List<CarBrandDto> allBrand() {
        return carBrandMapper.mapToCarBrandDtoList(carBrandService.findAll());
    }


    @GetMapping("/car")
    public ModelAndView cars() {
        ModelAndView modelAndView = new ModelAndView();
        List<CarDto> cars = carMapper.mapToCarDtoList(carService.findAll());
        modelAndView.addObject("cars", cars);
        modelAndView.addObject("carDto", new CarDto());
        modelAndView.addObject("afterPrice", "/Day");
        modelAndView.addObject("beforePrice", " $");
        modelAndView.addObject("isAdd", false);
        modelAndView.setViewName("cars");
        return modelAndView;
    }


    @PostMapping(value = "/car")
    public ModelAndView save(@Valid @ModelAttribute CarDto carDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isAdd", false);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            modelAndView.setViewName("cars");
            return modelAndView;
        }
        Car car = carService.save(carMapper.mapToCar(carDto));
        if (car != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car is saved successfully");
            modelAndView.setViewName("redirect:/admin/car");
            return modelAndView;
        } else {
            modelAndView.addObject("errormessage", "Car is not save, Please try again");
            modelAndView.addObject("carDto", carDto);
            modelAndView.setViewName("cars");
            }

        return modelAndView;
    }

    @GetMapping(value = "/car/{id}")
    public ModelAndView getCar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        CarDto carDto = carMapper.mapToCarDto(carService.findById(id));
        List<CarDto> cars = carMapper.mapToCarDtoList(carService.findAll());
        modelAndView.addObject("cars", cars);
        modelAndView.addObject("carDto", carDto);
        modelAndView.addObject("afterPrice", "/Day");
        modelAndView.addObject("beforePrice", " $");
        modelAndView.addObject("title", "Cars");
        modelAndView.addObject("isAdd", true);
        modelAndView.setViewName("cars");
        return modelAndView;
    }

    @PutMapping(value = "/car")
    public ModelAndView update(@Valid @ModelAttribute CarDto carDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isAdd", true);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            modelAndView.setViewName("cars");
        } else {
            Car car = carService.save(carMapper.mapToCar(carDto));
            if (car != null) {
                redirectAttributes.addFlashAttribute("successmessage", "Car is updated successfully");
                modelAndView.setViewName("redirect:/admin/car");

            } else {
                modelAndView.addObject("errormessage", "Car is not updated, Please try again");
                modelAndView.addObject("carDto", carDto);
                modelAndView.setViewName("cars");
            }
        }
        return modelAndView;
    }

    @Transactional
    @DeleteMapping(value = "/car/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        carService.deleteByID(id);
        redirectAttributes.addFlashAttribute("successmessage", "Car is deleted successfully");
        modelAndView.setViewName("redirect:/admin/car");
        return modelAndView;
    }

    @RequestMapping(value = "/car/model", method = RequestMethod.GET)
    public @ResponseBody
    List<CarBrandModelDto> findAllModels(@RequestParam(value = "brandId") Long brandId) {
        return carBrandModelMapper.mapToCarBrandModelDtoList(carBrandModelService.getAllModelByBrand_Id(brandId));
    }
}
