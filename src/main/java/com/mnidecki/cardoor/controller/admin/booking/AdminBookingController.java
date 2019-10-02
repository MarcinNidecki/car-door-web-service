package com.mnidecki.cardoor.controller.admin.booking;

import com.mnidecki.cardoor.domain.dto.BookingDto;
import com.mnidecki.cardoor.mapper.BookingMapper;
import com.mnidecki.cardoor.services.DBService.DBBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminBookingController {

    @Autowired
    private DBBookingService bookingService;
    @Autowired
    private BookingMapper bookingMapper;



    @GetMapping(value = "/booking")
    public ModelAndView booking() {
        ModelAndView modelAndView = new ModelAndView();
        List<BookingDto> bookingList = bookingMapper.mapToBookingDtoList(bookingService.findAll());
        modelAndView.addObject("bookingList",bookingList);
        modelAndView.setViewName("bookings");
        return modelAndView;
    }
}
