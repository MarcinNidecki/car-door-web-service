package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.DBBookingService;
import com.mnidecki.cardoor.services.DBService.DBCarService;
import com.mnidecki.cardoor.services.DBService.DBLocationService;
import com.mnidecki.cardoor.services.DBService.DBUserService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private DBBookingService bookingService;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private DBCarService carService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private DBLocationService locationService;
    @Autowired
    private DBLocationService cityService;
    @Autowired
    private DBUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookingExtrasItemMapper bookingExtrasItemMapper;

    @ModelAttribute("allCity")
    public List<LocationDto> allCity() {
        return locationMapper.mapToLocationDtoList(this.cityService.getAllLocations());
    }


    @GetMapping("car")
    public  ModelAndView searchAndFilter(
            @And({
            @Spec(path="carParameters.doorsNumber", params={"minDoor","maxDoor"},  spec= Between.class),
            @Spec(path="carParameters.seatsNumber", params={"minSeat","maxSeat"}, spec= Between.class),
            @Spec(path="carParameters.airConditioning", params="airCon", spec=Equal.class),
            @Spec(path="carParameters.transmissionIsAutomatic", params="automatic", spec=Equal.class),
            @Spec(path="carParameters.fuelType", params="fuel", spec= Like.class),
            @Spec(path="carParameters.smallBags", params={"minSmallBag","maxSmallBag"}, spec= Between.class),
            @Spec(path="carParameters.bigBags", params={"minLargeBag","maxLargeBag"}, spec= Between.class),
            }) Specification<Car> carSpecification, @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "endTime") String endTime, @RequestParam(value = "cityId") Long cityId, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        List<CarDto> carList =  carMapper.mapToCarDtoList(carService.getAllAvailableCar(carSpecification, startDate, startTime, endDate, endTime,cityId));
        model.addAttribute("cars", carList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("cityId", cityId);
        model.addAttribute("booking", new BookingSpecDto());
        modelAndView.setViewName("booking");
        return modelAndView;
    }

    @GetMapping("car/{carId}/booking")
    public  ModelAndView  chooseExtrasItem (
            @RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "cityId") Long cityId, @PathVariable Long carId, Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        CarDto car = carMapper.mapToCarDto(carService.getCar(carId));
        LocationDto location = locationMapper.mapToLocationDto(locationService.getLocation(cityId));
        BookingItemCreationDto booking = new BookingItemCreationDto(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList(bookingService.prepareEmptyExtrasList()));
        long daysOfRent =  bookingService.countBookingDays(bookingService.timeToTimestampConverter(startDate,startTime),bookingService.timeToTimestampConverter(endDate,endTime));

        session.setAttribute("car", car);
        session.setAttribute("location",location);
        session.setAttribute("daysOfRent", daysOfRent);
        model.addAttribute("bookingExtras", booking);
        modelAndView.setViewName("carBooking");
        return modelAndView;
    }


    @RequestMapping(value = {"car/{carId}/booking/checkout"}, method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public  ModelAndView bookingCheckout(@RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
                                         @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
                                         @RequestParam(value = "cityId") Long cityId, @PathVariable(value = "carId") Long carId,
                                         @ModelAttribute(value = "bookingExtras") BookingItemCreationDto bookingExtras, Model model, RedirectAttributes redirectAttributes, HttpSession session)  {
        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("isAdd", false);

        if (session.getAttribute("car") == null ||session.getAttribute("daysOfRent") == null) {
            redirectAttributes.addFlashAttribute("errormessage", "Your session expired try again");
            modelAndView.setViewName("redirect:/");
        } else {
            UserDto user = userMapper.maptoUserDto(userService.getUserFromAuthentication());
            BookingDto userBooking = new BookingDto(carId,1L,cityId,
                    BigDecimal.ZERO, bookingService.timeToTimestampConverter(startDate,startTime), bookingService.timeToTimestampConverter(endDate,endTime), bookingExtras.getItems());
            userBooking = bookingMapper.mapToBookingDto(bookingService.setAllBookingCostFields(bookingMapper.mapToBooking(userBooking)));
            session.setAttribute("userBooking",userBooking);
            model.addAttribute("userDto", user);
            session.setAttribute("bookingExtras", bookingExtras);
            modelAndView.setViewName("bookingCheckout");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"car/{carId}/booking/checkout"}, method = {RequestMethod.GET})
    public  ModelAndView  bookingCheckout (@PathVariable(value = "carId") Long carId, Model model, RedirectAttributes redirectAttributes, HttpSession session)  {
        ModelAndView modelAndView = new ModelAndView();
        if (session.getAttribute("car") == null ||session.getAttribute("daysOfRent") == null) {
            redirectAttributes.addFlashAttribute("errormessage", "Your session expired try again");
            modelAndView.setViewName("redirect:/");
        } else {
            UserDto user = userMapper.maptoUserDto(userService.getUserFromAuthentication());
            model.addAttribute("userDto", user);
            modelAndView.setViewName("bookingCheckout");
        }
        return modelAndView;
    }


    @RequestMapping(value = {"car/{carId}/booking/"}, method = {RequestMethod.POST})
    public  ModelAndView  saveBooking (@PathVariable(value = "carId") Long carId, @Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session, Model model)  {
        System.out.println(userDto.getAddressLine1());
        System.out.println(userDto.getEmail());
        System.out.println(userDto.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        if (session.getAttribute("userBooking") == null ||session.getAttribute("daysOfRent") == null) {
            redirectAttributes.addFlashAttribute("errormessage", "Your session expired try again");
            modelAndView.setViewName("redirect:/");
        }
        if (userService.isUserExist(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
                System.out.println(bindingResult.getFieldError());
                modelAndView.setViewName("bookingCheckout");
        } else {
            User user = userService.save(userMapper.mapToUser(userDto));
            if (user.getStatus()!=1) {
                userService.sendConfirmationToken(user);
            }
            Booking booking = bookingMapper.mapToBooking((BookingDto) session.getAttribute("userBooking"));
            booking.setUser(user);
            bookingService.saveBooking(booking);
            modelAndView.addObject("email", user.getEmail());
            modelAndView.addObject("msg", "User has been registered successfully");
            modelAndView.addObject("user", new UserDto());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("booking" , new BookingSpecDto());
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
