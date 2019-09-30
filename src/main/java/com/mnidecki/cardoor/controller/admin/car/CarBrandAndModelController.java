package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarBrand;
import com.mnidecki.cardoor.domain.car.CarBrandModel;
import com.mnidecki.cardoor.domain.dto.CarBrandDto;
import com.mnidecki.cardoor.domain.dto.CarBrandModelDto;
import com.mnidecki.cardoor.mapper.CarBrandMapper;
import com.mnidecki.cardoor.mapper.CarBrandModelMapper;
import com.mnidecki.cardoor.services.DBService.DBCarBrandModelService;
import com.mnidecki.cardoor.services.DBService.DBCarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    private DBCarBrandService carBrandService;
    @Autowired
    private DBCarBrandModelService carBrandModelService;

    @ModelAttribute("allBrand")
    public List<CarBrandDto> allType() {
        return carBrandMapper.mapToCarBrandDtoList(carBrandService.findAll());
    }

    @ModelAttribute("allModel")
    public List<CarBrandModelDto> allModel() {
        return carBrandModelMapper.mapToCarBrandModelDtoList(carBrandModelService.findAll());
    }

    @GetMapping("/brand")
    public ModelAndView models(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        model.addAttribute("carBrandModels", carModels);
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("carBrandModelDto", new CarBrandModelDto());
        model.addAttribute("carBrandDto", new CarBrandDto());
        model.addAttribute("title", "Cars Models");
        model.addAttribute("carBrandModelDtoEmpty", new CarBrandModelDto());
        model.addAttribute("isAdd", false);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}")
    public ModelAndView brand(@PathVariable Long brandId, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandDto carBrandDto = carBrandMapper.mapToCarBrandDto(carBrandService.findByID(brandId));

        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        model.addAttribute("carBrandModels", carModels);
        model.addAttribute("carBrandModelDto", new CarBrandModelDto());
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("carBrandModelDtoEmpty", new CarBrandModelDto());
        model.addAttribute("carBrandDto", carBrandDto);
        model.addAttribute("title", "Cars Models");
        model.addAttribute("isBrandAdd", true);
        model.addAttribute("isModelAdd", false);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @GetMapping("/brand/{brandId}/model/{modelId}")
    public ModelAndView model(@PathVariable Long brandId, @PathVariable Long modelId, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModelDto carBrandModelDto = carBrandModelMapper.mapToCarBrandModelDto(carBrandModelService.findByID(modelId));

        List<CarBrandDto> carBrands = allType();
        List<CarBrandModelDto> carModels = allModel();
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("carBrandModelDto", carBrandModelDto);
        model.addAttribute("carBrandModelDtoEmpty", new CarBrandModelDto());
        model.addAttribute("carBrandDto", new CarBrandDto());
        model.addAttribute("title", "Cars Models");
        model.addAttribute("isBrandAdd", false);
        model.addAttribute("isModelAdd", true);
        modelAndView.setViewName("carBrand");
        return modelAndView;
    }

    @PostMapping(value = "/brand/{brandId}/model")
    public ModelAndView saveModel(@PathVariable Long brandId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car model is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            model.addAttribute("errormessage", "Car model is not save, Please try again");
            model.addAttribute("carBrandModelDto", carBrandModelDto);
            modelAndView.setViewName("redirect:/admin/car/brand");
        }
        return modelAndView;
    }

    @PostMapping(value = "/brand")
    public ModelAndView saveBrand(@ModelAttribute CarBrandDto carBrandDto, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        if (carBrand != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car brand is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            model.addAttribute("errormessage", "Car brand is not save, Please try again");
            model.addAttribute("carBrandDto", carBrandDto);
            modelAndView.setViewName("carBrand");
        }
        return modelAndView;
    }


    @PutMapping(value = "/brand/{brandId}/model/{modelId}")
    public ModelAndView updateModel(@PathVariable Long brandId, @PathVariable Long modelId, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car model is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            model.addAttribute("errormessage", "Car model is not update, Please try again");
            model.addAttribute("carBrandModelDto", carBrandModelDto);
            modelAndView.setViewName("carBrand");
        }
        return modelAndView;
    }

    @PutMapping(value = "/brand/{brandId}")
    public ModelAndView updateBrand(@PathVariable Long brandId, @ModelAttribute CarBrandDto carBrandDto, @ModelAttribute CarBrandModelDto carBrandModelDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        CarBrand carBrand = carBrandService.save(carBrandMapper.mapToCarBrand(carBrandDto));
        CarBrandModel carBrandModel = carBrandModelService.save(carBrandModelMapper.mapToCarBrandModel(carBrandModelDto));
        if (carBrand != null || carBrandModel != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car recrod is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/brand");
        } else {
            model.addAttribute("errormessage", "Car record is not update, Please try again");
            model.addAttribute("carBrandDto", carBrandDto);
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
