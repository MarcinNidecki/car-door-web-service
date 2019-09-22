package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.booking.BookingStatusCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingStatusCodeRepository extends CrudRepository<BookingStatusCode, Integer> {

    @Override
    List<BookingStatusCode> findAll();

    Optional<BookingStatusCode> findById(Long id);

    @Override
    BookingStatusCode save(BookingStatusCode car);

    void deleteById(Long id);

}
