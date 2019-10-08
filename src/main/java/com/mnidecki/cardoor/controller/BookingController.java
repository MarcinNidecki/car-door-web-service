package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.client.AccuWeatherClient;
import com.mnidecki.cardoor.client.KayakClient;
import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.domain.dto.accuweather.ForecastResponseDto;
import com.mnidecki.cardoor.mapper.*;
import com.mnidecki.cardoor.services.DBService.BookingService;
import com.mnidecki.cardoor.services.DBService.CarService;
import com.mnidecki.cardoor.services.DBService.LocationService;
import com.mnidecki.cardoor.services.DBService.UserService;
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


@RestController
@RequestMapping("/")
@CrossOrigin("*")
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
    private AccuWeatherClient accuWeatherClient;
    @Autowired
    private KayakClient kayakClient;



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

        if (!bookingService.isBookingDateValid(startDate,startTime,endDate,endTime)) {
            return redirectToHome(redirectAttributes, "Please check your date!");
        }

        List<CarDto> carList = carMapper.mapToCarDtoList(carService.getAllAvailableCar(carSpecification, startDate,
                startTime, endDate, endTime, cityId));
        String cityName = locationService.findById(cityId).getCity();
        long averagePrice = kayakClient.getKayakAverageTotalCarPrice(cityName,startDate,startTime,endDate,endTime);
        ForecastResponseDto forecastResponseDto = accuWeatherClient.get5DayForecasts(locationService.findById(cityId));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("weather", forecastResponseDto);
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
            @RequestParam(value = "cityId") Long cityId, @PathVariable Long carId, HttpSession session) {

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
        modelAndView.addObject("longUserDto", new UserLongFormDto());
        modelAndView.addObject("editUserDto", getUser());
        modelAndView.addObject("bookingExtras", bookingExtras);
        modelAndView.setViewName("carBooking");
        return modelAndView;
    }


    @RequestMapping(value = {"car/{carId}/booking/checkout"}, method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView bookingCheckout
            (@RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
             @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
             @RequestParam(value = "cityId") Long cityId, @PathVariable(value = "carId") Long carId,
             @ModelAttribute(value = "bookingExtras") BookingItemCreationDto bookingExtras,
             RedirectAttributes redirectAttributes, HttpSession session) {

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
        modelAndView.addObject("longUserDto", new UserLongFormDto());
        modelAndView.addObject("editUserDto", getUser());
        session.setAttribute("bookingExtras", bookingExtras);
        modelAndView.setViewName(ControllerConstant.BOOKING_CHECKOUT);
        return modelAndView;
    }

    @RequestMapping(value = {"car/{carId}/booking/checkout"}, method = {RequestMethod.GET})
    public ModelAndView bookingCheckout(@PathVariable(value = "carId") Long carId, RedirectAttributes redirectAttributes,
                                        HttpSession session) {
        if(isSessionExpired(session))  return redirectToHome(redirectAttributes, ControllerConstant.SESSION_EXPIRED);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ControllerConstant.BOOKING_CHECKOUT);
        modelAndView.addObject("longUserDto", new UserLongFormDto());
        modelAndView.addObject("editUserDto", getUser());
        return modelAndView;
    }

    @RequestMapping(value = {"car/{carId}/booking/user/{userId}"}, method = {RequestMethod.POST})
    public ModelAndView saveBookingAndUser(@PathVariable(value = "carId") Long carId, @PathVariable(value = "userId") Long userId,
                                           @Valid @ModelAttribute("editUserDto") UserEditFormDto userDto, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, HttpSession session) {
        if(isSessionExpired(session))   return redirectToHome(redirectAttributes, ControllerConstant.SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("editUserDto", userDto);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(ControllerConstant.BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, userMapper.mapToUser(userDto));
            modelAndView.addObject(
                    ControllerConstant.ERRORMESSAGE, "Booking has been confirmed! ");
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"car/{carId}/booking/"}, method = {RequestMethod.POST})
    public ModelAndView saveBookingAndUser(@PathVariable(value = "carId") Long carId,
                                           @Valid @ModelAttribute("longUserDto") UserLongFormDto longUserDto, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, HttpSession session) {
        if(isSessionExpired(session)) return redirectToHome(redirectAttributes, ControllerConstant.SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("longUserDto", longUserDto);
        if (userService.isUserExist(longUserDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(ControllerConstant.BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, userMapper.mapToUser(longUserDto));
            modelAndView.addObject(ControllerConstant.ERRORMESSAGE, "Booking has been confirmed!  Open the email, and click the link to confirm your account.");
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
        return (session.getAttribute("bookingExtras") == null || session.getAttribute(ControllerConstant.USER_BOOKING) == null
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
