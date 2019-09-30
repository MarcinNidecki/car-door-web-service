package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController("AdminCarController")
@RequestMapping("/admin")
public class CarController {

    @Autowired
    private DBLocationService locationService;
    @Autowired
    private DBCarService carService;
    @Autowired
    private DBCarTypeService carTypeService;
    @Autowired
    private DBCarPicture carPictureService;
    @Autowired
    private DBCarParameters carParametersService;
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
    private DBCarBrandService carBrandService;
    @Autowired
    private CarBrandModelMapper carBrandModelMapper;
    @Autowired
    private DBCarBrandModelService carBrandModelService;

    @ModelAttribute("allCity")
    public List<LocationDto> allCity() {
        return locationMapper.mapToLocationDtoList(this.locationService.findAll());
    }

    @ModelAttribute("allType")
    public List<CarTypeDto> allType() {
        return carTypeMapper.mapToCarTypeDtoList(this.carTypeService.findAll());
    }

    @ModelAttribute("allCar")
    public List<CarDto> allCar() {
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
    public ModelAndView cars(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<CarDto> cars = carMapper.mapToCarDtoList(carService.findAll());
        model.addAttribute("cars", cars);
        model.addAttribute("carDto", new CarDto());
        model.addAttribute("title", "Cars");
        model.addAttribute("afterPrice", "/Day");
        model.addAttribute("beforePrice", " $");
        model.addAttribute("isAdd", false);
        modelAndView.setViewName("cars");
        return modelAndView;
    }


    @PostMapping(value = "/car")
    public ModelAndView save(@Valid @ModelAttribute CarDto carDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("isAdd", false);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            modelAndView.setViewName("cars");
        } else {
            CarParameters carParameters = carParametersService.saveCarParameters(carMapper.mapToCarParameters(carDto));
            carDto.setCarParametersId(carParameters.getId());
            Car car = carService.save(carMapper.mapToCar(carDto));
            if (car != null && car.getCarParameters() != null) {
                redirectAttributes.addFlashAttribute("successmessage", "Car is saved successfully");
                modelAndView.setViewName("redirect:/admin/car");
            } else {
                model.addAttribute("errormessage", "Car is not save, Please try again");
                model.addAttribute("carDto", carDto);
                modelAndView.setViewName("cars");
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/car/{id}")
    public ModelAndView getCar(@PathVariable Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarDto carDto = carMapper.mapToCarDto(carService.findById(id));
        List<CarDto> cars = carMapper.mapToCarDtoList(carService.findAll());
        model.addAttribute("cars", cars);
        model.addAttribute("carDto", carDto);
        model.addAttribute("afterPrice", "/Day");
        model.addAttribute("beforePrice", " $");
        model.addAttribute("title", "Cars");
        model.addAttribute("isAdd", true);
        modelAndView.setViewName("cars");
        return modelAndView;
    }

    @PutMapping(value = "/car")
    public ModelAndView update(@Valid @ModelAttribute CarDto carDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("isAdd", true);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            modelAndView.setViewName("cars");
        } else {
            CarParameters carParameters = carParametersService.saveCarParameters(carMapper.mapToCarParameters(carDto));
            carDto.setCarParametersId(carParameters.getId());
            Car car = carService.save(carMapper.mapToCar(carDto));
            if (car != null && car.getCarParameters() != null) {
                redirectAttributes.addFlashAttribute("successmessage", "Car is updated successfully");
                modelAndView.setViewName("redirect:/admin/car");
            } else {
                model.addAttribute("errormessage", "Car is not updated, Please try again");
                model.addAttribute("carDto", carDto);
                modelAndView.setViewName("cars");
            }
        }
        return modelAndView;
    }

    @Transactional
    @GetMapping(value = "/car/delete/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        carService.deleteByID(id);
        redirectAttributes.addFlashAttribute("successmessage", "Car is deleted successfully");
        modelAndView.setViewName("redirect:/admin/car/");
        return modelAndView;
    }

    @RequestMapping(value = "/car/modelbeforePrice", method = RequestMethod.GET)
    public @ResponseBody
    List<CarBrandModelDto> findAllModels(@RequestParam(value = "brandId") Long brandId) {
        return carBrandModelMapper.mapToCarBrandModelDtoList(carBrandModelService.getAllModelByBrand_Id(brandId));
    }
}
