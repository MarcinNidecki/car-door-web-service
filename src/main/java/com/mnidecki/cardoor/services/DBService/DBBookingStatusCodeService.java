package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.repository.BookingStatusCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBBookingStatusCodeService {

    @Autowired
    private BookingStatusCodeRepository bookingStatusCodeRepository;


    public List<BookingStatusCode> getAllBookingStatusCode() {
        return bookingStatusCodeRepository.findAll();
    }

    public BookingStatusCode getBookingStatusCode(final Long id) {
        return bookingStatusCodeRepository.findById(id).orElse(null);
    }

    public BookingStatusCode saveBookingStatusCode(final BookingStatusCode statusCode) {
        return bookingStatusCodeRepository.save(statusCode);
    }

    public void deleteBookingStatusCode(final Long id) {
        bookingStatusCodeRepository.deleteById(id);
    }


}
