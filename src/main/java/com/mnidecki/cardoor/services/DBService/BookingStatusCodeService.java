package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.repository.BookingStatusCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingStatusCodeService {

    @Autowired
    private BookingStatusCodeRepository bookingStatusCodeRepository;

    public List<BookingStatusCode> findAll() {
        return bookingStatusCodeRepository.findAll();
    }

    public BookingStatusCode findById(final Long id) {
        return bookingStatusCodeRepository.findById(id).orElse(new BookingStatusCode());
    }

    public BookingStatusCode save(final BookingStatusCode statusCode) {
        return bookingStatusCodeRepository.save(statusCode);
    }

    public void deleteById(final Long id) {
        bookingStatusCodeRepository.deleteById(id);
    }


}
