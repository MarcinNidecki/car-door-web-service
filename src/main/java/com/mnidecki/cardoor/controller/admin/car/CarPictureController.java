package com.mnidecki.cardoor.controller.admin.car;

import com.mnidecki.cardoor.domain.car.CarPicture;
import com.mnidecki.cardoor.domain.dto.CarPictureDto;
import com.mnidecki.cardoor.mapper.CarPictureMapper;
import com.mnidecki.cardoor.services.DBService.CarParametersService;
import com.mnidecki.cardoor.services.DBService.CarPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/car")
public class CarPictureController {

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
        modelAndView.addObject("carPictureDto", new CarPictureDto());
        modelAndView.addObject("carParametersService", carParametersService);
        modelAndView.addObject("isAdd", true);
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @PostMapping(value = "/picture")
    public ModelAndView save(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isAdd", true);
        CarPicture  carPicture = pictureService.save(carPictureMapper.mapToCarPicture(carPictureDto));
        if (carPicture != null && pictureService.isFileNameTheSameLikeFileNamePath(carPicture)) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE,
                    "Picture " + carPicture.getFileName()+ "."+ carPicture.getFileExtension() +" saved successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_PICTURE);
        } else {
            modelAndView.addObject(ERRORMESSAGE, "We could not save your picture." +carPictureDto.getFile().getOriginalFilename()+" Please" +
                    " try again" +
                    " later");
            modelAndView.addObject("carPictureDto", carPictureDto);
            modelAndView.setViewName("carsPicture");
        }
        return modelAndView;
    }

    @GetMapping(value = "/picture/{id}")
    public ModelAndView getPicture(@PathVariable Long id)  {
        ModelAndView modelAndView = new ModelAndView();
        CarPictureDto carPictureDto = carPictureMapper.mapToCarPictureDto(pictureService.findById(id));
        List<CarPictureDto> carsPictures = carPictureMapper.mapToCarPictureDtoList(pictureService.findAll());
        modelAndView.addObject("carsPictures", carsPictures);
        modelAndView.addObject("carPictureDto", carPictureDto);
        modelAndView.addObject("carParametersService", carParametersService);
        modelAndView.addObject("isAdd", false);
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @PutMapping(value = "/picture")
    public ModelAndView update(@ModelAttribute CarPictureDto carPictureDto, RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        CarPicture  carPicture = pictureService.save(carPictureMapper.mapToCarPicture(carPictureDto));
        if (carPicture != null && pictureService.isFileNameTheSameLikeFileNamePath(carPicture)) {
            redirectAttributes.addFlashAttribute(SUCCESSMESSAGE, "Car picture is updated successfully");
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_PICTURE);
        } else {
            redirectAttributes.addFlashAttribute(ERRORMESSAGE, "Car picture is not update, Please try again");
            modelAndView.addObject("cartPictureDto", carPictureDto);
            modelAndView.setViewName(REDIRECT_ADMIN_CAR_PICTURE);
        }
        return modelAndView;
    }

    @Transactional
    @DeleteMapping(value = "/picture/{id}")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("carParametersService", carParametersService);
        pictureService.deleteById(id);
        modelAndView.addObject(SUCCESSMESSAGE, "Car picture is deleted successfully");
        modelAndView.addObject("carPictureDto", new CarPictureDto());
        modelAndView.setViewName("carsPicture");
        return modelAndView;
    }

    @RequestMapping(value = "/picturejson", method = RequestMethod.GET)
    public @ResponseBody CarPictureDto findPictureById(@RequestParam(value = "pictureId", required = true) Long pictureId) {
        if (pictureId <= 0) {
            return new CarPictureDto();
        } else {
            return carPictureMapper.mapToCarPictureDto(pictureService.findById(pictureId));
        }
    }

}
