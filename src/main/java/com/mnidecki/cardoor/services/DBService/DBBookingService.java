package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.Mail;
import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.repository.BookingRepository;
import com.mnidecki.cardoor.services.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBBookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DBCarService carService;
    @Autowired
    private  DBBookingExtrasService extrasService;
    @Autowired
    private SimpleEmailService simpleEmailService;


    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(final Long id) {
        return bookingRepository.findById(id).orElseGet(null);
    }

    public Booking saveBooking(final Booking booking) {
        booking.getBookingExtrasList().forEach(bookingExtrasItem -> bookingExtrasItem.setBooking(booking));
        booking.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        String SUBJECT = "CARDOOR - Rental reservation confirmation";
        simpleEmailService.send(new Mail(
                booking.getUser().getEmail(),
                SUBJECT,
                "CARDOOR car reservation confirmation"),booking
        );
        return bookingRepository.save(booking);
    }

    public void deleteBooking(final Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookingByCarId(Long id) {
        return bookingRepository.findAllByCar_Id(id);
    }


    public long countBookingDays(Timestamp rentDate, Timestamp returnDate) {
        LocalDateTime startDateTime = rentDate.toLocalDateTime();
        LocalDateTime endDateTime = returnDate.toLocalDateTime();
        LocalTime timeStart = startDateTime.toLocalTime();
        LocalTime timeEnd = endDateTime.toLocalTime();
        long daysBetween = ChronoUnit.DAYS.between(startDateTime,endDateTime);
        if (timeEnd.isAfter(timeStart)) {
            daysBetween += 1;
        }
        return daysBetween;
    }

    public Booking setAllBookingCostFields(Booking booking) {

        booking.setBookingExtrasList(booking.getBookingExtrasList().stream()
                .filter(item -> item.getQuantity()>0)
                .collect(Collectors.toList()));
        booking.getBookingExtrasList()
                .forEach(bookingExtrasItem ->
                        bookingExtrasItem.setValue(BigDecimal.valueOf(bookingExtrasItem.getQuantity()).multiply(bookingExtrasItem.getBookingExtras().getPrice())));

        BigDecimal totalCost = booking.getBookingExtrasList().stream()
                .map(BookingExtrasItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCost = totalCost.add(booking.getCar().getPrice().multiply(new BigDecimal(countBookingDays(booking.getStartDate(),booking.getReturnDate()))));
        booking.setTotalCost(totalCost);
        return booking;

    }

    public List<BookingExtrasItem> prepareEmptyExtrasList() {
        List<BookingExtrasItem> extrasItemList = new ArrayList<>();
        extrasService.findAll().forEach(extras -> extrasItemList.add(new BookingExtrasItem(0L,BigDecimal.ZERO,extras)));
        return extrasItemList;
    }

    public Timestamp timeToTimestampConverter(String date, String time) {
        return  Timestamp.valueOf(date+ " " + time +":00");
    }

}

