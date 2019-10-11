package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import com.mnidecki.cardoor.mapper.CarPictureMapper;
import com.mnidecki.cardoor.services.DBService.CarParametersService;
import com.mnidecki.cardoor.services.DBService.CarPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.*;

@RestController
@RequestMapping("/admin/car")
public class CarPictureController {
    private static Logger LOGGER = LoggerFactory.getLogger(CarPictureController.class);
    @Autowired
    private CarParametersService carParametersService;
    @Autowired
    private CarPictureMapper carPictureMapper;
    @Autowired
    private CarPictureService pictureService;

    @GetMapping("/picture")
    public ModelAndView cars() {
        ModelAndView modelAndView = new ModelAndView();
        List<CarPictureDto> carsPictures = carPictureMapper.mapToCarPictureDtoList(pictureService.findAll());
        modelAndView.addObject("carsPictures", carsPictures);
        modelAndView.addObject(CAR_PICTURE_DTO, new CarPictureDto());
        modelAndView.addObject(CAR_PARAMETERS_SERVICE, carParametersService);
        modelAndView.addObject(IS_ADD, true);
        modelAndView.setViewName(CARS_PICTURE);
        return modelAndView;
    }

    @PostMapping(value = "/picture")
    public ModelAndView save(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        CarPicture carPicture = carPictureMapper.mapToCarPicture(carPictureDto);
        if (!carPicture.getFile().isEmpty() && !carPicture.getDescriptions().isEmpty()) {
            carPicture = pictureService.save(carPicture);
            if (pictureService.isFileNameTheSameLikeFileNamePath(carPicture)) {
                redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Picture " + carPicture.getFileName() +
                        "." + carPicture.getFileExtension() + " saved successfully");
                modelAndView.setViewName(REDIRECT_ADMIN_CAR_PICTURE);
                return modelAndView;
            }
        }
        modelAndView.addObject(ERRORMESSAGE, "We could not save your picture. Fill all the field");
        modelAndView.addObject(CAR_PICTURE_DTO, carPictureDto);
        modelAndView.addObject(IS_ADD, true);
        modelAndView.setViewName(CARS_PICTURE);
        return modelAndView;
    }




    @GetMapping(value = "/picture/{id}")
    public ModelAndView getPicture(@PathVariable Long id)  {
        ModelAndView modelAndView = new ModelAndView();
        CarPictureDto carPictureDto = carPictureMapper.mapToCarPictureDto(pictureService.findById(id));
        List<CarPictureDto> carsPictures = carPictureMapper.mapToCarPictureDtoList(pictureService.findAll());
        modelAndView.addObject("carsPictures", carsPictures);
        modelAndView.addObject(CAR_PICTURE_DTO, carPictureDto);
        modelAndView.addObject(CAR_PARAMETERS_SERVICE, carParametersService);
        modelAndView.addObject(IS_ADD, false);
        modelAndView.setViewName(CARS_PICTURE);
        return modelAndView;
    }

    @PutMapping(value = "/picture")
    public ModelAndView update(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        CarPicture carPicture = carPictureMapper.mapToCarPicture(carPictureDto);
        if (!carPicture.getDescriptions().isEmpty()) {
            carPicture = pictureService.save(carPicture);
            LOGGER.info(pictureService.isFileNameTheSameLikeFileNamePath(carPicture)+" controler");
            if (pictureService.isFileNameTheSameLikeFileNamePath(carPicture)) {
                redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Picture was updated successfully");
                modelAndView.setViewName(REDIRECT_ADMIN_CAR_PICTURE);
                return modelAndView;
            }
        }
        modelAndView.addObject(ERRORMESSAGE, "We could not update your picture. Fill all the field");
        modelAndView.addObject(CAR_PICTURE_DTO, carPictureDto);
        modelAndView.addObject(IS_ADD, false);
        modelAndView.setViewName(CARS_PICTURE);
        return modelAndView;
    }

    @Transactional
    @DeleteMapping(value = "/picture/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CAR_PARAMETERS_SERVICE, carParametersService);
        pictureService.deleteById(id);
        modelAndView.addObject(SUCCESSMESSAGE, "Car picture is deleted successfully");
        modelAndView.addObject(CAR_PICTURE_DTO, new CarPictureDto());
        modelAndView.setViewName(CARS_PICTURE);
        return modelAndView;
    }

    @GetMapping(value = "/picturejson")
    public @ResponseBody CarPictureDto findPictureById(@RequestParam(value = "pictureId", required = true) Long pictureId) {
        if (pictureId <= 0) {
            return new CarPictureDto();
        } else {
            return carPictureMapper.mapToCarPictureDto(pictureService.findById(pictureId));
        }
    }

}
