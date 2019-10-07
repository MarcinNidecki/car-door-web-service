package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandDto;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import com.mnidecki.cardoor.mapper.CarBrandMapper;
import com.mnidecki.cardoor.mapper.CarBrandModelMapper;
import com.mnidecki.cardoor.services.DBService.CarBrandModelService;
import com.mnidecki.cardoor.services.DBService.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@CrossOrigin(origins = "*")
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
        modelAndView.addObject("carBrands", carBrands);
        modelAndView.addObject("carBrandModelDto", new CarBrandModelDto());
        modelAndView.addObject("carBrandDto", new CarBrandDto());
        modelAndView.addObject("title", "Cars Models");
        modelAndView.addObject("carBrandModelDtoEmpty", new CarBrandModelDto());
        modelAndView.addObject("isAdd", false);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}")
    public ModelAndView brand(@PathVariable Long brandId) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandDto carBrandDto = carBrandMapper.mapToCarBrandDto(carBrandService.findByID(brandId));
        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        modelAndView.addObject("carBrandModels", carModels);
        modelAndView.addObject("carBrandModelDto", new CarBrandModelDto());
        modelAndView.addObject("carBrands", carBrands);
        modelAndView.addObject("carBrandModelDtoEmpty", new CarBrandModelDto());
        modelAndView.addObject("carBrandDto", carBrandDto);
        modelAndView.addObject("title", "Cars Models");
        modelAndView.addObject("isBrandAdd", true);
        modelAndView.addObject("isModelAdd", false);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}/model/{modelId}")
    public ModelAndView model(@PathVariable Long brandId, @PathVariable Long modelId) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModelDto carBrandModelDto = carBrandModelMapper.mapToCarBrandModelDto(carBrandModelService.findByID(modelId));
        List<CarBrandDto> carBrands = allType();
        modelAndView.addObject("carBrands", carBrands);
        modelAndView.addObject("carBrandModelDto", carBrandModelDto);
        modelAndView.addObject("carBrandModelDtoEmpty", new CarBrandModelDto());
        modelAndView.addObject("carBrandDto", new CarBrandDto());
        modelAndView.addObject("title", "Cars Models");
        modelAndView.addObject("isBrandAdd", false);
        modelAndView.addObject("isModelAdd", true);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @PostMapping(value = "/brand/{brandId}/model")
    public ModelAndView saveModel(@PathVariable Long brandId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car model is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            modelAndView.addObject("errormessage", "Car model is not save, Please try again");
            modelAndView.addObject("carBrandModelDto", carBrandModelDto);
            modelAndView.setViewName("redirect:/admin/car/brand");
        }
        return modelAndView;
    }

    @PostMapping(value = "/brand")
    public ModelAndView saveBrand(@ModelAttribute CarBrandDto carBrandDto, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        if (carBrand != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car brand is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            modelAndView.addObject("errormessage", "Car brand is not save, Please try again");
            modelAndView.addObject("carBrandDto", carBrandDto);
            modelAndView.setViewName("carBrand");
        }
        return modelAndView;
    }


    @PutMapping(value = "/brand/{brandId}/model/{modelId}")
    public ModelAndView updateModel(@PathVariable Long brandId, @PathVariable Long modelId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes)  {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car model is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            modelAndView.addObject("errormessage", "Car model is not update, Please try again");
            modelAndView.addObject("carBrandModelDto", carBrandModelDto);
            modelAndView.setViewName("carBrand");
        }
        return modelAndView;
    }

    @PutMapping(value = "/brand/{brandId}")
    public ModelAndView updateBrand(@PathVariable Long brandId, @ModelAttribute CarBrandDto carBrandDto, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes)  {
        ModelAndView modelAndView = new ModelAndView();
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrand != null || carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car recrod is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            modelAndView.addObject("errormessage", "Car record is not update, Please try again");
            modelAndView.addObject("carBrandDto", carBrandDto);
            modelAndView.setViewName("carBrand");
        }
        return modelAndView;
    }

    @DeleteMapping(value = "/brand/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        carBrandService.deleteById(id);
        redirectAttributes.addFlashAttribute("successmessage", "Car brand is deleted successfully");
        modelAndView.setViewName("redirect:/admin/car/brand");
        return modelAndView;
    }

    @DeleteMapping(value = "/brand/{brandId}/model/{modelId}")
    public ModelAndView delete(@PathVariable Long brandId, @PathVariable Long modelId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        carBrandModelService.delete(modelId);
        redirectAttributes.addFlashAttribute("successmessage", "Car model is deleted successfully");
        modelAndView.setViewName("redirect:/admin/car/brand");
        return modelAndView;
    }


}
