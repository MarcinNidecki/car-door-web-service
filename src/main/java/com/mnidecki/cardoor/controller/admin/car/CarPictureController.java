package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.config.FtpConfig;
import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarDto;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import com.mnidecki.cardoor.domain.dto.LocationDto;
import com.mnidecki.cardoor.mapper.CarMapper;
import com.mnidecki.cardoor.mapper.CarPictureMapper;
import com.mnidecki.cardoor.mapper.CarTypeMapper;
import com.mnidecki.cardoor.mapper.LocationMapper;
import com.mnidecki.cardoor.services.DBService.*;
import com.mnidecki.cardoor.services.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/car/")
public class CarPictureController {

    private final FtpService ftpService = new FtpService();
    @Autowired
    private DBLocationService locationService;
    @Autowired
    private DBCarService carService;
    @Autowired
    private DBCarParameters carParametersService;
    @Autowired
    private DBCarTypeService carTypeService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired
    private CarPictureMapper carPictureMapper;
    @Autowired
    private DBCarPicture pictureService;
    @Autowired
    private FtpConfig ftpConfig;

    @ModelAttribute("allCity")
    public List<LocationDto> allCity() {
        return locationMapper.mapToLocationDtoList(this.locationService.getAllLocations());
    }




    @ModelAttribute("allCar")
    public List<CarDto> allCar() {
        return carMapper.mapToCarDtoList(this.carService.getAllCars());
    }

    @GetMapping("/picture")
    public ModelAndView cars(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<CarPictureDto> carsPictures = carPictureMapper.mapToCarPictureDtoList(pictureService.getAllCarPictures());
        model.addAttribute("carsPictures", carsPictures);
        model.addAttribute("carPictureDto", new CarPictureDto());
        model.addAttribute("carParametersService", carParametersService);
        model.addAttribute("title", "Cars Picture");
        model.addAttribute("isAdd", true);
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @PostMapping(value = "/picture")
    public ModelAndView save(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        model.addAllAttributes(ftpService.uploadPicture(carPictureDto, redirectAttributes).getFlashAttributes());
        CarPicture carPicture = pictureService.saveCarPicture(carPictureMapper.mapToCarPicture(carPictureDto));
        if (carPicture != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car is saved successfully");
            modelAndView.setViewName("redirect:/admin/car/picture");

        } else {
            model.addAttribute("errormessage", "Car is not save, Please try again");
            model.addAttribute("carPictureDto", carPictureDto);
            modelAndView.setViewName("carsPicture");
        }
        return modelAndView;
    }

    @GetMapping(value = "/picture/{id}")
    public ModelAndView getPicture(@PathVariable Long id, Model model) throws IOException, SQLException {
        ModelAndView modelAndView = new ModelAndView();
        CarPictureDto carPictureDto = carPictureMapper.mapToCarPictureDto(pictureService.getCarPicture(id));
        List<CarPictureDto> carsPictures = carPictureMapper.mapToCarPictureDtoList(pictureService.getAllCarPictures());
        model.addAttribute("carsPictures", carsPictures);
        model.addAttribute("carPictureDto", carPictureDto);
        model.addAttribute("carParametersService", carParametersService);
        model.addAttribute("title", "Cars Picture");
        model.addAttribute("isAdd", false);
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @PutMapping(value = "/picture")
    public ModelAndView update(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (!carPictureDto.getFile().isEmpty()) {
            model.addAllAttributes(ftpService.uploadPicture(carPictureDto, redirectAttributes).getFlashAttributes());
        }
        CarPicture carPicture = pictureService.saveCarPicture(carPictureMapper.mapToCarPicture(carPictureDto));
        if (carPicture != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Car picture is updated successfully");
            modelAndView.setViewName("redirect:/admin/car/picture");
        } else {
            redirectAttributes.addFlashAttribute("errormessage", "Car picture is not update, Please try again");
             model.addAttribute("cartPictureDto", carPictureDto);
             modelAndView.setViewName("redirect:/admin/car/picture");
        }
        return modelAndView;
    }

    @Transactional
    @DeleteMapping(value = "/picture/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        pictureService.deleteCarPicture(id);
        model.addAttribute("carPictureDto", new CarPictureDto());
        redirectAttributes.addFlashAttribute("successmessage", "Car picture is deleted successfully");
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @RequestMapping(value = "/picturejson", method = RequestMethod.GET)
    public @ResponseBody CarPictureDto findPictureById(@RequestParam(value = "pictureId", required = true) Long pictureId) {
        if (pictureId==0) {
            return new CarPictureDto();
        } else {
            return carPictureMapper.mapToCarPictureDto(pictureService.getCarPicture(pictureId));
        }

    }

}
