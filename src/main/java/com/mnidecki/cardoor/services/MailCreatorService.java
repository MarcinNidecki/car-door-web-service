package com.mnidecki.cardoor.services;


import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.dto.BookingDto;
import com.mnidecki.cardoor.domain.dto.BookingExtrasItemDto;
import com.mnidecki.cardoor.domain.dto.CarDto;
import com.mnidecki.cardoor.mapper.BookingMapper;
import com.mnidecki.cardoor.mapper.CarMapper;
import com.mnidecki.cardoor.services.DBService.DBBookingService;
import com.mnidecki.cardoor.services.DBService.DBCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MailCreatorService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private DBBookingService bookingService;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private DBCarService carService;
    @Autowired
    private CarMapper carMapper;


    public String bookingConfirmation(Booking booking) {

        Context context = new Context();
        BookingDto bookingDto = bookingMapper.mapToBookingDto(booking);
        CarDto carDto = carMapper.mapToCarDto(carService.getCar(bookingDto.getCarId()));
        List<BookingExtrasItemDto> extrasList =  bookingDto.getBookingExtrasList();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        long daysOfRent =  bookingService.countBookingDays(bookingDto.getStartDate(),bookingDto.getReturnDate());
        context.setVariable("daysOfRent", daysOfRent);
        context.setVariable("booking", bookingDto);
        context.setVariable("car", carDto);
        context.setVariable("extrasList", extrasList);
        context.setVariable("current_day", new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        return templateEngine.process("mailConfirmation", context);
    }
}
