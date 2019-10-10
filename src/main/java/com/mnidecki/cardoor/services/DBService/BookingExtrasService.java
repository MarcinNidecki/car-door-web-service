package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingExtras;
import com.mnidecki.cardoor.repository.BookingExtrasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingExtrasService {

    @Autowired
    BookingExtrasRepository bookingExtrasRepository;


    public List<BookingExtras> findAll() {
        return bookingExtrasRepository.findAll();
    }

    public BookingExtras save(BookingExtras bookingExtras) {
        return bookingExtrasRepository.save(bookingExtras);
    }

    public BookingExtras findById(Long id) {
        return bookingExtrasRepository.findById(id).orElse(new BookingExtras());
    }

    public void deleteById(Long id) {
        bookingExtrasRepository.deleteById(id);
    }
}
