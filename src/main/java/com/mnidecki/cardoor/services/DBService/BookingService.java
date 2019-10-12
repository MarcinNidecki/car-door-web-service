package com.mnidecki.cardoor.services.DBService;

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
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CommentService commentService;
    private final BookingExtrasService extrasService;
    private final SimpleEmailService simpleEmailService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CommentService commentService,
                          BookingExtrasService extrasService, SimpleEmailService simpleEmailService) {
        this.bookingRepository = bookingRepository;
        this.commentService = commentService;
        this.extrasService = extrasService;
        this.simpleEmailService = simpleEmailService;
    }


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(final Long id) {
        return bookingRepository.findById(id).orElse(new Booking());
    }

    public Booking save(Booking booking) {
        booking.getBookingExtrasList().forEach(bookingExtrasItem -> bookingExtrasItem.setBooking(booking));
        booking.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        simpleEmailService.sendBookingConfirmation(booking);
        return bookingRepository.save(booking);
    }

    public void deleteById(final Long id) {
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
        long daysBetween = ChronoUnit.DAYS.between(startDateTime, endDateTime);
        if (timeEnd.isAfter(timeStart)) {
            daysBetween += 1;
        }
        return daysBetween;
    }

    public Booking setAllBookingCostFields(Booking booking) {

        booking.setBookingExtrasList(booking.getBookingExtrasList().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList()));
        booking.getBookingExtrasList()
                .forEach(bookingExtrasItem ->
                        bookingExtrasItem.setValue(BigDecimal.valueOf(bookingExtrasItem.getQuantity())
                                .multiply(bookingExtrasItem.getBookingExtras().getPrice())));

        BigDecimal totalCost = booking.getBookingExtrasList().stream()
                .map(BookingExtrasItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCost = totalCost.add(booking.getCar().getPrice().multiply(
                new BigDecimal(countBookingDays(booking.getStartDate(), booking.getReturnDate()))));
        booking.setTotalCost(totalCost);
        return booking;

    }

    public List<BookingExtrasItem> prepareEmptyExtrasItemList() {
        List<BookingExtrasItem> extrasItemList = new ArrayList<>();
        extrasService.findAll().forEach(extras -> extrasItemList.add(new BookingExtrasItem(0L, BigDecimal.ZERO, extras)));
        return extrasItemList;
    }

    public Timestamp stringTimeToTimestampConverter(String date, String time) {
        return Timestamp.valueOf(LocalDateTime.parse(date+"T" +time));
    }



    public int  countHappyClients() {
        Integer unhappyClient;
        unhappyClient = commentService.countAllByRatingLessThan2();
        int allClient = findAll().size();
        if(allClient==0) return 0;
        return allClient - unhappyClient;
    }


}

