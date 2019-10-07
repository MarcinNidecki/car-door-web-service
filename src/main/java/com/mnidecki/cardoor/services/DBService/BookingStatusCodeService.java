package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import com.mnidecki.cardoor.repository.BookingStatusCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class BookingStatusCodeService {

    @Autowired
    private BookingStatusCodeRepository bookingStatusCodeRepository;

    public List<BookingStatusCode> findAll() {
        return bookingStatusCodeRepository.findAll();
    }

    public Optional<BookingStatusCode> findById(final Long id) {
        return bookingStatusCodeRepository.findById(id);
    }

    public BookingStatusCode save(final BookingStatusCode statusCode) {
        return bookingStatusCodeRepository.save(statusCode);
    }

    public void deleteById(final Long id) {
        bookingStatusCodeRepository.deleteById(id);
    }


}
