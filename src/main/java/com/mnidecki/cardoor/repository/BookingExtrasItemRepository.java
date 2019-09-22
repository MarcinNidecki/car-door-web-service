package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.booking.BookingExtrasItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookingExtrasItemRepository extends CrudRepository<BookingExtrasItem,Long> {


    @Override
    List<BookingExtrasItem> findAll();

    @Override
    BookingExtrasItem save(BookingExtrasItem bookingExtrasItem);

    Optional<BookingExtrasItem> findById(Long id);

    void deleteById(Long id);

    List<BookingExtrasItem> findAllBookingExtrasItemByBooking_Id(Long id);

}
