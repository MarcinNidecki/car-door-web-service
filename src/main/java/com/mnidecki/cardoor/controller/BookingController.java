package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.client.KayakClient;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.Weather;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.AccuWeatherService;
import com.mnidecki.cardoor.services.DBService.BookingService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import com.mnidecki.cardoor.services.DBService.UserService;
import com.mnidecki.cardoor.services.DateTimeService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.*;


@RestController
@RequestMapping("/")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private CarService carService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookingExtrasItemMapper bookingExtrasItemMapper;
    @Autowired
    private AccuWeatherService accuWeatherService;
    @Autowired
    private KayakClient kayakClient;
    @Autowired
    private DateTimeService dateTimeService;

    @GetMapping("car")
    public ModelAndView searchAndFilter(
            @And({
                    @Spec(path = "carParameters.doorsNumber", params = {"minDoor", "maxDoor"}, spec = Between.class),
                    @Spec(path = "carParameters.seatsNumber", params = {"minSeat", "maxSeat"}, spec = Between.class),
                    @Spec(path = "carParameters.airConditioning", params = "airCon", spec = Equal.class),
                    @Spec(path = "carParameters.transmissionIsAutomatic", params = "automatic", spec = Equal.class),
                    @Spec(path = "carParameters.fuelType", params = "fuel", spec = Like.class),
                    @Spec(path = "carParameters.smallBags", params = {"minSmallBag", "maxSmallBag"}, spec = Between.class),
                    @Spec(path = "carParameters.bigBags", params = {"minLargeBag", "maxLargeBag"}, spec = Between.class),
            }) Specification<Car> carSpecification, @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "endTime") String endTime, @RequestParam(value = "cityId") Long cityId, RedirectAttributes redirectAttributes) {

        if (!dateTimeService.isBookingDateValid(startDate,startTime,endDate,endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }

        List<CarDto> carList = carMapper.mapToCarDtoList(carService.getAllAvailableCar(carSpecification, startDate,
                startTime, endDate, endTime, cityId));
        String cityName = locationService.findById(cityId).getCity();
        long averagePrice = kayakClient.getKayakAverageTotalCarPrice(cityName,startDate,startTime,endDate,endTime);
        Weather weather = accuWeatherService.findById(cityId);
        System.out.println(weather);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("cars", carList);
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);
        modelAndView.addObject("cityId", cityId);
        modelAndView.addObject("cityName", cityName);
        modelAndView.addObject("averagePrice",averagePrice);
        modelAndView.setViewName("booking");
        return modelAndView;
    }


    @GetMapping("car/{carId}/booking")
    public ModelAndView chooseExtrasItem(
            @RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "cityId") Long cityId, @PathVariable Long carId,
            RedirectAttributes redirectAttributes, HttpSession session) {

        if (!dateTimeService.isBookingDateValid(startDate,startTime,endDate,endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }

        CarDto car = carMapper.mapToCarDto(carService.findById(carId));
        LocationnDto location = locationMapper.mapToLocationDto(locationService.findById(cityId));
        BookingItemCreationDto bookingExtras = new BookingItemCreationDto(bookingExtrasItemMapper.mapToBookingExtrasItemDtoList
                (bookingService.prepareEmptyExtrasItemList()));
        long daysOfRent = bookingService.countBookingDays(bookingService.stringTimeToTimestampConverter(startDate, startTime),
                bookingService.stringTimeToTimestampConverter(endDate, endTime));

        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("car", car);
        session.setAttribute("location", location);
        session.setAttribute(ControllerConstant.DAYS_OF_RENT, daysOfRent);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO, getUser());
        modelAndView.addObject(BOOKING_EXTRAS, bookingExtras);
        modelAndView.setViewName("carBooking");
        return modelAndView;
    }


    @PostMapping(value = {"car/{carId}/booking/checkout"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView bookingCheckout
            (@RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
             @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
             @RequestParam(value = "cityId") Long cityId, @PathVariable(value = "carId") Long carId,
             @ModelAttribute(value = BOOKING_EXTRAS) BookingItemCreationDto bookingExtras,
             RedirectAttributes redirectAttributes, HttpSession session) {

        if (!dateTimeService.isBookingDateValid(startDate,startTime,endDate,endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }
        if (session.getAttribute("car") == null || session.getAttribute(ControllerConstant.DAYS_OF_RENT) == null)
            return redirectToHome(redirectAttributes, ControllerConstant.SESSION_EXPIRED);


        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("isAdd", false);
        BookingDto userBooking =
                    bookingMapper.mapToBookingDto(bookingService.setAllBookingCostFields(bookingMapper.mapToBooking(
                            new BookingDto.BookingDtoBuilder()
                                .carId(carId)
                                .bookingStatusCodeId(1L)
                                .cityId(cityId)
                                .totalCost(BigDecimal.ZERO)
                                .startDate(bookingService.stringTimeToTimestampConverter(startDate, startTime))
                                .returnDate(bookingService.stringTimeToTimestampConverter(endDate, endTime))
                                .bookingExtrasList(bookingExtras.getItems())
                                .build())));
        session.setAttribute(ControllerConstant.USER_BOOKING, userBooking);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO, getUser());
        session.setAttribute(BOOKING_EXTRAS, bookingExtras);
        modelAndView.setViewName(ControllerConstant.BOOKING_CHECKOUT);
        return modelAndView;
    }

    @GetMapping(value = {"car/{carId}/booking/checkout"})
    public ModelAndView bookingCheckout(@PathVariable(value = "carId") Long carId, RedirectAttributes redirectAttributes,
                                        HttpSession session) {
        if(isSessionExpired(session))  return redirectToHome(redirectAttributes, SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BOOKING_CHECKOUT);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO, getUser());
        return modelAndView;
    }

    @PostMapping(value = {"car/{carId}/booking/user/{userId}"})
    public ModelAndView saveBooking(@PathVariable(value = "carId") Long carId, @PathVariable(value = "userId") Long userId,
                                           @Valid @ModelAttribute(EDIT_USER_DTO) UserEditFormDto userDto, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, HttpSession session) {
        if(isSessionExpired(session))   return redirectToHome(redirectAttributes, SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EDIT_USER_DTO, userDto);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, userMapper.mapToUser(userDto));
            return redirectToHome(redirectAttributes, "Booking has been confirmed");
        }
        return modelAndView;
    }

    @PostMapping(value = {"car/{carId}/booking/"})
    public ModelAndView saveBookingAndUser(@PathVariable(value = "carId") Long carId,
                                           @Valid @ModelAttribute(LONG_USER_DTO) UserRegisterLongFormDto longUserDto, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, HttpSession session) {
        if(isSessionExpired(session)) return redirectToHome(redirectAttributes, ControllerConstant.SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(LONG_USER_DTO, longUserDto);
        if (userService.isUserExist(longUserDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, userMapper.mapToUser(longUserDto));
            return redirectToHome(redirectAttributes, "Booking has been confirmed!  Open the email, and click the link to confirm your account.");
        }
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("booking", new BookingSpecDto());
        modelAndView.setViewName("index");
        return modelAndView;
    }


    private UserEditFormDto getUser() {
        if(userService.getUserFromAuthentication().getEmail()!=null) {
            return userMapper.mapToEditFormUserDto(userService.getUserFromAuthentication());
        } else {
            return new UserEditFormDto();
        }
    }

    private boolean isSessionExpired( HttpSession session) {
        return (session.getAttribute(BOOKING_EXTRAS) == null || session.getAttribute(ControllerConstant.USER_BOOKING) == null
                || session.getAttribute(ControllerConstant.DAYS_OF_RENT) == null);
    }

    private ModelAndView redirectToHome(RedirectAttributes redirectAttributes, String message) {
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute(ControllerConstant.ERRORMESSAGE, message);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    private void saveBookingAndUser(HttpSession session, User user2) {
        User user = userService.save(user2);
        Booking booking = bookingMapper.mapToBooking((BookingDto) session.getAttribute(ControllerConstant.USER_BOOKING));
        booking.setUser(user);
        bookingService.save(booking);
    }
}
