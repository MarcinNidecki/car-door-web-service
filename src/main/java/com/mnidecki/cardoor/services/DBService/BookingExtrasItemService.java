package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import com.mnidecki.cardoor.repository.BookingExtrasItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingExtrasItemService {

    @Autowired
    BookingExtrasItemRepository bookingExtrasItemRepository;

    public List<BookingExtrasItem> findAll() {
        return bookingExtrasItemRepository.findAll();
    }

    public BookingExtrasItem save(BookingExtrasItem bookingExtrasItem) {
        return bookingExtrasItemRepository.save(bookingExtrasItem);
    }

    public Optional<BookingExtrasItem> findById(Long id) {
        return bookingExtrasItemRepository.findById(id);
    }

    public void deleteById(Long id) {
        bookingExtrasItemRepository.deleteById(id);
    }

}
