package com.mnidecki.cardoor.controller;

import com.mnidecki.cardoor.domain.Weather;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.dto.*;
import com.mnidecki.cardoor.facade.BookingFacade;
import com.mnidecki.cardoor.services.AccuWeatherService;
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
import java.util.List;

import static com.mnidecki.cardoor.controller.ControllerConstant.*;


@RestController
@RequestMapping("/")
public class BookingController {

    @Autowired
    private BookingFacade bookingFacade;
    @Autowired
    private AccuWeatherService accuWeatherService;

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
        startTime = bookingFacade.reformatTime(startTime);
        endTime = bookingFacade.reformatTime(endTime);
        if (!bookingFacade.isDateValid(startDate, startTime, endDate, endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }

        List<CarDto> carList = bookingFacade.getAllAvailableCar(carSpecification, startDate, startTime, endDate,
                endTime, cityId);
        String cityName = bookingFacade.getLocationName(cityId);
        long averagePrice = bookingFacade.getKayakAverageTotalCarPrice(cityName, startDate, startTime, endDate, endTime);
        Weather weather = accuWeatherService.findById(cityId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("cars", carList);
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);
        modelAndView.addObject("cityId", cityId);
        modelAndView.addObject("cityName", cityName);
        modelAndView.addObject("averagePrice", averagePrice);
        modelAndView.setViewName("booking");
        return modelAndView;
    }

    @GetMapping("car/{carId}/booking")
    public ModelAndView chooseExtrasItem(
            @RequestParam(value = "startDate") String startDate, @RequestParam(value = "startTime") String startTime,
            @RequestParam(value = "endDate") String endDate, @RequestParam(value = "endTime") String endTime,
            @RequestParam(value = "cityId") Long cityId, @PathVariable Long carId,
            RedirectAttributes redirectAttributes, HttpSession session) {

        if (!bookingFacade.isDateValid(startDate, startTime, endDate, endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }

        CarDto car = bookingFacade.getCar(carId);
        LocationnDto location = bookingFacade.getLocation(carId);
        BookingItemCreationDto bookingExtras = bookingFacade.getEmptyExtrasItemList();
        long daysOfRent = bookingFacade.countBookingDays(startDate,startTime,endDate,endTime);

        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("car", car);
        session.setAttribute("location", location);
        session.setAttribute(DAYS_OF_RENT, daysOfRent);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO,  bookingFacade.getUser());
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


        if (!bookingFacade.isDateValid(startDate, startTime, endDate, endTime)) {
            return redirectToHome(redirectAttributes, PLEASE_CHECK_YOUR_DATE);
        }
        if (session.getAttribute("car") == null || session.getAttribute(DAYS_OF_RENT) == null)
            return redirectToHome(redirectAttributes, SESSION_EXPIRED);


        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("isAdd", false);
        BookingDto userBooking = bookingFacade.prepareBooking(carId, cityId, startTime,
                endTime, startDate, endDate,  bookingExtras);
        session.setAttribute(USER_BOOKING, userBooking);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO, bookingFacade.getUser());
        session.setAttribute(BOOKING_EXTRAS, bookingExtras);
        modelAndView.setViewName(BOOKING_CHECKOUT);
        return modelAndView;
    }

    @GetMapping(value = {"car/{carId}/booking/checkout"})
    public ModelAndView bookingCheckout(@PathVariable(value = "carId") Long carId, RedirectAttributes redirectAttributes,
                                        HttpSession session) {
        if (isSessionExpired(session)) return redirectToHome(redirectAttributes, SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BOOKING_CHECKOUT);
        modelAndView.addObject(LONG_USER_DTO, new UserRegisterLongFormDto());
        modelAndView.addObject(EDIT_USER_DTO, bookingFacade.getUser());
        return modelAndView;
    }

    @PostMapping(value = {"car/{carId}/booking/user/{userId}"})
    public ModelAndView saveBooking(@PathVariable(value = "carId") Long carId, @PathVariable(value = "userId") Long userId,
                                    @Valid @ModelAttribute(EDIT_USER_DTO) UserEditFormDto userDto, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, HttpSession session) {
        if (isSessionExpired(session)) return redirectToHome(redirectAttributes, SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EDIT_USER_DTO, userDto);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, userDto);
            return redirectToHome(redirectAttributes, "Booking has been confirmed, check your email!");
        }
        return modelAndView;
    }

    @PostMapping(value = {"car/{carId}/booking/"})
    public ModelAndView saveBookingAndUser(@PathVariable(value = "carId") Long carId,
                                           @Valid @ModelAttribute(LONG_USER_DTO) UserRegisterLongFormDto longUserDto, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, HttpSession session) {
        if (isSessionExpired(session)) return redirectToHome(redirectAttributes, SESSION_EXPIRED);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(LONG_USER_DTO, longUserDto);
        if (bookingFacade.isUserExist(longUserDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(BOOKING_CHECKOUT);
        } else {
            saveBookingAndUser(session, longUserDto);
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

    private boolean isSessionExpired(HttpSession session) {
        return (session.getAttribute(BOOKING_EXTRAS) == null || session.getAttribute(USER_BOOKING) == null
                || session.getAttribute(DAYS_OF_RENT) == null);
    }

    private ModelAndView redirectToHome(RedirectAttributes redirectAttributes, String message) {
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute(ERRORMESSAGE, message);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    private void saveBookingAndUser(HttpSession session, UserDto user2) {
        bookingFacade.registerUserAndOrder((BookingDto) session.getAttribute(USER_BOOKING), user2);
    }
}
