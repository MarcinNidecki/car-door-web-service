package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandDto;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import com.mnidecki.cardoor.mapper.CarBrandMapper;
import com.mnidecki.cardoor.mapper.CarBrandModelMapper;
import com.mnidecki.cardoor.services.DBService.CarBrandModelService;
import com.mnidecki.cardoor.services.DBService.CarBrandService;
import com.mnidecki.cardoor.services.DBService.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.*;
@Transactional
@RestController
@RequestMapping("/admin/car/")
public class CarBrandAndModelController {


    @Autowired
    private CarBrandMapper carBrandMapper;
    @Autowired
    private CarBrandModelMapper carBrandModelMapper;
    @Autowired
    private CarBrandService carBrandService;
    @Autowired
    private CarBrandModelService carBrandModelService;
    @Autowired
    private CarService carService;

    @ModelAttribute("allBrand")
    public List<CarBrandDto> allType() {
        return carBrandMapper.mapToCarBrandDtoList(carBrandService.findAll());
    }

    @ModelAttribute("allModel")
    public List<CarBrandModelDto> allModel() {
        return carBrandModelMapper.mapToCarBrandModelDtoList(carBrandModelService.findAll());
    }

    @GetMapping("/brand")
    public ModelAndView models() {
        ModelAndView modelAndView = new ModelAndView();

        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        modelAndView.addObject("carBrandModels", carModels);
        modelAndView.addObject(CAR_SERVICE, carService);
        modelAndView.addObject(CAR_BRANDS, carBrands);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO, new CarBrandModelDto());
        modelAndView.addObject(CAR_BRAND_DTO, new CarBrandDto());
        modelAndView.addObject(TITLE, CARS_MODELS);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO_EMPTY, new CarBrandModelDto());
        modelAndView.addObject(IS_ADD, false);
        modelAndView.addObject("isBrandAdd", false);
        modelAndView.addObject("isModelAdd", false);
        modelAndView.setViewName(CAR_BRAND);
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}")
    public ModelAndView brand(@PathVariable Long brandId) {
        ModelAndView modelAndView = new ModelAndView();
        
        CarBrandDto carBrandDto = carBrandMapper.mapToCarBrandDto(carBrandService.findByID(brandId));
        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        modelAndView.addObject("carBrandModels", carModels);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO, new CarBrandModelDto());
        modelAndView.addObject(CAR_BRANDS, carBrands);
        modelAndView.addObject(CAR_SERVICE, carService);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO_EMPTY, new CarBrandModelDto());
        modelAndView.addObject(CAR_BRAND_DTO, carBrandDto);
        modelAndView.addObject(TITLE, CARS_MODELS);
        modelAndView.addObject("isBrandAdd", true);
        modelAndView.addObject("isModelAdd", false);
        modelAndView.setViewName(CAR_BRAND);
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}/model/{modelId}")
    public ModelAndView model(@PathVariable Long brandId, @PathVariable Long modelId) {
        ModelAndView modelAndView = new ModelAndView();

        CarBrandModelDto carModelDto = carBrandModelMapper.mapToCarBrandModelDto(
                carBrandModelService.findByID(modelId));
        List<CarBrandDto> carBrands = allType();
        modelAndView.addObject(CAR_BRANDS, carBrands);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO, carModelDto);
        modelAndView.addObject(CAR_BRAND_MODEL_DTO_EMPTY, new CarBrandModelDto());
        modelAndView.addObject(CAR_SERVICE, carService);
        modelAndView.addObject(CAR_BRAND_DTO, new CarBrandDto());
        modelAndView.addObject(TITLE, CARS_MODELS);
        modelAndView.addObject("isBrandAdd", false);
        modelAndView.addObject("isModelAdd", true);
        modelAndView.setViewName(CAR_BRAND);
        return modelAndView;
    }

    @PostMapping(value = "/brand/{brandId}/model")
    public ModelAndView saveModel(@PathVariable Long brandId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car model is saved successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_BRAND);
        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car model is not save, Please try again");
            modelAndView.addObject(CAR_BRAND_MODEL_DTO, carBrandModelDto);
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_BRAND);
        }
        return modelAndView;
    }

    @PostMapping(value = "/brand")
    public ModelAndView saveBrand(@ModelAttribute CarBrandDto carBrandDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        if (carBrand != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car brand is saved successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_BRAND);
        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car brand is not save, Please try again");
            modelAndView.addObject(CAR_BRAND_DTO, carBrandDto);
            modelAndView.setViewName(CAR_BRAND);
        }

        return modelAndView;
    }


    @PutMapping(value = "/brand/{brandId}/model/{modelId}")
    public ModelAndView updateModel(@PathVariable Long brandId, @PathVariable Long modelId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes)  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CAR_SERVICE, carService);
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car model is updated successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_BRAND);
        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car model is not update, Please try again");
            modelAndView.addObject(CAR_BRAND_DTO, new CarBrandDto());
            modelAndView.addObject(CAR_BRAND_MODEL_DTO, carBrandModelDto);
            modelAndView.setViewName(CAR_BRAND);
        }
        return modelAndView;
    }

    @PutMapping(value = "/brand/{brandId}")
    public ModelAndView updateBrand(@PathVariable Long brandId, @ModelAttribute CarBrandDto carBrandDto, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes)  {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CAR_SERVICE, carService);
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrand != null || carBrandModel != null) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car recrod is updated successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_BRAND);
        } else {
            modelAndView.addObject(ERRORMESSAGE, "Car record is not update, Please try again");
            modelAndView.addObject(CAR_BRAND_DTO, carBrandDto);
            modelAndView.setViewName(CAR_BRAND);
        }
        return modelAndView;
    }

    @DeleteMapping(value = "/brand/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CAR_SERVICE, carService);
        carBrandService.deleteById(id);
        modelAndView.addObject(CAR_BRAND_DTO, new CarBrandDto());
        modelAndView.addObject(CAR_BRAND_MODEL_DTO_EMPTY, new CarBrandModelDto());
        modelAndView.addObject(SUCCESSMESSAGE, "Car brand is deleted successfully");
        modelAndView.setViewName(CAR_BRAND);
        return modelAndView;
    }

    @DeleteMapping(value = "/brand/{brandId}/model/{modelId}")
    public ModelAndView delete(@PathVariable Long brandId, @PathVariable Long modelId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CAR_BRAND_DTO, new CarBrandDto());
        modelAndView.addObject(CAR_BRAND_MODEL_DTO_EMPTY, new CarBrandModelDto());
        modelAndView.addObject(CAR_SERVICE, carService);
        carBrandModelService.delete(modelId);
        modelAndView.addObject(SUCCESSMESSAGE, "Car model is deleted successfully");
        modelAndView.setViewName(CAR_BRAND);
        return modelAndView;
    }


}
