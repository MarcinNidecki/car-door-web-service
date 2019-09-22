package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.booking.BookingExtras;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingExtrasRepository extends CrudRepository<BookingExtras,Long> {

    @Override
    List<BookingExtras> findAll();

    @Override
    BookingExtras save(BookingExtras bookingExtras);

    Optional<BookingExtras> findById(Long id);

    void deleteById(Long id);


}
